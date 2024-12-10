package oreilly.patterns;

public class Deadlock {
    public static void main(String[] args) {
        var base = "cat";
        Object a = base + "s";
        Object b = base + "ologue";

        Thread t1 = new Thread(() -> {
                synchronized(a) { // Lightweight lock on a
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) { }
                    synchronized(b) { compute(); } // Lightweight lock on b
                }});

        Thread t2 = new Thread(() ->{
                synchronized(b) { // Forces b to transition to heavy lock
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
