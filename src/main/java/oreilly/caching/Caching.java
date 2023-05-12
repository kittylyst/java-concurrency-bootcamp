package oreilly.caching;

// tag::CACHE_EFFECT[]
public class Caching {
    private final int ARR_SIZE = 2 * 1024 * 1024;
    private final int[] testData = new int[ARR_SIZE];

    private void run() {
        for (int i = 0; i < 10_000; i += 1) {
            touchEvery4thLine();
            touchEveryItem();
        }
        System.out.println("Line     Item");
        for (int i = 0; i < 100; i += 1) {
            long t0 = System.nanoTime();
            touchEvery4thLine();
            long t1 = System.nanoTime();
            touchEveryItem();
            long t2 = System.nanoTime();
            long elEveryLine = t1 - t0;
            long elEveryItem = t2 - t1;
            System.out.println(elEveryLine + " " + elEveryItem);
        }
    }

    private void touchEveryItem() {
        for (int i = 0; i < testData.length; i += 1)
            testData[i] += 1;
    }

    private void touchEvery4thLine() {
        for (int i = 0; i < testData.length; i += 64)
            testData[i] += 1;
    }

    public static void main(String[] args) {
        Caching c = new Caching();
        c.run();
    }
}
// end::CACHE_EFFECT[]
