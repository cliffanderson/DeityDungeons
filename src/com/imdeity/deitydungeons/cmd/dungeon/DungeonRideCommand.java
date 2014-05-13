package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;

public class DungeonRideCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length == 1 && DeityDungeons.plugin.getServer().getPlayer(args[0]).isOnline()) {
			Player rider = player;
			Player fighter = DeityDungeons.plugin.getServer().getPlayer(args[0]);
			
			//ride
			fighter.setPassenger(rider);
			
			//make it so the fighter cannot see the admin
			fighter.hidePlayer(rider);
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Have fun watching <aqua>" + args[0] + " <green>fight!");			
		}
		
		return true;
	}
}
