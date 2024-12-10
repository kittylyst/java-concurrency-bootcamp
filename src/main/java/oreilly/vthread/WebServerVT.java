package oreilly.vthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class WebServerVT {

    void serve(ServerSocket serverSocket) throws IOException, InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            try {
                while (true) {
                    var socket = serverSocket.accept();
                    executor.submit(() -> handle(socket));
                }
            } finally {
                // If there's been an error or we're interrupted, we stop accepting
            }
        }
    }

    private void handle(Socket socket) {
        // Process request
        // HTTP handling goes here
    }
}
