package us.malfeasant.kidstuff;

public enum VideoMode {
    TEXT,   // 8-bit char pointer + 8-bit color (bg/fg) 78x52 total 8k, also needs 2k char gen
    QUAD,   // 4-bit x 4 quads = 16-bit per block - total 8k
    NYBBLEMAP,  // 4-bit per pixel = 32-bit per char per line - 624x416 = 128k
    HICOLOR;    // 16-bit per pixel = 128-bit per char per line - 512k
}
