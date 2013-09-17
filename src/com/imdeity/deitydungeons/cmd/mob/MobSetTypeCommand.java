package com.imdeity.deitydungeons.cmd.mob;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;

public class MobSetTypeCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		//Check args
		if(args.length != 1) {
			return false;
		}
		
		//Make sure player has a selected mob
		if(!DungeonManager.playerHasMob(player)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You must first select a mob");
			return true;
		}
		
		//Check the type
		String typeName = args[0];
		EntityType type = EntityType.fromName(typeName);
		
		if(type == null) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Invalid entity!");
			return true;
		}
		
		//Finally
		DungeonManager.setMobType(DungeonManager.selectedMobs.get(player), type);
		
		

		return true;
	}

}
