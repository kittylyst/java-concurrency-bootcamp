package oreilly.counters.wk2;

import oreilly.counters.Counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCheatCounter implements Counter  {
    private final AtomicInteger internal = new AtomicInteger();

    @Override
    public int increment() {
        return internal.incrementAndGet();
    }

    @Override
    public int get() {
        return internal.get();
    }
}
