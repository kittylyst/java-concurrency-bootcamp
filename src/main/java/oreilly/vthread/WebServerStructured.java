package oreilly.vthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.StructuredTaskScope;

public class WebServerStructured {

    void serve(ServerSocket serverSocket) throws IOException, InterruptedException {
        try (var scope = new StructuredTaskScope<Void>()) {
            try {
                while (true) {
                    var socket = serverSocket.accept();
                    scope.fork(() -> handle(socket));
                }
            } finally {
                // If there's been an error or we're interrupted, we stop accepting
                scope.shutdown();  // Close all active connections
                scope.join();
            }
        }
    }

    private Void handle(Socket socket) {
        // Process request
        return null;
    }
}
