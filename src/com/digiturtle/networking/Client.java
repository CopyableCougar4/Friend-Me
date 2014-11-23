package com.digiturtle.networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import me.CopyableCougar4.main.FriendMe;

public class Client {
	
	public Connection entryPoint;
	
	protected ArrayList<Connection> others = new ArrayList<Connection>();
	
	public Client loadConnections() {
		FileConfiguration configuration = FriendMe.getPlugin().getConfig();
		entryPoint = new Connection();
		try {
			entryPoint.ip = InetAddress.getByName(configuration.getString("server-ip"));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		entryPoint.port = 25252;
		List<String> connectionTexts = configuration.getStringList("connected");
		for (String connectionText : connectionTexts) {
			String[] data = connectionText.split(":");
			Connection connection = new Connection();
			connection.port = Integer.parseInt(data[1]);
			try {
				connection.ip = InetAddress.getByName(data[0]);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			others.add(connection);
		}
		return this;
	}

}
