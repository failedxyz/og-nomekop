package ognomekop.server;

import java.io.IOException;
import java.net.ServerSocket;

public class OGServer {
    static final int PORT = 17271;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                new OGClient(server.accept()).start();
            }
        }
    }
}
