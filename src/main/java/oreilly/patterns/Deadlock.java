package oreilly.patterns;

public class Deadlock {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        Thread t1 = new Thread(() -> {
                synchronized(a) { // Lightweight lock on a
                    try {
                        Thread.sleep(1); // or yield
                    } catch (InterruptedException e) { }
                    synchronized(b) { compute(); } // Forces b to transition to heavy lock
                }});

        Thread t2 = new Thread(() ->{
                synchronized(b) { // Lightweight lock on b
                    synchronized(a) { compute(); } // Forces a to transition to heavy lock
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
