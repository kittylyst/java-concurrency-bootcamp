package oreilly.vthread;

import oreilly.counters.wk1.UnprotectedCounter;
import oreilly.counters.wk2.AtomicCounterVarHandle;

public class VTExamples {

    public static final int REPS = 10_000_000;

    static void main() {
        final var c = new UnprotectedCounter();

        Runnable r = () -> {
            for (int i = 0; i < REPS; i = i + 1) {
                c.increment();
            }
        };

        Thread.Builder tb = Thread.ofVirtual();
        tb.name("MyVThread");
        Thread t = tb.unstarted(() -> System.out.println("Hello World!"));
        System.out.println(t);
        t.start();

    }

}
