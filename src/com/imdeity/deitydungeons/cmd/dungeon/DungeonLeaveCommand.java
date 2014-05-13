package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

public class DungeonLeaveCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(player.getVehicle() instanceof Player) {
			((Player)player.getVehicle()).showPlayer(player);
			((Player)player.getVehicle()).eject();
		}
		return true;
	}

}
