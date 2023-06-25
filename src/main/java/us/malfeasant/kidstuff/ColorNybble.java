package us.malfeasant.kidstuff;

import javafx.scene.paint.Color;

public enum ColorNybble {
    BLACK       (Color.web("#000")),
    RED         (Color.web("#a00")),
    GREEN       (Color.web("#0a0")),
    YELLOW      (Color.web("#aa0")),
    BLUE        (Color.web("#00a")),
    PURPLE      (Color.web("#a0a")),
    CYAN        (Color.web("#0aa")),
    WHITE       (Color.web("#aaa")),
    LIGHTBLACK  (Color.web("#555")),
    LIGHTRED    (Color.web("#f55")),
    LIGHTGREEN  (Color.web("#5f5")),
    LIGHTYELLOW (Color.web("#ff5")),
    LIGHTBLUE   (Color.web("#55f")),
    LIGHTPURPLE (Color.web("#f5f")),
    LIGHTCYAN   (Color.web("#5ff")),
    LIGHTWHITE  (Color.web("#fff"));

    public final Color color;

    ColorNybble(Color c) {
        color = c;
    }
}
