package us.malfeasant.kidstuff;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class CanvasPainter extends AnimationTimer {
    private VideoMode upperMode = VideoMode.TEXT;
    private VideoMode lowerMode = null;
    // text line that divides upper from lower part of screen- out of range disables lower
    private int splitLine = -1; // 6 bits significant
    private ColorNybble borderColor = ColorNybble.LIGHTGREEN;
    private int bank = 0;   // sets the 1M bank accessible to the video system- 4 bits
    private int upperPage;  // 4k page within bank to pull gfx data from - 8 bits 
    private int lowerPage;  // as above, but for lower part of screen
    // some bits may be ignored depending on mode
    private int charPage;   // 2k block to pull char data from- 9 bits (only used in text mode)

    private final Canvas canvas;
    private final Memory memory;

    private enum Register {
        MODEBANK { // mode bits, bank select
            @Override
            void poke(CanvasPainter p, byte data) {
                var upMode = (data >> 6) & 3; // bit shift & mask in case of negative
                var loMode = (data >> 4) & 3; // bit shift & mask
                p.upperMode = VideoMode.values()[upMode];
                p.lowerMode = VideoMode.values()[loMode];
                p.bank = data & 0xf;
            }
            @Override
            byte peek(CanvasPainter p) {
                byte data = (byte) p.bank;
                data |= p.upperMode.ordinal() << 6;
                data |= p.lowerMode.ordinal() << 4;
                return data;
            }
        },
        // TODO offsets for registers

        BORDERCOLOR {
            @Override
            void poke(CanvasPainter p, byte data) {
                p.borderColor = ColorNybble.values()[data & 0xf];
            }
            @Override
            byte peek(CanvasPainter p) {
                return (byte) p.borderColor.ordinal();
            }
        }, 
        NOP;    // Used if address is out of range

        void poke(CanvasPainter p, byte data) {}    // NOP
        byte peek(CanvasPainter p) { return -1;}    // NOP

        static Register getRegisterFor(int addr) {
            return addr >= values().length ? NOP : values()[addr];
        }
    }

    public CanvasPainter(Canvas canv, Memory mem) {
        canvas = canv;
        memory = mem;
        memory.attachDevice(0, new MemInterface() {
            @Override
            public byte peek(int addr) {
                return Register.getRegisterFor(addr).peek(CanvasPainter.this);
            }

            @Override
            public void poke(int addr, byte data) {
                Register.getRegisterFor(addr).poke(CanvasPainter.this, data);
            }
        });
    }

    @Override
    public void handle(long now) {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(borderColor.color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
