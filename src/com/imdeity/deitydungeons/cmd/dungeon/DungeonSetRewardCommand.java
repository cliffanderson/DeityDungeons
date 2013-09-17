package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;

public class DungeonSetRewardCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length < 1) {
			return false;
		}

		//Make sure the player has a selected dungeon
		if(!DungeonManager.playerHasDungeon(player)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You first must select a dungeon");
			return true;
		}

		if(DeityDungeons.isInt(args[0])) {
			DungeonManager.setDungeonReward(DungeonManager.getPlayersDungeon(player), Integer.parseInt(args[0]));
		}

		return true;
	}

}
