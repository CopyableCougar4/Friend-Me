package me.CopyableCougar4.main;

import org.bukkit.entity.Player;

public class FPlayer {

	public Player player;
	public String username;
	
	public FPlayer(String username) {
		this((Player) null);
		this.username = username;
	}
	
	public FPlayer(Player player) {
		this.player = player;
	}
	
}
