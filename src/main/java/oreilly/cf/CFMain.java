package oreilly.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static oreilly.cf.AsynchPrimeFinder.getNthPrime;

public class CFMain {

    void main() throws ExecutionException, InterruptedException {
        CompletableFuture<Long> numF = getNthPrime(10_000); // main + pthread1
        // l -> l+2 performed on thread used for original async task
        numF = numF.thenApply(l -> {
            IO.println("getNthPrime " + l);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return l + 2; } ); // pthread1


        // when numF completes,       vvvvvvv separate async task
        numF = numF.thenCompose(l -> CompletableFuture.supplyAsync(() -> l * 3)); // pthread2
        Consumer<Long> cL = l -> IO.println("In lambda: "+ l);
        CompletableFuture<Void> numV = numF.thenAccept(cL); // pthread2
        numV.join();
    }

}
