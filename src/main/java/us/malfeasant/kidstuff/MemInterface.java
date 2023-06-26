package us.malfeasant.kidstuff;

public interface MemInterface {
    default byte peek(int addr) {
        return -1;
    }
    default void poke(int addr, byte data) {}   // nop
}
