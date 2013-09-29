package com.imdeity.deitydungeons.cmd.mob;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;

public class MobSetTargetCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Invalid arguments. Usage: /mob settarget <true|false|T|F|0|1>");
			return true;
		}
		
		if(!DungeonManager.playerHasMob(player)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You must first select a mob.");
			return true;
		}
			
		String flag = args[1];
		
		if(flag.equalsIgnoreCase("true") || flag.equalsIgnoreCase("t") || flag.equalsIgnoreCase("1")) {
			DungeonManager.setMobTarget(DungeonManager.getPlayersMob(player), true);
		}else if(flag.equalsIgnoreCase("false") || flag.equalsIgnoreCase("f") || flag.equalsIgnoreCase("0")) {
			DungeonManager.setMobTarget(DungeonManager.getPlayersMob(player), false);
		}else{
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Invalid arguments. Usage: /mob settarget <mob> <true|false|T|F|0|1>");
			return true;
		}
		
		return true;
	}

}
