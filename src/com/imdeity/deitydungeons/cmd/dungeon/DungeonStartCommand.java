package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonStartCommand extends DeityCommandReceiver {

	//Usage: /dungeon start dungeonName cliff777 Notch The_Yogs ImDeity
	@Override
	public boolean onConsoleRunCommand(String[] args) {
		if(args.length < 2) {			
			DeityAPI.getAPI().getChatAPI().outWarn("DeityDungeons", "Usage: /dungeon start dungeonName player1 player2...");
			return true;
		}
		
		if(!DungeonManager.dungeonExists(args[0])) {
			DeityAPI.getAPI().getChatAPI().outWarn("DeityDungeons", "The dungeon " + args[0] + " does not exist");
			return true;
		}
		
		if(DeityDungeons.getRunningDungeonNames().contains(args[0])) {
			DeityAPI.getAPI().getChatAPI().outWarn("DeityDungeons", "The dungeon " + args[0] + " is currently being run. Please try again later.");
			return true;
		}
		
		
		Player[] players = new Player[args.length - 1];
		
		for(int i = 1; i < args.length; i++) {
			if(Bukkit.getPlayer(args[i]) == null || !Bukkit.getPlayer(args[i]).isOnline()) {
				DeityAPI.getAPI().getChatAPI().outWarn("DeityDungeons", "Error: The player " + args[i] + " does not exist or is not online!");
				DeityAPI.getAPI().getChatAPI().outWarn("DeityDungeons", "Usage: /dungeon start dungeonName player1 player2...");
				
				return true;
			}else{
				players[i-1] = Bukkit.getPlayer(args[i]);
			}			
		}
		
		Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
		DeityDungeons.getRunningDungeons().add(new RunningDungeon(dungeon, players));
		DeityDungeons.getRunningDungeonNames().add(dungeon.getName());
		
		DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "The dungeon " + args[0] + " has been started");
		return true;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {		
		if(args.length > 1) {
			return false;
		}
		
		String dungeonName;
		
		if(args.length == 0 && DungeonManager.playerHasDungeon(player)) {
			dungeonName = DungeonManager.getPlayersDungeon(player).getName();
		}else{
			dungeonName = args[0];
		}
		
		if(DeityDungeons.getRunningDungeonNames().contains(dungeonName)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The dungeon " + dungeonName + " is currently being run. Please try again later.");
			return true;
		}
		
		if(!DungeonManager.dungeonExists(dungeonName)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "The dungeon " + dungeonName + " is invalid");
			return true;
		}
		
		Dungeon dungeon = DungeonManager.getDungeonByName(dungeonName);

		DeityDungeons.getRunningDungeons().add(new RunningDungeon(dungeon, player));
		DeityDungeons.getRunningDungeonNames().add(dungeon.getName());
		
		return true;
	}
}
