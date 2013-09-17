package com.imdeity.deitydungeons.cmd.mob;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Mob;

public class MobSetDelayCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] arg0) {
		// TODO Auto-generated method stub
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
		
		if(!DeityDungeons.isInt(args[0])) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "That is not a valid delay value.");
			return false;
		}
		
		int delay = Integer.parseInt(args[0]);
		
		Mob mob = DungeonManager.getPlayersMob(player);
		
		DungeonManager.setMobDelay(DungeonManager.getPlayersMob(player), Integer.parseInt(args[0]));
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The delay for mob " + mob.getName() + " has been set to " + delay);
		
		return true;
	}

}
