package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;

public class DungeonCreateCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		//Basic checking
		if(args.length != 1) return false;
	//	if(!DeityDungeons.isInt(args[1])) return false;
		String dungeonName = args[0];
		
		//Make sure there isn't an existing dungeon
		if(DungeonManager.dungeonExists(dungeonName)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeon", "The dungeon " + dungeonName + " already exists");
			return true;
		}
		
		//Make the dungeon
		DungeonManager.createDungeon(player, dungeonName);
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The dungeon " + dungeonName + " has been created. " +
				"The player spawn point has been set to your location. Please use the <red>/dungeon setfinish<white> command to set the finish point of the dungeon");
		
		return true;
	}

}
