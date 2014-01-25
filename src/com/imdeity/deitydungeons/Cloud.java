package com.imdeity.deitydungeons;

import org.bukkit.entity.Player;

public class Cloud {

	public static void onDungeonStart(Player[] players, int dungeonID) {
		//Called after players have been teleported and dungeon has officially "started"
	}

	public static void onPlayerJoin(String player) {

	}

	public static void onPlayerDeath(String player) {

	}

	public static void onPlayerRespawn(String player) {

	}

	public static void onPlayerDisconnect(String player) {

	}
	
	public static void playerFinishedDungeon(Player player, int dungeonID) {
		//if the dungeon is fully complete, run this line of code
		//this adds a record in the database and removes all spawned mobs that were not killed by players
		//as well as freeing the dungeon up for someone else to play
		DungeonManager.endDungeon(DungeonManager.getPlayersRunningDungeon(player));
	}

}
