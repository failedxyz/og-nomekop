package ognomekop.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OGServer {
    static final int PORT = 17271;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket client = server.accept();
                new OGServerThread(client).start();
            }
        }
    }
}
