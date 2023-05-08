package oreilly.locks;

public class ClassicQueue implements SimpleBoundedQueue {

    private final Object[] items = new Object[100];
    private int putptr, takeptr, count;

    public synchronized void put(Object x) throws InterruptedException {
        // If full, release lock & wait until we get it back
        while (count == items.length) {
            wait();
        }

        items[putptr] = x;
        if (++putptr == items.length) putptr = 0;
        count += 1;

        // signal other threads that they can try for the lock again
        notify();
    }

    public synchronized Object take() throws InterruptedException {
        // If empty, release the lock & wait
        while (count == 0) {
            wait();
        }

        Object x = items[takeptr];
        if (++takeptr == items.length) takeptr = 0;
        count -= 1;

        // Signal other threads that they can stop waiting & try for the lock
        notify();
        return x;
    }
}
