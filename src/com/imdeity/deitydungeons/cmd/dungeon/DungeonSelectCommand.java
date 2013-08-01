package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;

public class DungeonSelectCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {		
		if(args.length != 1) return false;
		String dungeon = args[0];

		//Make sure there is a dungeon with the name
		if(!DungeonManager.dungeonExists(dungeon)) {
			//There is no dungeon with that name
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "[DeityDungeons]", "The dungeon " + dungeon + " does not exist");
			return true; 
		}
		
		//The put method will automatically replace an existing pair in the map
		DungeonManager.selectedDungeons.put(player, DungeonManager.getDungeonByName(dungeon));
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>You have selected the dungeon <aqua>" + dungeon);

		return true;
	}

}
