package com.imdeity.deitydungeons.cmd.mob;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;

public class MobSelectCommand extends DeityCommandReceiver {
	
	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		System.out.println("entered " + args[0]);
		if(args.length != 1) return false;
		String mobName = args[0];
		
		if(DungeonManager.selectedDungeons.get(player) == null) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You must have a dungeon selected to select a mob");
			return true;
		}
		
		Dungeon dungeon = DungeonManager.selectedDungeons.get(player);
		
		if(!dungeon.hasMob(mobName)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "That mob does not exist!");
			return true;
		}
		Mob mob = dungeon.getMobByName(mobName);
		
		
		
		DungeonManager.selectedMobs.put(player, mob);
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You have selected the mob " + mob.getName());
		
		return true;
	}

}