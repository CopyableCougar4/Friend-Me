package me.CopyableCougar4.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpPage {
	
	public static void sendMessage(Player player){
		String prefix = ChatColor.DARK_AQUA + "[FriendMe] " + ChatColor.GOLD;
		String message = ChatColor.DARK_AQUA + "[FriendMe] " + ChatColor.GOLD + "Help Page - Anything in curly brackets is optional.";
		String[] commands  = {"/friend add <player>", "/friend remove <player>", "/friend accept <player>", 
				"/friend deny <player>", "/friend list {<accept/deny/pending> <sent/receive>}", "/friend chat"};
		String[] desc = {"Add a friend", "Remove a friend", "Accept a friend request", "Deny a friend request", "List friends or active requests",
				"Toggle friend chat"};
		Integer i = 0;
		player.sendMessage(message);
		for(String command : commands){
			player.sendMessage(prefix + command + " " + ChatColor.YELLOW + desc[i]);
			i++;
		}
	}

}
