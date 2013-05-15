package com.imdeity.deitydungeons.cmd.mob;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;

public class MobSetFeetCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) {
			return false;
		}
		
		if(!DungeonManager.playerHasMob(player)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You must first select a mob.");
			return false;
		}
		
		Material material = DungeonManager.getMaterialByName(args[0], "BOOTS");
		
		if(material == null) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Invalid material.");
		}
		
		DungeonManager.setMobAttribute(DungeonManager.getPlayersMob(player), "feet", material.getId());
		
		return true;
	}

}
