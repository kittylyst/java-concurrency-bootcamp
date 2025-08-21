package oreilly.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static oreilly.cf.AsynchPrimeFinder.getNthPrime;

public class CFMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Long> numF = getNthPrime(10_000); // main
        // l -> l+2 performed on thread used for original async task
        numF = numF.thenApply(l -> l + 2);
        // when numF completes,       vvvvvvv separate async task
        numF = numF.thenCompose(l -> CompletableFuture.supplyAsync(() -> l * 3));
        Consumer<Long> cL = l -> System.out.println("In lambda: "+ l);
        CompletableFuture<Void> numV = numF.thenAccept(cL);
        numV.join();
    }

}
