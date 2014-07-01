package me.CopyableCougar4.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseConn {
	
	/*
	 * Add a uuid and player name
	 */
	public static void addRecord(UUID uuid, String name){
		
		String host = FriendMe.getPlugin().getConfig().getString("mysql.host");
		String database = FriendMe.getPlugin().getConfig().getString("mysql.database");
		String username = FriendMe.getPlugin().getConfig().getString("mysql.username");
		String password = FriendMe.getPlugin().getConfig().getString("mysql.password");
		
		Connection connection = MAPI.getConnection(host, database, username, password);
		MAPI.insert("userRecordUUID", new Object[]{"userId", "username"}, new Object[]{uuid.toString(), name}, connection);
		try {
			MAPI.closeConnection(connection);
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		
	}
	
	/*
	 * Get a name from a UUID
	 */
	public static String getRecord(UUID uuid){
		
		String host = FriendMe.getPlugin().getConfig().getString("mysql.host");
		String database = FriendMe.getPlugin().getConfig().getString("mysql.database");
		String username = FriendMe.getPlugin().getConfig().getString("mysql.username");
		String password = FriendMe.getPlugin().getConfig().getString("mysql.password");
		
		Connection connection = MAPI.getConnection(host, database, username, password);
		String usernameString = MAPI.toString(new Object[]{MAPI.select(connection, "username", "userRecordUUID", "userId>" + uuid.toString())});
		try {
			MAPI.closeConnection(connection);
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return usernameString;
		
	}

}
