package oreilly.vthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServerScoped {

    // Dynamic or implicit scope
    private static final ScopedValue<Socket> socketSV = ScopedValue.newInstance();

    private volatile boolean running = true;

    void serve(ServerSocket serverSocket) throws IOException {
        while (running) {
            var socket = serverSocket.accept();
            ScopedValue.where(socketSV, socket)
                .run(() -> handle());
        }
    }

    public void stop() {
        running = false;
    }

    private void handle() {
        var socket = socketSV.get();
        // handle incoming traffic
        System.out.println(Thread.currentThread().getName() + ": " + socket);
    }

    public static void main(String[] args) {

    }
}
