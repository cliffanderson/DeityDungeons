package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonStopCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>Usage: /dungeon stop <dungeon name>");
			return true;
		}
		
		if(!DungeonManager.dungeonExists(args[0])) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "<red>DeityDungeons", "The dungeon " + args[0] + " does not exist");
			return true;
		}
		
		for(RunningDungeon running : DeityDungeons.getRunningDungeons()) {
			if(running.getDungeon().getName().equals(args[0])) {
				//This dungeon needs to be stopped
				DeityDungeons.getRunningDungeons().remove(running);
				DeityDungeons.getRunningDungeonNames().remove(running.getDungeon().getName());
				
				for(Entity entity : running.getSpawnedEntities()) {
					entity.remove();
				}
				
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeon", "<green>The dungeon " + running.getDungeon().getName() + " has been stopped");
				return true;
			}
		}
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The dungeon " + args[0] + " is not currently running");
		return true;
	}

}
