package oreilly.vthread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExampleVTExecutor {

    public static void main(String[] args) {
        handle(Arrays.stream(args).map(s -> {
            try {
                return new URL(s);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).toArray(URL[]::new));
    }

    static void handle(URL... urls) {
        Future<String>[] fs = new Future[urls.length];
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var i = 0; i < urls.length; i = i + 1) {
                var url = urls[i];
                fs[i] = executor.submit(() -> fetchURL(url));
            }
            var sb = new StringBuilder();
            for (var f : fs) {
                sb.append(f.get());
            }
            System.out.println(sb);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static String fetchURL(URL url) throws IOException {
        try (var in = url.openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    static <T> List<T> runAll(List<Callable<T>> tasks) throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<StructuredTaskScope.Subtask<T>> handles =
                    tasks.stream().map(scope::fork).toList();
                    scope.join().throwIfFailed();  // Propagate exception if any subtask fails

            // Here, all tasks have succeeded, so compose their results
            return handles.stream().map(StructuredTaskScope.Subtask::get).toList();
        }
    }

    static <T> T race(List<Callable<T>> tasks, Instant deadline)
            throws InterruptedException, ExecutionException, TimeoutException {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<T>()) {
            for (var task : tasks) {
                scope.fork(task);
            }
            return scope.joinUntil(deadline)
                        .result();  // Throws if none of the subtasks completed successfully
        }
    }
}