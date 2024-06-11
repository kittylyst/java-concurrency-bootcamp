package oreilly.counters;

import oreilly.counters.wk1.*;

/**
 * @author ben
 */
public class CounterMain {
    public static final int REPS = 10_000_000;

    public static void main(String[] args) throws InterruptedException {
        final var c = new UnprotectedCounter();

        Runnable r = () -> {
            for (int i = 0; i < REPS; i = i + 1) {
                c.increment();
            }
        };

//        Runnable rB = () -> {
//            for (int i = 0; i < REPS; i = i + 1) {
//                c.increment(true);
//            }
//        };


        Thread tA = new Thread(r);
        Thread tB = new Thread(r);
        long start = System.currentTimeMillis();
        tA.start();
        tB.start();
        tA.join();
        tB.join();
        long fin = System.currentTimeMillis();
        int lost = 2 * REPS - c.get();
        System.out.println("Lost Updates: " + lost + " ; "+ 100 * ((lost +0.0) / (2 * REPS)) +"%");
        System.out.println("Elapsed: " + (fin - start));
    }

}
