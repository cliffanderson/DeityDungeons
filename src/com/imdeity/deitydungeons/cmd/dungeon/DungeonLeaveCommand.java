package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;

public class DungeonLeaveCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(player.getVehicle() instanceof Player && DungeonManager.watchers.keySet().contains(player)) {
			//command user is riding a player, get them off
			DungeonManager.watchers.get(player).eject();
			DungeonManager.watchers.get(player).showPlayer(player);
			
			DungeonManager.watchers.remove(player);
			
		}else{
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>Error: Are you riding a player?");
			return true;
		}
		return false;
	}

}
