package oreilly.vthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class WebServerVT {

    private volatile boolean running = true;

    void serve(ServerSocket serverSocket) throws IOException, InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            try {
                while (running) {
                    var socket = serverSocket.accept();
                    executor.submit(() -> handle(socket));
                }
            } finally {
                // If there's been an error or we're interrupted, we stop accepting
            }
        }
    }

    public void shutdown() {
        running = false;
    }

    private void handle(Socket socket) {
        // Process request
        // HTTP handling goes here
    }
}
