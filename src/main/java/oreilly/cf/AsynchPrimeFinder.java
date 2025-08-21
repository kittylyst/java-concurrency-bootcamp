package oreilly.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsynchPrimeFinder {

    private AsynchPrimeFinder() {}

    public static CompletableFuture<Long> getNthPrime(int n) {
        var nthF = new CompletableFuture<Long>();

        new Thread( () -> {
            Long num = Eratosthenes.findPrime(n);
            nthF.complete(num);
        } ).start();
        return nthF;

//        Supplier<Long> c = () -> Eratosthenes.findPrime(n);
//        return CompletableFuture.supplyAsync(c);
    }
}
