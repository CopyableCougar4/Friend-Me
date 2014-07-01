package me.CopyableCougar4.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.CopyableCougar4.main.Friendship.Status;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ListData {

	public static void list(TypeD t, Player p, Direction d){
		String message = ChatColor.DARK_AQUA + "[FriendMe] Friends " + ChatColor.GOLD;
		finalize(t, p, message, d);
	}
	
	public enum TypeD {
		PENDING, ACCEPTED, DENIED;
	}
	
	public enum Direction {
		SEND, RECEIVE, BOTH;
	}
	
	public static String convertList(List<Friendship> friends, Player p){
		String converted = "";
		Integer i = 0;
		Friendship[] data = (Friendship[]) friends.toArray();
		for(Friendship f : data){
			if(f.getSender().equals(p)){
				Player friend = f.getReceiver();
				if(i == 0){
					if(Arrays.asList(Bukkit.getOnlinePlayers()).contains(friend)){
						converted += ChatColor.GREEN + friend.getName();
					} else {
						converted += ChatColor.RED + friend.getName();
					}
				} else {
					if(Arrays.asList(Bukkit.getOnlinePlayers()).contains(friend)){
						converted += ", " + ChatColor.GREEN + friend.getName();
					} else {
						converted += ", " + ChatColor.RED + friend.getName();
					}
				}
			} else {
				Player friend = f.getSender();
				if(i == 0){
					if(Arrays.asList(Bukkit.getOnlinePlayers()).contains(friend)){
						converted += ChatColor.GREEN + friend.getName();
					} else {
						converted += ChatColor.RED + friend.getName();
					}
				} else {
					if(Arrays.asList(Bukkit.getOnlinePlayers()).contains(friend)){
						converted += ", " + ChatColor.GREEN + friend.getName();
					} else {
						converted += ", " + ChatColor.RED + friend.getName();
					}
				}
			}
			i++;
		}
		return converted;
	}
	
	public static void finalize(TypeD t, Player p, String message, Direction d){
		List<Friendship> matches = new ArrayList<Friendship>();
		if(t.equals(TypeD.PENDING)){
			if(d.equals(Direction.SEND)){
				for(Friendship f : FriendMe.friendships){
					if(f.getSender().equals(p) && f.getStatus().equals(Status.PENDING)){
						matches.add(f);
					}
				}
			} 
			else if(d.equals(Direction.RECEIVE)){
				for(Friendship f : FriendMe.friendships){
					if(f.getReceiver().equals(p) && f.getStatus().equals(Status.PENDING)){
						matches.add(f);
					}
				}
			}
			else if(d.equals(Direction.BOTH)){
				for(Friendship f : FriendMe.friendships){
					if(f.getReceiver().equals(p) && f.getStatus().equals(Status.PENDING) || f.getSender().equals(p) && f.getStatus().equals(Status.PENDING)){
						matches.add(f);
					}
				}
			}
		}
		else if(t.equals(TypeD.ACCEPTED)){
			if(d.equals(Direction.SEND)){
				for(Friendship f : FriendMe.friendships){
					if(f.getSender().equals(p) && f.getStatus().equals(Status.ACCEPTED)){
						matches.add(f);
					}
				}
			} 
			else if(d.equals(Direction.RECEIVE)){
				for(Friendship f : FriendMe.friendships){
					if(f.getReceiver().equals(p) && f.getStatus().equals(Status.ACCEPTED)){
						matches.add(f);
					}
				}
			}
			else if(d.equals(Direction.BOTH)){
				for(Friendship f : FriendMe.friendships){
					if(f.getReceiver().equals(p) && f.getStatus().equals(Status.PENDING) || f.getSender().equals(p) && f.getStatus().equals(Status.ACCEPTED)){
						matches.add(f);
					}
				}
			}
		}
		else if(t.equals(TypeD.DENIED)){
			if(d.equals(Direction.SEND)){
				for(Friendship f : FriendMe.friendships){
					if(f.getSender().equals(p) && f.getStatus().equals(Status.DENIED)){
						matches.add(f);
					}
				}
			} 
			else if(d.equals(Direction.RECEIVE)){
				for(Friendship f : FriendMe.friendships){
					if(f.getReceiver().equals(p) && f.getStatus().equals(Status.DENIED)){
						matches.add(f);
					}
				}
			}
			else if(d.equals(Direction.BOTH)){
				for(Friendship f : FriendMe.friendships){
					if(f.getReceiver().equals(p) && f.getStatus().equals(Status.PENDING) || f.getSender().equals(p) && f.getStatus().equals(Status.DENIED)){
						matches.add(f);
					}
				}
			}
		}
		String list = convertList(matches, p);
		message += t.toString() + "(" + matches.size() + "): " + list;
		p.sendMessage(message);
	}
	
}
