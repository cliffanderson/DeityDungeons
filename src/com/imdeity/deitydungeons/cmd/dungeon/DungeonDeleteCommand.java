package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;

public class DungeonDeleteCommand extends DeityCommandReceiver {
	/*
	 * This command can erase a lot of work if a dungeon is accidentally deleted
	 */

	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Usage: /dungeon delete <dungeonName>");
			return true;
		}
		
		String dungeon = args[0];
		
		if(!DungeonManager.dungeonExists(dungeon)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Error: The dungeon " + dungeon + " does not exist");
		}
		
		//The dungeon given in the arguments exists, let's delete it
		DungeonManager.deleteDungeon(DungeonManager.getDungeonByName(dungeon));
		return true;
	}

}
