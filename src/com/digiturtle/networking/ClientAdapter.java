package com.digiturtle.networking;

import java.net.InetAddress;

public interface ClientAdapter {
	
	public void handlePacket(Packet packet);

}
