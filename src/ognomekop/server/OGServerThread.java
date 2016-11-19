package ognomekop.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class OGServerThread extends Thread {
    protected Socket socket;

    public OGServerThread(Socket client) {
        this.socket = client;
    }

    @Override
    public void run() {
        String line;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                line = in.readLine();
                out.writeBytes(line + "\n");
            }
        } catch (SocketException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
