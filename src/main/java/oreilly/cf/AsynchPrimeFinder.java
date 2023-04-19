package oreilly.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsynchPrimeFinder {

    private AsynchPrimeFinder() {}

    public static Future<Long> getNthPrime(int n) {
        var nthF = new CompletableFuture<Long>();

        new Thread( () -> {
            Long num = Eratosthenes.findPrime(n);
            nthF.complete(num);
        } ).start();
        return nthF;
    }
}
