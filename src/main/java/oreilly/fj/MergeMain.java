package oreilly.fj;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

public class MergeMain {

    private static final int RANGE_MAX = 16 * 8192;

    void main() {
        var numbers = shuffleFY(IntStream.range(1, RANGE_MAX).toArray());
        IO.println(Arrays.toString(numbers));

        var pool = new ForkJoinPool();
        ForkJoinTask<Void> job =
                pool.submit(new MergeSortTask(numbers, 0, numbers.length));

        IO.println("Waiting");
        job.join();
        IO.println(Arrays.toString(numbers));
    }

    static int[] shuffleFY(int[] inPlace) {
        var rnd = new SecureRandom();
        for (var i = inPlace.length - 1; i > 0; i -= 1) {
            var index = rnd.nextInt(i + 1);
            var tmp = inPlace[index];
            inPlace[index] = inPlace[i];
            inPlace[i] = tmp;
        }
        return inPlace;
    }
}
