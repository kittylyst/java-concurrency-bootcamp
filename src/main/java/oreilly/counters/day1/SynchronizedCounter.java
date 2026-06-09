package oreilly.counters.day1;

import oreilly.counters.Counter;

/**
 *
 * @author ben
 */
public final class SynchronizedCounter implements Counter {

    private int i = 0;

    public int increment() { // synchronized
        synchronized (this) {
            i = i + 1;
        }
        return i;
    }

    // public synchronized int increment() { ... }

    public synchronized int get() {
        return i;
    }

}
