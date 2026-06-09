package oreilly.locks;

public interface SimpleQueue {
    void put(Object x) throws InterruptedException;
    Object take() throws InterruptedException;
}
