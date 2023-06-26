package us.malfeasant.kidstuff;

import java.util.IdentityHashMap;
import java.util.Map;

public class Memory {
    private static final int RAMSIZE = 0x1000000;
    private final byte[] mem = new byte[RAMSIZE];

    public void poke(int addr, byte data) {
        var io = isIo(addr);
        if (io) {
            getPage(addr).poke(maskIo(addr), data);
        } else {
            addr &= (RAMSIZE - 1);
            mem[addr] = data;
        }
    }

    public byte peek(int addr) {
        byte data;
        var io = isIo(addr);
        if (io) {
            data = getPage(addr).peek(maskIo(addr));
        } else {
            addr &= (RAMSIZE - 1);
            data = mem[addr];
        }
        return data;
    }

    private boolean isIo(int addr) {
        return (addr & 0xff0000) != 0;
    }
    private int maskIo(int addr) {
        return addr & 0xffff;
    }
    private static final MemInterface NOP = new MemInterface(){};
    private MemInterface getPage(int addr) {
        var page = addr >> 16;
        var device = ioMap.get(page);
        return device == null ? NOP : device;
    }
    private final Map<Integer, MemInterface> ioMap = new IdentityHashMap<>();
    public void attachDevice(int page, MemInterface device) {
        ioMap.put(page, device);
    }
    public void removeDevice(int page) {
        ioMap.remove(page);
    }
}
