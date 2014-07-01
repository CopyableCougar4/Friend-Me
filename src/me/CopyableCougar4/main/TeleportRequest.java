package me.CopyableCougar4.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportRequest {

	public Player sent, receive;
	
	public TeleportRequest(Player from, Player to){
		this.sent = from;
		this.receive = to;
	}
	
	public Player getFrom(){
		return this.sent;
	}
	
	public Player getTo(){
		return this.receive;
	}
	
	public void accept(){
		this.getFrom().teleport(this.getTo().getLocation());
		this.getFrom().sendMessage(ChatColor.YELLOW + this.getTo().getName() + " accepted your request!");
		this.getTo().sendMessage(ChatColor.YELLOW + "You accepted " + this.getFrom().getName() + "'s request");
		FriendMe.teleports.remove(this);
	}
	
	public void send(){
		FriendMe.teleports.add(this);
	}
	
	public void deny(){
		FriendMe.teleports.remove(this);
		this.getFrom().sendMessage(ChatColor.YELLOW + this.getTo().getName() + " denied your request!");
		this.getTo().sendMessage(ChatColor.YELLOW + "You denied " + this.getFrom().getName() + "'s request");
	}
	
	public static TeleportRequest create(String player, CommandSender sender){
		Player from = Bukkit.getPlayer(sender.getName());
		Player to = Bukkit.getPlayer(player);
		TeleportRequest request = new TeleportRequest(from, to);
		return request;
	}
	
	public static TeleportRequest byReceive(Player receive){
		for(TeleportRequest request : FriendMe.teleports){
			if(request.getTo().equals(receive))
				return request;
		}
		return null;
	}
	
}
