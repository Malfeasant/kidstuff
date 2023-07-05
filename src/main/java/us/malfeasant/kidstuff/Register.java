package us.malfeasant.kidstuff;

enum Register {
    MODEBANK { // mode bits, bank select
        @Override
        void poke(CanvasPainter p, byte data) {
            var upMode = (data >> 6) & 3; // bit shift & mask in case of negative
            var loMode = (data >> 4) & 3; // bit shift & mask
            p.upperMode = VideoMode.values()[upMode];
            p.lowerMode = VideoMode.values()[loMode];
            p.setBank(data & 0xf);
        }
        @Override
        byte peek(CanvasPainter p) {
            byte data = (byte) p.bank;
            data |= p.upperMode.ordinal() << 6;
            data |= p.lowerMode.ordinal() << 4;
            return data;
        }
    },
    SPLITLINE { // text line that divides upper from lower
        @Override
        void poke(CanvasPainter p, byte data) {
            p.splitLine = data & 0x3f;
        }
        @Override
        byte peek(CanvasPainter p) {
            return (byte) p.splitLine;
        }
    },
    UPPERPAGE {    // which 4k page to pull gfx data/text from
        @Override
        void poke(CanvasPainter p, byte data) {
            p.upperPage = data & 0xff; // needed because there's no unsigned byte type...
        }
        @Override
        byte peek(CanvasPainter p) {
            return (byte) p.upperPage;
        }
    },
    LOWERPAGE {    // 
        @Override
        void poke(CanvasPainter p, byte data) {
            p.lowerPage = data & 0xff; // needed because there's no unsigned byte type...
        }
        @Override
        byte peek(CanvasPainter p) {
            return (byte) p.lowerPage;
        }
    },
    CHARPAGE {    // lower 8 bits
        @Override
        void poke(CanvasPainter p, byte data) {
            var page = p.charPage & 0x100;  // mask away lower bits
            p.charPage |= (data & 0xff);    // replace lower bits
        }
        @Override
        byte peek(CanvasPainter p) {
            return (byte) (p.charPage & 0xff);
        }
    },
    CHARPAGEHI {    // Only one bit - TODO rename if more bits come from other sources...
        @Override
        void poke(CanvasPainter p, byte data) {
            var page = p.charPage & 0xff;  // mask away upper bit
            p.charPage |= (data & 1) << 8;    // replace upper bit
        }
        @Override
        byte peek(CanvasPainter p) {
            return (byte) (p.charPage >> 8);
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