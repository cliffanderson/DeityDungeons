package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;

public class DungeonSetFinishCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		//Basic checking
		if(args.length == 0 && DungeonManager.playerHasDungeon(player)) {
			Dungeon dungeon = DungeonManager.getPlayersDungeon(player);
			Location l = player.getLocation();
			DungeonManager.setDungeonFinish(dungeon, l.getBlockX(), l.getBlockY(), l.getBlockZ());
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The finish point for dungeon " + dungeon.getName() + " has been set to your location");
			
			return true;
		} else {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You must first select a dungeon or use the command like this: <red>/dungeon setfinish <dungeonname>");
		}
		
		if(args.length == 1 && DungeonManager.dungeonExists(args[0])) {
			Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
			Location l = player.getLocation();
			DungeonManager.setDungeonFinish(dungeon, l.getBlockX(), l.getBlockY(), l.getBlockZ());
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The finish point for dungeon " + dungeon.getName() + " has been set to your location");
			
			return true;
		} else {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Correct usage: <red>/dungeon setfinish <dungeonname>");
		}	
		
		return true;
	}
}