package oreilly.locks;

public interface SimpleBoundedQueue {
    void put(Object x) throws InterruptedException;
    Object take() throws InterruptedException;
}
