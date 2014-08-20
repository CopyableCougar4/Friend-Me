package me.CopyableCougar4.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;

public class MySQL_Loader {
	
	private static Connection databaseConnection;
	
	/**
	 * Check the connection
	 */
	public static void prepare(){
		String host = FriendMe.getPlugin().getConfig().getString("mysql.host");
		String database = FriendMe.getPlugin().getConfig().getString("mysql.database");
		String username = FriendMe.getPlugin().getConfig().getString("mysql.username");
		String password = FriendMe.getPlugin().getConfig().getString("mysql.password");
		databaseConnection = MAPI.getConnection(host, database, username, password);
	}
	
	/**
	 * Unload to the mysql database
	 */
	public static void unload(){
		String table = FriendMe.getPlugin().getConfig().getString("mysql.table");
		Connection connection = databaseConnection;
		for(Friendship friendship : FriendMe.friendships){
			String sender = friendship.getSenderUUID().toString();
			String receiver = friendship.getReceiverUUID().toString();
			try {
				connection.createStatement().executeUpdate("INSERT INTO `" + table + "` (sender, receiver) VALUES ('" + sender + "', '" + receiver + "')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Unload then load
	 */
	public static void cycle(){
		prepare();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FriendMe.getPlugin(), new Runnable(){
			public void run(){
				unload();
				try {
					Thread.sleep(1000); // wait for any servers accessing the database to complete their unload
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				load();
			}
		}, 0, FriendMe.getPlugin().getConfig().getLong("reload-count"));
	}
	
	/**
	 * Load from the mysql database into the friendships
	 */
	public static void load(){
		HashMap<String, String> friendStringSet = new HashMap<String, String>();
		ArrayList<Friendship> tmp = new ArrayList<Friendship>();
		// get the resultset
		String table = FriendMe.getPlugin().getConfig().getString("mysql.table");
		Connection connection = databaseConnection;
		try {
			ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + table);
			while(resultSet.next()){
				String sender = resultSet.getString("sender");
				String receiver = resultSet.getString("receiver");
				friendStringSet.put(sender, receiver);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			MAPI.closeConnection(connection);
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		// load the results to a list of friendships
		for(String key : friendStringSet.keySet()){
			UUID sender = UUID.fromString(key);
			UUID receiver = UUID.fromString(friendStringSet.get(key));
			tmp.add(new Friendship(sender, receiver));
		}
		// swap out the arrays
		FriendMe.friendships.clear();
		FriendMe.friendships.addAll(tmp);
	}

}
