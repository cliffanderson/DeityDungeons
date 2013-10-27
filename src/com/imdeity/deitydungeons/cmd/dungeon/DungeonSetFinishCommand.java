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
		if(args.length == 1 && DungeonManager.dungeonExists(args[0])) {
			Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
			Location l = player.getLocation();
			DungeonManager.setDungeonFinish(dungeon, l.getBlockX(), l.getBlockY(), l.getBlockZ());
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The finish point for dungeon <red>" + dungeon.getName() + " <white>has been set to your location");
			
			return true;
		} else if(args.length == 1 && !DungeonManager.dungeonExists(args[0])){
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The dungeon <red>" + args[0] + " <white>does not exist.");
		}else if(args.length == 0 && DungeonManager.playerHasDungeon(player)) {
			Dungeon dungeon = DungeonManager.getPlayersDungeon(player);
			Location l = player.getLocation();
			DungeonManager.setDungeonFinish(dungeon, l.getBlockX(), l.getBlockY(), l.getBlockZ());
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The finish point for dungeon <red>" + dungeon.getName() + " <white>has been set to your location");
		
		}else{
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Correct usage: <red>/dungeon setfinish <dungeonname>");
			return true;
		}
		
		return true;
	}
}