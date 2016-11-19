package ognomekop.server;

import ognomekop.lib.packets.ByteDataInputStream;
import ognomekop.lib.packets.Packet;
import ognomekop.lib.packets.Packets;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class OGClient extends Thread {
    protected Socket socket;

    public OGClient(Socket client) {
        this.socket = client;
    }

    @Override
    public void run() {
        String line;
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
                        Packet packet = (Packet) packetClass.newInstance();
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
