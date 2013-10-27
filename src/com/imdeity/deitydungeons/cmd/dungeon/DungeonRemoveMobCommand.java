package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;

public class DungeonRemoveMobCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		//dungeon removemob dungeon id
		//dungeon removemob id

		if(args.length == 2 && DeityDungeons.isInt(args[1]) && DungeonManager.dungeonExists(args[0])) {
			//Get the dungeon
			Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);		
			int id = Integer.parseInt(args[1]);


			//Make sure the dungeon has the mob
			if(!dungeon.hasMob(id)) {
				DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "The dungeon " + dungeon.getName() + " does not have a mob with id " + id);
				return true;
			}

			//Finally everything has been checked and we can remove the mob
			DungeonManager.removeMobFromDungeon(dungeon.getMobByID(id), player);

			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>The mob with id " + args[1] + " has been removed from the dungeon " + dungeon.getName());

			return true;
		}else if(args.length == 1 && DeityDungeons.isInt(args[0]) && DungeonManager.playerHasDungeon(player)) {
			//Get the dungeon
			Dungeon dungeon = DungeonManager.getPlayersDungeon(player);		
			int id = Integer.parseInt(args[0]);


			//Make sure the dungeon has the mob
			if(!dungeon.hasMob(id)) {
				DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "The dungeon " + dungeon.getName() + " does not have a mob with id " + id);
				return true;
			}

			//Finally everything has been checked and we can remove the mob
			DungeonManager.removeMobFromDungeon(dungeon.getMobByID(id), player);

			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>The mob with id " + args[0] + " has been removed from the dungeon " + dungeon.getName());

			return true;
		}else{
			DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "Usage: /dungeon removemob <dungeon> <id>");
			return true;
		}
	}
}
