package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;

public class DungeonRemoveMobCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) return false;

		//Make sure the player has a selected dungeon
		if(!DungeonManager.playerHasDungeon(player)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You first must select a dungeon");
			return false;
		}

		//Get the dungeon
		Dungeon dungeon = DungeonManager.getPlayersDungeon(player);		
		String mobName = args[0];

		
		//Make sure the mob name is in use
		if(!dungeon.hasMob(mobName)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The dungeon " + dungeon.getName() + " does not have the mob " + mobName);
			return false;
		}

		//Finally everything has been checked and we can remove the mob
		DungeonManager.removeMobFromDungeon(dungeon.getMobByName(mobName), player);

		return true;
	}

}
