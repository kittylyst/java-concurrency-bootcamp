package oreilly.fj;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

public class MergeMain {

    public static void main(String[] args) {
        var numbers = IntStream.range(1, 8192).toArray();

        var pool = new ForkJoinPool();
        ForkJoinTask<Void> job =
                pool.submit(new MergeSortTask(numbers, 0, numbers.length));

        System.out.println("Waiting");
        job.join();
        System.out.println(Arrays.toString(numbers));
    }

}
