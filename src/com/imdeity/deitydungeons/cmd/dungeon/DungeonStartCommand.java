package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonStartCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) return false;
		
		if(!DungeonManager.dungeonExists(args[0])) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "[DeityDungeons]", "The dungeon " + args[0] + " is invalid");
			return true;
		}
		
		DeityDungeons.runningDungeons.add(new RunningDungeon(DungeonManager.getDungeonByName(args[0]), player));
		
		
		
		
		return false;
	}

}
