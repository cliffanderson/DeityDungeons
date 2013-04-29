package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;

public class DungeonAddMobCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 2) return false;
		
		//Make sure the player has a selected dungeon
		if(!DungeonManager.dungeons.containsKey(player)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You first must select a dungeon");
			return false;
		}
		
		Dungeon dungeon = DungeonManager.dungeons.get(player);		
		String mob = args[0];
		String type = args[1];
		
		if(EntityType.fromName(type) == null) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Invalid entity type");
			return false;
		}
		
		if(dungeon.hasMob(mob)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The dungeon " + dungeon.getName() + " already has a mob named " + mob);
			return false;
		}
		
		//Finally everything has been checked and we can add the mob
		//Mob mob = new Mob();
		
		
		return false;
	}

}
