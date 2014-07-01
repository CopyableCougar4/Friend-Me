package me.CopyableCougar4.main;

import me.CopyableCougar4.main.FriendMe.Type;
import me.CopyableCougar4.main.ListData.Direction;
import me.CopyableCougar4.main.ListData.TypeD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendCommand implements CommandExecutor {

	public FriendCommand(){
		// Constructor
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("This command cannot be issued via console!");
			return false;
		}
		if(args.length == 0){
			HelpPage.sendMessage((Player)sender);
			return false;
		}
		if(args[0].equalsIgnoreCase("add")){
			if(args.length > 1)
				Request.send((Player)sender, Bukkit.getPlayer(args[0]));
			else
				FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
		}
		else if(args[0].equalsIgnoreCase("deny")){
			if(args.length > 1)
				Request.deny((Player)sender, Bukkit.getPlayer(args[0]));
			else
				FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
		}
		else if(args[0].equalsIgnoreCase("accept")){
			if(args.length > 1)
				Request.accept((Player)sender, Bukkit.getPlayer(args[0]));
			else
				FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
		}
		else if(args[0].equalsIgnoreCase("remove")){
			if(args.length > 1)
				Request.remove((Player)sender, Bukkit.getPlayer(args[0]));
			else
				FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
		}
		else if(args[0].equalsIgnoreCase("tp")){
			if(!sender.hasPermission("friendme.teleport")){
				sender.sendMessage(ChatColor.YELLOW + "You don't have the permission " + ChatColor.RED + "friendme.teleport");
				return false;
			}
			if(args.length < 2){
				FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
			} else {
				if(!Friendship.isFriend(Bukkit.getPlayer(sender.getName()), Bukkit.getPlayer(args[1]))){
					sender.sendMessage(ChatColor.YELLOW + "You can only sent teleport requests to friends!");
					return false;
				}
				if(args[0].equalsIgnoreCase("send")){
					TeleportRequest request = TeleportRequest.create(args[1], sender);
					request.send();
				}
				else if(args[0].equalsIgnoreCase("accept")){
					TeleportRequest request = TeleportRequest.byReceive(Bukkit.getPlayer(sender.getName()));
					request.accept();
				}
				else if(args[0].equalsIgnoreCase("deny")){
					TeleportRequest request = TeleportRequest.byReceive(Bukkit.getPlayer(sender.getName()));
					request.deny();
				}
			}
		}
		else if(args[0].equalsIgnoreCase("chat")){
			FriendChat.toggle((Player)sender);
			sender.sendMessage(ChatColor.YELLOW + "Friend chat toggled");
		}
		else if(args[0].equalsIgnoreCase("list")){
			if(args.length > 3){
				if(args[1].equalsIgnoreCase("pending")){
					if(args[2].equalsIgnoreCase("sent")){
						ListData.list(TypeD.PENDING, (Player)sender, Direction.SEND);
					}
					else if(args[2].equalsIgnoreCase("receive")){
						ListData.list(TypeD.PENDING, (Player)sender, Direction.RECEIVE);
					} 
					else {
						FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
					}
				} 
				else if(args[1].equalsIgnoreCase("accept")){
					if(args[2].equalsIgnoreCase("sent")){
						ListData.list(TypeD.ACCEPTED, (Player)sender, Direction.SEND);
					}
					else if(args[2].equalsIgnoreCase("receive")){
						ListData.list(TypeD.ACCEPTED, (Player)sender, Direction.RECEIVE);
					} 
					else {
						FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
					}
					
				}
				else if(args[1].equalsIgnoreCase("deny")){
					if(args[2].equalsIgnoreCase("sent")){
						ListData.list(TypeD.DENIED, (Player)sender, Direction.SEND);
					}
					else if(args[2].equalsIgnoreCase("receive")){
						ListData.list(TypeD.DENIED, (Player)sender, Direction.RECEIVE);
					} 
					else {
						FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
					}
					
				} 
				else {
					ListData.list(TypeD.ACCEPTED, (Player)sender, Direction.BOTH);
				}
			} else {
				FriendMe.sendMessage((Player)sender, Type.INVALID_COMMAND, null);
			}
		} 
		else {
			HelpPage.sendMessage((Player)sender);
		}
		return false;
	}
	
}
