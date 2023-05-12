package oreilly.counters.wk1;

import oreilly.counters.Counter;

/**
 *
 * @author ben
 */
public final class HalfSynchronizedCounter implements Counter {

    private int i = 0;

    public synchronized int increment() {
        return i = i + 1;
    }

    public synchronized int get() {
        return i;
    }

    public int increment(boolean b) {
        return i = i + 1;
    }

    public int get(boolean b) {
        return i;
    }

}
