package oreilly.vthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class WebServerVT {

    private volatile boolean running = true;

    void serve(ServerSocket serverSocket) throws IOException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            while (running) {
                var socket = serverSocket.accept();
                executor.submit(() -> handle(socket));
            }
        }
    }

    public void stop() {
        running = false;
    }

    private void handle(Socket socket) {
        // Process request
        // HTTP handling goes here
    }
}
