package oreilly.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static oreilly.cf.AsynchPrimeFinder.getNthPrime;

public class PrimeMain {

    public static void main(String[] args) {
        var numF = getNthPrime(10_000);
        var theNum = -1L;
        try {
            while ( !numF.isDone() ) {
                System.out.println("Doing something else");
                Thread.sleep(500);
            }
            theNum = numF.get();
            System.out.println("Fetched: " + theNum);
        } catch (ExecutionException ee ) {
            // ..
        } catch (InterruptedException ie ) {
            // ..
        }
    }

}
