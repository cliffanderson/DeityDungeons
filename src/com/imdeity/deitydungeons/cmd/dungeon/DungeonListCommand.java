package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;

public class DungeonListCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		if(args.length != 0) {
			DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "No arguments are required for the dungeon list command");
		}
		
		DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "Dungeons:");
		
		for(Dungeon dungeon : DungeonManager.getDungeons()) {
			String plural = dungeon.getMobs().size() == 1 ? "" : "s";
			DeityAPI.getAPI().getChatAPI().out("DeityDungeons", dungeon.getName() + " [" + dungeon.getMobs().size() + " mob" + plural +"]");
		}
		
		return true;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length == 1 && DungeonManager.dungeonExists(args[0])) {
			if(DungeonManager.getDungeonByName(args[0]).getMobs().size() == 0) {
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>The dungeon <yellow>" + args[0] + " <red>contains no mobs");
				return true;
			}
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>Mobs for dungeon <aqua>" + DungeonManager.getDungeonByName(args[0]).getName() + "<red>:");
		
			for(Mob mob : DungeonManager.getDungeonByName(args[0]).getMobs()) {
				String plural = mob.getAmount() == 1 ? "" : "s";
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", mob.getID() + " - " + mob.getType().toString() + " - <aqua>" + mob.getAmount() + " mob" + plural);
			}
			
			return true;
		
		}else if(args.length == 0) {
			if(DungeonManager.getDungeons().size() == 0) {
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>There are no dungeons that currently exist");
				return true;
			}
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>Dungeons:");
			
			for(Dungeon dungeon : DungeonManager.getDungeons()) {
				String plural = dungeon.getMobs().size() == 1 ? "" : "s";
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", dungeon.getName() + " [" + dungeon.getMobs().size() + " mob" + plural + "]");
			}
			
			return true;
		}else{
			return false;
		}
		
		
	}

}