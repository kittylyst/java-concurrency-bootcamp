package oreilly.counters.day2;

import oreilly.counters.Counter;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public final class AtomicCounterVarHandle implements Counter {
    private static final VarHandle vhForValue;
    private volatile int value = 0;

    static {
        try {
            var l = MethodHandles.lookup();
            vhForValue = l.findVarHandle(l.lookupClass(), "value", int.class);
        } catch (Exception ex) { throw new Error(ex); }
    }

    /**
     * Atomically increments by one the current value.
     *
     * @return the updated value
     */
    public int increment() {
        return 1 + (int) vhForValue.getAndAdd(this, 1);
    }

    /**
     * Gets the current value.
     *
     * @return the current value
     */
    public int get() {
        return value;
    }
}
