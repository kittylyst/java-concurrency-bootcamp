package oreilly.vthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.StructuredTaskScope;

public class WebServerStructured {

    private volatile boolean running = true;

    void serve(ServerSocket serverSocket) throws IOException, InterruptedException {
        try (var scope = StructuredTaskScope.open()) {
            try {
                while (running) {
                    var socket = serverSocket.accept();
                    scope.fork(() -> handle(socket));
                }
            } finally {
                // If there's been an error or we're interrupted, we stop accepting
                scope.close(); // Close all active connections, try to avoid waiting
                scope.join();
            }
        }
    }

    private Void handle(Socket socket) {
        // Process request
        return null;
    }

    public void shutdown() {
        running = false;
    }
}
