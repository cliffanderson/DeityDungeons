package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

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
		if(DungeonManager.dungeonExists(dungeon)) return false; //There is already a dungeon with that name
		
		//The put method will automatically replace an existing pair in the map
		DungeonManager.selectedDungeons.put(player, DungeonManager.getDungeonByName(dungeon));

		return true;
	}

}
