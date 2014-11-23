package me.CopyableCougar4.main;

import me.CopyableCougar4.main.FriendMe.Type;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FriendMeEvents implements Listener {

	public FriendMeEvents(){
		// Constructor
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent evt){
		if(!FriendMe.friendlyFire)
			return;
		Entity cause = evt.getDamager();
		Entity player = evt.getEntity();
		if(player.getType().equals(EntityType.PLAYER) && cause.getType().equals(EntityType.PLAYER)){
			Player playerCause = (Player)cause;
			Player damageCause = (Player)player;
			for(Friendship f : FriendMe.friendships){
				if(f.getSender().equals(playerCause) && f.getReceiver().equals(damageCause)){
					evt.setCancelled(true);
					FriendMe.sendMessage(damageCause, Type.FRIENDLY_FIRE, null);
				}
				else if(f.getReceiver().equals(playerCause) && f.getSender().equals(damageCause)){
					evt.setCancelled(true);
					FriendMe.sendMessage(damageCause, Type.FRIENDLY_FIRE, null);
				}
			}
		} else {
			return;
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt){
		PlayersOnline.setStatus(MineUUID.getUUID(evt.getPlayer().getName()), true);
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.equals(evt.getPlayer()))
				continue;
			for(Friendship f : FriendMe.friendships){
				if(f.getSender() != null){
					if(f.getSender().equals(evt.getPlayer())){
						if(f.getReceiver() != null)
							FriendMe.sendMessage(f.getReceiver(), Type.FRIEND_ON, evt.getPlayer());
						continue;
					}
				}
				if(f.getReceiver() != null){
					if(f.getReceiver().equals(evt.getPlayer())){
						if(f.getSender() != null)
							FriendMe.sendMessage(f.getSender(), Type.FRIEND_ON, evt.getPlayer());
						continue;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent evt){
		PlayersOnline.setStatus(MineUUID.getUUID(evt.getPlayer().getName()), false);
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.equals(evt.getPlayer()))
				continue;
			for(Friendship f : FriendMe.friendships){
				if(f.getSender().equals(evt.getPlayer())){
					FriendMe.sendMessage(f.getReceiver(), Type.FRIEND_OFF, evt.getPlayer());
					break;
				}
				else if(f.getReceiver().equals(evt.getPlayer())){
					FriendMe.sendMessage(f.getSender(), Type.FRIEND_OFF, evt.getPlayer());
					break;
				}
			}
		}
	}
	
}
