package oreilly.vthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;

public class ExampleStructuredCon {

    // A triple consisting of a symbol, its social media sentiment score (-1.0 to +1.0)
    // and the price change (as a percentage) in last 24 hours
    record StockTip(String symbol, double sentiment, double delta24) {}

    public static void main(String[] args) {
        for (var s : args) {
            System.out.println(makeStockTip(s));
        }
    }

    private static StockTip makeStockTip(String s) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Callable<Double> getSentiment = () -> getSentiment(s);
            StructuredTaskScope.Subtask<Double> fSentiment = scope.fork(getSentiment);

            Callable<Double> getDelta = () -> getDelta24(s);
            StructuredTaskScope.Subtask<Double> fDelta = scope.fork(getDelta);

            scope.join();
            scope.throwIfFailed();

            return new StockTip(s, fSentiment.get(), fDelta.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Double getSentiment(String s) {
        try {
            Thread.sleep((int)(100 * Math.random()));
        } catch (InterruptedException __) {
        }
        return 2 * Math.random() - 1;
    }

    private static Double getDelta24(String s) {
        try {
            Thread.sleep((int)(100 * Math.random()));
        } catch (InterruptedException __) {
        }
        return 100 * (2 * Math.random() - 1);
    }

}
