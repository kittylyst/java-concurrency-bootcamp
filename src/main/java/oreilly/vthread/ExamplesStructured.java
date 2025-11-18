package oreilly.vthread;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.StructuredTaskScope.Joiner.allSuccessfulOrThrow;

public final class ExamplesStructured {
    private ExamplesStructured() {}

    static <T> List<T> runAll(List<Callable<T>> tasks) {
        // All forked subtasks must succeed
        try (var scope = StructuredTaskScope.open(allSuccessfulOrThrow())) {
            List<StructuredTaskScope.Subtask<T>> handles =
                    tasks.stream().map(scope::fork).toList();

            // Here, all tasks have succeeded, so compose their results
            return handles.stream().map(StructuredTaskScope.Subtask::get).toList();
        }
    }

    static <T> T race(Collection<Callable<T>> tasks,
                      ThreadFactory factory,
                      Duration timeout)
            throws InterruptedException
    {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<T>anySuccessfulResultOrThrow(),
                cf -> cf.withThreadFactory(factory)
                        .withTimeout(timeout))) {
            tasks.forEach(scope::fork);
            return scope.join();
        }
    }

}
