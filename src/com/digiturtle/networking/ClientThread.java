package com.digiturtle.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.bukkit.Bukkit;

public class ClientThread implements Runnable {
	
	public DatagramSocket socket;
	public static volatile boolean ACTIVE = false;
	private ClientHandler handler;
	
	public ClientThread(Client client, ClientHandler handler) {
		try {
			socket = new DatagramSocket(client.entryPoint.port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.handler = handler;
	}

	public void run() {
		ACTIVE = true;
		while (ACTIVE) {
			byte[] block = new byte[256];
			DatagramPacket packet = new DatagramPacket(block, 256);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Packet nPacket = new Packet();
			nPacket.data = new String(packet.getData());
			handler.handlePacket(nPacket);
			ClientHandler._handler = handler;
		}
	}

}
