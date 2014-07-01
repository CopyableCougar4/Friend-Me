package me.CopyableCougar4.main;

import me.CopyableCougar4.main.Friendship.Status;
import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FriendList {

	public static void displayOnline(Player p, String format){
		Integer amount = 0;
		for(Friendship f : FriendMe.friendships){
			if(f.getSender() == null || f.getReceiver() == null || f.getStatus() == null)
				continue;
			if(f.getSender().equals(p) && f.getStatus().equals(Status.ACCEPTED)){
				amount++;
				continue;
			}
			if(f.getReceiver().equals(p) && f.getStatus().equals(Status.ACCEPTED)){
				amount++;
				continue;
			}
		}
		String message = ChatColor.translateAlternateColorCodes('&', format).replaceAll("%on%", amount.toString());
		Plugin bar = Bukkit.getPluginManager().getPlugin("BarAPI");
		if(bar != null)
			if(FriendMe.pl.getConfig().getStringList("worlds").contains(p.getWorld().getName()))
				BarAPI.setMessage(p, message);
	}
	
}
