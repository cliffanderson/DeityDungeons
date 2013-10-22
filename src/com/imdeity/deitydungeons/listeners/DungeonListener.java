package com.imdeity.deitydungeons.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.imdeity.deityapi.api.DeityListener;
import com.imdeity.deitydungeons.Cloud;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonListener extends DeityListener {

	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		if(DungeonManager.playerIsRunningDungeon(event.getPlayer())) {
			Cloud.onPlayerDisconnect(event.getPlayer().getName());
		}
	}	

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		
		//Player
		if(entity instanceof Player) {
			Player player = (Player) entity;
			for(RunningDungeon rd : DeityDungeons.getRunningDungeons()) {
				if(rd.containsPlayer(player)) {
					Cloud.onPlayerDeath(player.getName());
					break;
				}
			}
		//Mob
		}else{
			Entity e = (Entity) entity;
			for(RunningDungeon rd : DeityDungeons.getRunningDungeons()) {
				if(rd.notifyDeathOfMob(e)) break;
			}
		}
	}	
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		for(RunningDungeon runningDungeon : DeityDungeons.getRunningDungeons()) {
			if(runningDungeon.containsPlayer(event.getPlayer())) {
				runningDungeon.handleMove(event.getPlayer(), event.getTo());
				
				//Prevents concurrent modifcation exceptions, as a move event can lead to the 
				//end of a dungeon, this removing the dungeon from the list
				break; 
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		for(RunningDungeon running : DeityDungeons.getRunningDungeons()) {
			if(running.containsPlayer(event.getPlayer())) {
				event.getPlayer().teleport(running.getDungeon().getSpawn());
			}
		}
		
		Cloud.onPlayerJoin(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		for(RunningDungeon running : DeityDungeons.getRunningDungeons()) {
			if(running.containsPlayer(event.getPlayer())) {
				event.getPlayer().teleport(running.getDungeon().getSpawn());
			}
		}
		
		Cloud.onPlayerRespawn(event.getPlayer().getName());
	}
}