package oreilly.counters.wk1;

import oreilly.counters.Counter;

/**
 *
 * @author ben
 */
public final class UnprotectedCounter implements Counter {

    private int i = 0;

    public int increment() {
        return i = i + 1;
    }

    public int get() {
        return i;
    }

}
