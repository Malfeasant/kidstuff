package us.malfeasant.kidstuff;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;

public class CanvasPainter extends AnimationTimer {
    public final ObjectProperty<VideoMode> upperModeProperty = new SimpleObjectProperty<>();
    public final ObjectProperty<VideoMode> lowerModeProperty = new SimpleObjectProperty<>();
    // text line that divides upper from lower part of screen- if unused, set to negative
    public final IntegerProperty splitLineProperty = new SimpleIntegerProperty(-1);
    public final ObjectProperty<ColorNybble> borderColorProperty =
        new SimpleObjectProperty<>(ColorNybble.LIGHTGREEN);
    
    private final Canvas canvas;
    private final Memory memory;

    private enum Register {
        // TODO offsets for registers

        BORDERCOLOR;
    }
    public CanvasPainter(Canvas canv, Memory mem) {
        canvas = canv;
        memory = mem;
        memory.attachDevice(0, new MemInterface() {

            @Override
            public byte peek(int addr) {
                switch (addr) {
                    
                }
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'peek'");
            }

            @Override
            public void poke(int addr, byte data) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'poke'");
            }
            
        });
    }

    @Override
    public void handle(long now) {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(borderColorProperty.get().color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
