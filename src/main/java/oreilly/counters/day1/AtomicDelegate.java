package oreilly.counters.day1;

import oreilly.counters.Counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDelegate implements Counter  {
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
