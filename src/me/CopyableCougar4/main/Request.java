package me.CopyableCougar4.main;

import java.util.UUID;

import me.CopyableCougar4.main.FriendMe.Type;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.digiturtle.networking.ClientAdapter;
import com.digiturtle.networking.ClientHandler;
import com.digiturtle.networking.Packet;

public class Request {

	public static void initialize() {
		ClientHandler.getHandler().attachAdapter(new ClientAdapter() {
			public void handlePacket(Packet packet) {
				if (packet.data.contains("-+")) {
					String[] users = packet.data.split("-+");
					accept(PlayersOnline.getPlayer(UUID.fromString(users[0]), users[1]).player, PlayersOnline.getPlayer(UUID.fromString(users[0]), users[1]));
				}
				if (packet.data.contains("--")) {
					String[] users = packet.data.split("--");
					deny(PlayersOnline.getPlayer(UUID.fromString(users[0]), users[1]).player, PlayersOnline.getPlayer(UUID.fromString(users[0]), users[1]));
				}
				if (packet.data.contains("-=")) {
					String[] users = packet.data.split("-=");
					remove(PlayersOnline.getPlayer(UUID.fromString(users[0]), users[1]).player, PlayersOnline.getPlayer(UUID.fromString(users[0]), users[1]));
				}
			}
		});
	}
	
	public static void forward(UUID uuid, String username, String splitter) {
		Packet packet = new Packet();
		packet.data = uuid.toString() + splitter + username;
		ClientHandler.getHandler().sendPacket(packet);
	}
	
	public static void send(Player sender, FPlayer to){
		if(to == null){
			sender.sendMessage(ChatColor.YELLOW + "Player not found.");
			return;
		}
		if (to.player == null) {
			// this request needs to be sent over a network
			Packet packet = new Packet();
			packet.data = MineUUID.getUUID(sender.getName()) + "->" + to.username;
			ClientHandler.getHandler().sendPacket(packet);
		}
		Friendship request = Friendship.create(sender, to.player);
		FriendMe.friendships.add(request);
		FriendMe.sendMessage(to.player, Type.RECEIVED_REQUEST, sender);
		FriendMe.sendMessage(sender, Type.SENT_REQUEST, to.player);
	}
	
	public static void accept(Player accept, FPlayer from){
		Friendship current = Friendship.bySender(from.player);
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(from) && f.getReceiver().equals(accept)){
				FriendMe.sendMessage(accept, Type.ALREADY_FRIENDS, from.player);
				return;
			}
		}
		if(current == null){
			FriendMe.sendMessage(accept, Type.REQUEST_NOT_FOUND, from.player);
			return;
		}
		current.accept();
		FriendMe.sendMessage(accept, Type.RECEIVE_ACCEPT, from.player);
		FriendMe.sendMessage(from.player, Type.SENT_ACCEPT, accept);
	}
	
	public static void deny(Player deny, FPlayer from){
		Friendship current = Friendship.bySender(from.player);
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(from) && f.getReceiver().equals(deny)){
				FriendMe.sendMessage(deny, Type.ALREADY_FRIENDS, from.player);
				return;
			}
		}
		if(current == null){
			FriendMe.sendMessage(deny, Type.REQUEST_NOT_FOUND, from.player);
			return;
		}
		current.deny();
		FriendMe.sendMessage(deny, Type.RECEIVE_DENY, from.player);
		FriendMe.sendMessage(from.player, Type.SENT_DENY, deny);
	}
	
	public static void remove(Player sender, FPlayer remove){
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(sender) && f.getReceiver().equals(remove.player)){
				f.delete();
				FriendMe.friendships.remove(f);
				return;
			}
			if(f.getSender().equals(remove.player) && f.getReceiver().equals(sender)){
				f.delete();
				FriendMe.friendships.remove(f);
				return;
			}
		}
	}
	
}
