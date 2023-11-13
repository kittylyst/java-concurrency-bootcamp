package oreilly.counters.wk1;

import oreilly.counters.Counter;

/**
 *
 * @author ben
 */
public final class SynchronizedCounter implements Counter {

    private int i = 0;

    public int increment() {
        synchronized (this) {
            i = i + 1;
        }
        return i;
    }

    public synchronized int get() {
        return i;
    }

}
