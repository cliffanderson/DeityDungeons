package com.imdeity.deitydungeons.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.imdeity.deityapi.api.DeityListener;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonListener extends DeityListener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		//Don't want players to respawn in dungeons
		for(RunningDungeon rd : DeityDungeons.getRunningDungeons()) {
			if(rd.containsPlayer(player)) {
				event.getPlayer().teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
				rd.removePlayer(player);
				
				if(!rd.hasPlayers()) {
					rd.removeAllMobs();
				}
			}
		}
		//TODO: ok?
	}	
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			for(RunningDungeon rd : DeityDungeons.getRunningDungeons()) {
				if(rd.containsPlayer(player)) {
					rd.notifyDeath(player);
					break;
				}
			}
		}else{
			Entity e = (Entity) entity;
			for(RunningDungeon rd : DeityDungeons.getRunningDungeons()) {
				if(rd.containsMob(e)) {
					rd.notifyDeathOfMob(e);
					break;
				}
			}
		}
	}	
}