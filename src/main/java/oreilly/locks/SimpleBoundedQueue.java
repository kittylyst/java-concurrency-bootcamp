package oreilly.locks;

import java.util.Queue;

public interface SimpleBoundedQueue {
    void put(Object x) throws InterruptedException;
    Object take() throws InterruptedException;
}
