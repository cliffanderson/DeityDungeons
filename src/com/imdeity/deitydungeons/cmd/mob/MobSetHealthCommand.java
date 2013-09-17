package com.imdeity.deitydungeons.cmd.mob;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Mob;

public class MobSetHealthCommand extends DeityCommandReceiver {

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
			return true;
		}
		
		if(!DeityDungeons.isInt(args[0]) || Integer.parseInt(args[0]) == 0) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You must input a valid number for the health!");
			return true;
		}
		
		int health = Integer.parseInt(args[0]);
		
		Mob mob = DungeonManager.getPlayersMob(player);
		
		DungeonManager.setMobHealth(mob, health);
				
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The health for mob " + mob.getName() + " has been set to " + health + "(" + (float) health / 2 + ") hearts");
		return true;
	}

}
