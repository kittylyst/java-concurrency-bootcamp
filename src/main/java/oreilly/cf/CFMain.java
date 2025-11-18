package oreilly.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static oreilly.cf.AsynchPrimeFinder.getNthPrime;

public class CFMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var start = System.currentTimeMillis(); // main
        CompletableFuture<Long> numF = getNthPrime(10_000); // common pool 1
//        System.out.println(numF.isDone());
//        System.out.println(numF.get());
//        var end = System.currentTimeMillis();
//        System.out.println(end - start);

        // l -> l+2 performed on thread used for original async task
        numF = numF.thenApply(l -> l + 2); // common pool 1

//        // when numF completes,       vvvvvvv separate async task
        numF = numF.thenCompose(l -> CompletableFuture.supplyAsync(() -> l * 3)); // common pool 2
        Consumer<Long> cL = l -> System.out.println("In lambda: "+ l); // print out
        CompletableFuture<Void> numV = numF.thenAccept(cL);


//        numV.join();
//        System.out.println(numF.get());
        var end = System.currentTimeMillis();
        System.out.println(end - start);

    }

}
