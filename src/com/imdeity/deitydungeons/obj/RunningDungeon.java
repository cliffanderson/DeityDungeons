package com.imdeity.deitydungeons.obj;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deitydungeons.DeityDungeons;

//Represents a running dungeon
public class RunningDungeon extends Thread {
	
	Dungeon dungeon;
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Player> originalPlayers = new ArrayList<Player>();
	ArrayList<Entity> mobs = new ArrayList<Entity>();
	
	Player player;
	
	public RunningDungeon(Dungeon dungeon, Player player) {
		this.dungeon = dungeon;
		this.player = player;
		players.add(player);
		originalPlayers.add(player);
		
		this.start();
	}
	
	@Override
	public void run() {
		//spawn players
		player.teleport(player);
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "[DeityDungeons]", "<green>The dungeon <yellow>" + dungeon.name + " <green>will be starting in 5 seconds with <yellow>" + dungeon.mobs.size() + " <green>mobs");
	
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//spawn mobs
		for(Mob mob : dungeon.mobs) {
			Entity e = dungeon.getWorld().spawnEntity(mob.getLocation(), mob.getType());
			mobs.add(e);
		}
	}
	
	public boolean containsPlayer(Player player) {
		//This will be an alive player
		return players.contains(player);
	}
	
	public boolean containsMob(Entity e) {
		return mobs.contains(e);
	}
	
	public void notifyDeath(Player player) {
		if(containsPlayer(player)) {
			players.remove(player);
			
			if(players.size() == 0 && mobs.size() != 0) {
				//Dungeon over all players have died
				for(Player p : originalPlayers) {
					if(p.isOnline())
						DeityAPI.getAPI().getChatAPI().sendPlayerMessage(p, "[DeityDungeons]", "All players have died while fighting " + dungeon.getName() + ". Better luck next time!");
				}
				
				//Abandon
				DeityDungeons.runningDungeons.remove(this);
			}
		}
	}
	
	public void notifyDeathOfMob(Entity e) {
		if(mobs.contains(e)) {
			mobs.remove(e);
			
			if(mobs.size() == 0) {
				//Players have won
				for(Player p : originalPlayers) {
					if(p.isOnline())
						DeityAPI.getAPI().getChatAPI().sendPlayerMessage(p, "[DeityDungeons]", "Congratulations! You have won!");
				}
				
				//Abandon
				DeityDungeons.runningDungeons.remove(this);
			}
		}
	}

}
