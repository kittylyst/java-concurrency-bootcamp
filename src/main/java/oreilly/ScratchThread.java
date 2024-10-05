package oreilly;

public class ScratchThread {

    public static void main(String[] args) throws InterruptedException {
        startThread();
        // tA is no longer reachable
        System.out.println("Main thread about to sleep");
        Thread.sleep(10_000);
        System.out.println("Main thread done");
    }

    private static void startThread() {
        Runnable r = () -> {
            for (int i = 0; i < 4_000; i = i + 1) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Out of scope thread done");
        };

        Thread tA = new Thread(r);
        tA.start(); // tA object goes out of scope here....
    }

}
