package me.CopyableCougar4.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class FriendChat implements Listener {
	
	public FriendChat(){
		// Constructor
	}
	
	public static HashMap<Player, Boolean> friendChat = new HashMap<Player, Boolean>();
	
	public static void toggle(Player player){
		Boolean result = friendChat.get(player);
		Boolean toPut = false;
		if(result == null){
			friendChat.put(player, true);
		} else {
			if(!result){
				toPut = true;
			}
			friendChat.put(player, toPut);
		}
	}
	
	public static boolean isToggled(Player player){
		Boolean result = friendChat.get(player);
		if(result == null){
			return false;
		} else {
			return result;
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		if(isToggled(event.getPlayer())){
			// they have friend chat on
			for(Player player : Bukkit.getOnlinePlayers()){
				if(Friendship.isFriend(event.getPlayer(), new FPlayer(player))){
					continue;
				} else {
					event.getRecipients().remove(player);
				}
			}
		}
	}

}
