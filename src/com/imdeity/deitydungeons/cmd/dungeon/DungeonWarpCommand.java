package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;

public class DungeonWarpCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>Usage: <green>/dungeon warp <dungoen name>");
			return true;
		}
		
		if(!DungeonManager.dungeonExists(args[0])) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>The dungeon <green>" + args[0] + " <red>does not exist");
			return true;
		}
		
		Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
		
		player.teleport(dungeon.getSpawn());
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>You have been teleported to the spawn of <aqua>" + dungeon.getName());
		
		return true;
	}

}
