package me.CopyableCougar4.main;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FriendHider implements Runnable {

	public void run(){
		if(!FriendMe.getPlugin().getConfig().getBoolean("hide-non-friends")){
			return;
		}
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			for(OfflinePlayer possible : Bukkit.getServer().getOfflinePlayers()){
				if(!possible.isOnline()){
					continue;
				}
				if(FriendMe.isFriend(player, possible)){
					player.showPlayer(possible.getPlayer());
				} else {
					player.hidePlayer(possible.getPlayer());
				}
			}
		}
	}
	
	// Update 10x per second
	public static void schedule(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FriendMe.getPlugin(), new FriendHider(), 0, 100);
	}
	
}
