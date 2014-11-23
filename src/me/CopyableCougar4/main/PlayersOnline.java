package me.CopyableCougar4.main;

import java.sql.Connection;
import java.util.UUID;

import org.bukkit.Bukkit;

public class PlayersOnline {
	
	public static FPlayer getPlayer(UUID uuid, String username) {
		if (Bukkit.getPlayer(uuid) != null) return new FPlayer(Bukkit.getPlayer(uuid));
		return new FPlayer(username);
	}
	
	public static boolean isOnline(UUID uuid) {

		String host = FriendMe.getPlugin().getConfig().getString("mysql.host");
		String database = FriendMe.getPlugin().getConfig().getString("mysql.database");
		String username = FriendMe.getPlugin().getConfig().getString("mysql.username");
		String password = FriendMe.getPlugin().getConfig().getString("mysql.password");
		
		Connection connection = MAPI.getConnection(host, database, username, password);
		
		Object onlineStatus = MAPI.select(connection, "status", "onlineStatus", "userID>" + uuid.toString());
		if (onlineStatus == null) {
			return false;
		}
		
		return Boolean.valueOf(
				String.valueOf(MAPI.select(connection, "status", "onlineStatus", "userID>" + uuid.toString()))
			);
		
	}
	
	public static void setStatus(UUID uuid, boolean online) {
		
		String host = FriendMe.getPlugin().getConfig().getString("mysql.host");
		String database = FriendMe.getPlugin().getConfig().getString("mysql.database");
		String username = FriendMe.getPlugin().getConfig().getString("mysql.username");
		String password = FriendMe.getPlugin().getConfig().getString("mysql.password");
		
		Connection connection = MAPI.getConnection(host, database, username, password);
		
		MAPI.insert("onlineStatus", new Object[]{"userID", "status"}, new Object[]{uuid.toString(), String.valueOf(online)}, connection);
		
	}

}
