package us.malfeasant.kidstuff;

import java.nio.ByteBuffer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class CanvasPainter extends AnimationTimer {
    private static final int LASTLINE = 52;

    VideoMode upperMode = VideoMode.TEXT;
    VideoMode lowerMode = null;
    // text line that divides upper from lower part of screen- out of range disables lower
    int splitLine = 0x3f; // 6 bits significant
    ColorNybble borderColor = ColorNybble.LIGHTGREEN;
    int bank = 0;   // sets the 1M bank accessible to the video system- 4 bits
    int upperPage;  // 4k page within bank to pull gfx data from - 8 bits 
    int lowerPage;  // as above, but for lower part of screen
    // some bits may be ignored depending on mode
    int charPage;   // 2k block to pull char data from- 9 bits (only used in text mode)

    private final Canvas canvas;
    private final Memory memory;
    private ByteBuffer videoBuffer;

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

        for (var line = 0; line < LASTLINE; ++line) {   // note these are text lines
            var upper = line < splitLine;
            var mode = upper ? upperMode : lowerMode;
            var page = upper ? upperPage : lowerPage;
            
            
        }
    }

    void setBank(int b) {
        if (bank != b) {
            bank = b;
            videoBuffer = memory.getVideoBank(b);
        }
    }
}
