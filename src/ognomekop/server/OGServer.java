package ognomekop.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * The server that powers OG Nomèkop.
 */
public class OGServer {
    static final int PORT = 17271;

    /**
     * The main entrypoint of the application. This method launches a new socket servers and continuously listens for
     * clients on the PORT indicated above, launching a new OGClientSocket thread each time a client connects to the
     * server.
     *
     * @param args Arguments passed from the command-line, although application isn't expecting any arguments.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                new OGClientSocket(server.accept()).start();
            }
        }
    }
}
