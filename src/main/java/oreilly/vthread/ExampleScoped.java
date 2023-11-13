package oreilly.vthread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleScoped {

    public static final ScopedValue<Socket> socketSV = ScopedValue.newInstance();

    void serve(ServerSocket serverSocket) throws IOException {
        while (true) {
            var socket = serverSocket.accept();
            ScopedValue.where(socketSV, socket)
                .run(() -> handle());
        }
    }

    private void handle() {
        var socket = socketSV.get();
        // handle incoming traffic
    }

    public static void main(String[] args) {

    }
}
