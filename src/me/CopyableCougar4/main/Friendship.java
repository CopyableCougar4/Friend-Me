package me.CopyableCougar4.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Friendship {

	public UUID senderUUID, receiverUUID;
	public Status requestStatus;
	
	public Friendship(Player sent, Player receive){
		this.senderUUID = MineUUID.getUUID(sent.getName());
		this.receiverUUID = MineUUID.getUUID(receive.getName());
		this.requestStatus = Status.PENDING;
	}
	
	public Friendship(UUID sentUUID, UUID receiveUUID){
		
		this.senderUUID = sentUUID;
		this.receiverUUID = receiveUUID;
		this.requestStatus = Status.PENDING;
		
	}
	
	public enum Status {
		PENDING,
		ACCEPTED,
		DENIED,
		REMOVED;
	}
	
	public void accept(){
		this.requestStatus = Status.ACCEPTED;
	}
	
	public void deny(){
		this.requestStatus = Status.DENIED;
	}
	
	public void delete(){
		this.requestStatus = Status.REMOVED;
	}
	
	public UUID getSenderUUID(){
		return senderUUID;
	}
	
	public UUID getReceiverUUID(){
		return receiverUUID;
	}
	
	public Player getSender(){
		String username = DatabaseConn.getRecord(this.senderUUID);
		Player sender = Bukkit.getPlayer(MineUUID.getUUID(username));
		return sender;
	}
	
	public Player getReceiver(){
		String username = DatabaseConn.getRecord(this.receiverUUID);
		Player receiver = Bukkit.getPlayer(MineUUID.getUUID(username));
		return receiver;
	}
	
	public static Friendship create(Player sent, Player receive){
		Friendship f = new Friendship(sent, receive);
		return f;
	}
	
	public static Friendship bySender(Player sender){
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(sender))
				return f;
		}
		return null;
	}
	
	public Status getStatus(){
		return this.requestStatus;
	}
	
	public static boolean isFriend(Player test, Player check){
		for(Friendship f : FriendMe.friendships){
			if(f.getSender().equals(test) && f.getReceiver().equals(check))
				return true;
			if(f.getSender().equals(check) && f.getReceiver().equals(test))
				return true;
		}
		return false;
	}
	
}
