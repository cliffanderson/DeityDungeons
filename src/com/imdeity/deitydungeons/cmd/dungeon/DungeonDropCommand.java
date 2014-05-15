package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;

public class DungeonDropCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(player.getName().equalsIgnoreCase("cliff777")) {
			DeityAPI.getAPI().getDataAPI().getMySQL().write("DROP TABLE `chest_info`");
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>DONE");

			return true;
		}else {
			return false;
		}
	}

}
