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

    public CanvasPainter(Canvas canv) {
        canvas = canv;
    }

    @Override
    public void handle(long now) {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(borderColorProperty.get().color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
