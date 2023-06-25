package us.malfeasant.kidstuff;

public enum VideoMode {
    TEXT,   // 8-bit char pointer + 8-bit color (bg/fg)
    QUAD,   // 4-bit x 4 quads = 16-bit (use char ptr + color)
    NYBBLEMAP,  // 4-bit per pixel = 32-bit per char per line
    HICOLOR;    // 16-bit per pixel = 128-bit per char per line
}
