package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonRunningCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	//lists the current running dungeons and the players in them
	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<red>Running dungeons: ");
		
		for(RunningDungeon running : DeityDungeons.getRunningDungeons()) {
			String playerList = "";
			
			for(Player p : running.getPlayers()) {
				playerList += p.getName() + ", ";
			}
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green> * " + running.getDungeon().getName() + " * <aqua>" + playerList.substring(0, playerList.length() - 1));
		}
		
		return true;
	}

}
