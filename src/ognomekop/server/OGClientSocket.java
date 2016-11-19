package ognomekop.server;

import ognomekop.lib.packets.ByteDataInputStream;
import ognomekop.lib.packets.Packet;
import ognomekop.lib.packets.Packets;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * The handler for a client accepted by OGServer. The handler opens a continuous connection with the client, and
 * processes all data received by the client, and sent to the client.
 */
public class OGClientSocket extends Thread {
    protected Socket socket;

    /**
     * The constructor for OGClientSocket, which simply saves the socket for future interactions.
     *
     * @param client The actual socket that will be used to communicate with the client.
     */
    public OGClientSocket(Socket client) {
        this.socket = client;
    }

    /**
     * The main loop for the client handler. This will continuously read input from the client, parse the input into
     * packets, and handle each packet using its respective handler.
     */
    @Override
    public void run() {
        try (ByteDataInputStream in = new ByteDataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                int type = in.readShort();
                int length = in.readInt();
                byte[] bytes = new byte[length];
                for (int i = 0; i < length; i++) {
                    bytes[i] = in.readByte();
                }
                Class<? extends Packet> packetClass = Packets.getById(type);
                if (packetClass != null) {
                    try {
                        Packet packet = packetClass.newInstance();
                        packet.read(new ByteDataInputStream(new ByteArrayInputStream(bytes)), length);
                        handlePacket(packet);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SocketException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePacket(Packet packet) {
        // TODO
    }
}
