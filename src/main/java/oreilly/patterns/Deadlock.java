package oreilly.patterns;

public class Deadlock {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        // It works with immutable strings too!
        //        var base = "cat";
//        Object a = base + "s";
//        Object b = base + "ologue";

        Thread t1 = new Thread(() -> {
                synchronized(a) { // Lightweight lock on a
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) { }
                    synchronized(b) { compute(); } // Forces b to transition to heavy lock
                }});

        Thread t2 = new Thread(() ->{
                synchronized(b) { // Lightweight lock on b
                    synchronized(a) { compute(); }
                }});

        t1.start();
        t2.start();
    }

    private static void compute() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
