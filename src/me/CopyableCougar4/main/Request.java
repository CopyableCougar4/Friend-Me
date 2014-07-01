package me.CopyableCougar4.main;

import me.CopyableCougar4.main.FriendMe.Type;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Request {
	
	public static void send(Player sender, Player to){
		if(to == null){
			sender.sendMessage(ChatColor.YELLOW + "Player not found.");
			return;
		}
		Friendship request = Friendship.create(sender, to);
		FriendMe.friendships.add(request);
		FriendMe.sendMessage(to, Type.RECEIVED_REQUEST, sender);
		FriendMe.sendMessage(sender, Type.SENT_REQUEST, to);
	}
	
	public static void accept(Player accept, Player from){
		Friendship current = Friendship.bySender(from);
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(from) && f.getReceiver().equals(accept)){
				FriendMe.sendMessage(accept, Type.ALREADY_FRIENDS, from);
				return;
			}
		}
		if(current == null){
			FriendMe.sendMessage(accept, Type.REQUEST_NOT_FOUND, from);
			return;
		}
		current.accept();
		FriendMe.sendMessage(accept, Type.RECEIVE_ACCEPT, from);
		FriendMe.sendMessage(from, Type.SENT_ACCEPT, accept);
	}
	
	public static void deny(Player deny, Player from){
		Friendship current = Friendship.bySender(from);
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(from) && f.getReceiver().equals(deny)){
				FriendMe.sendMessage(deny, Type.ALREADY_FRIENDS, from);
				return;
			}
		}
		if(current == null){
			FriendMe.sendMessage(deny, Type.REQUEST_NOT_FOUND, from);
			return;
		}
		current.deny();
		FriendMe.sendMessage(deny, Type.RECEIVE_DENY, from);
		FriendMe.sendMessage(from, Type.SENT_DENY, deny);
	}
	
	public static void remove(Player sender, Player remove){
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(sender) && f.getReceiver().equals(remove)){
				f.delete();
				FriendMe.friendships.remove(f);
				return;
			}
			if(f.getSender().equals(remove) && f.getReceiver().equals(sender)){
				f.delete();
				FriendMe.friendships.remove(f);
				return;
			}
		}
	}
	
}
