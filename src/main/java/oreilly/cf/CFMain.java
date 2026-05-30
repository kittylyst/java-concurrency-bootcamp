package oreilly.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static oreilly.cf.AsynchPrimeFinder.getNthPrime;

public class CFMain {

    void main() throws ExecutionException, InterruptedException {
        final CompletableFuture<Long> numF = getNthPrime(10_000); // pthread1
        IO.println(numF.get()); // block on main

        try {
            Thread.sleep(3000); // main
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // l -> l+2 performed on thread used for original async task
        final var num2 = numF.thenApply(l -> {
            IO.println("getNthPrime " + l);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return l + 2; } ); // pool-thread-2
        IO.println(num2.get());


//        // when numF completes,       vvvvvvv separate async task
        final var num3 = num2.thenCompose(l -> CompletableFuture.supplyAsync(() -> l * 3)); // pthread3
        Consumer<Long> cL = l -> IO.println("In lambda: "+ l);
        CompletableFuture<Void> numV = num3.thenAccept(cL); // main
        numV.join();
    }

}
