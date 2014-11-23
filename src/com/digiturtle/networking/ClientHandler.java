package com.digiturtle.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ClientHandler {
	
	private ArrayList<ClientAdapter> adapters = new ArrayList<ClientAdapter>();
	private Client client;
	private ClientThread thread;
	
	protected static ClientHandler _handler;

	public ClientHandler(Client client) {
		this.client = client;
		client.loadConnections();
	}
	
	public static ClientHandler getHandler() {
		return _handler;
	}
	
	public void sendPacket(Packet packet) {
		for (Connection connection : client.others) {
			sendPacket(packet, connection.ip, connection.port);
		}
	}
	
	public void sendPacket(Packet packet, InetAddress ip, int port) {
		DatagramPacket dPacket = new DatagramPacket(packet.data.getBytes(), packet.data.getBytes().length, ip, port);
		try {
			thread.socket.send(dPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientHandler attachAdapter(ClientAdapter adapter) {
		adapters.add(adapter);
		return this;
	}
	
	public void start() {
		thread = new ClientThread(client, this);
		new Thread(thread).start();
	}
	
	public void handlePacket(Packet packet) {
		for (int index = 0; index < adapters.size(); index++) {
			adapters.get(index).handlePacket(packet);
		}
	}
	
}
