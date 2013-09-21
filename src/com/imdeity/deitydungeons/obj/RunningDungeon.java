package com.imdeity.deitydungeons.obj;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deitydungeons.DeityDungeons;

//Represents a running dungeon
public class RunningDungeon extends Thread {
	
	Dungeon dungeon;
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Player> originalPlayers = new ArrayList<Player>();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	Player player;
	
	/*
	public RunningDungeon(Dungeon dungeon, Player player) {
		this.dungeon = dungeon;
		this.player = player;
		players.add(player);
		originalPlayers.add(player);
		
		this.start();
	}
	*/
	
	public RunningDungeon(Dungeon dungeon, Player...playerArray) {
		this.dungeon = dungeon;
		
		for(Player player : playerArray) {
			this.players.add(player);
			this.originalPlayers.add(player);
		}
		
		this.start();
	}
	
	@Override
	public void run() {
		//spawn players
		for(Player player : players) {
			System.out.println("is player null: " + player);
			player.teleport(dungeon.getSpawn());
		}
			
		for(Player player : players) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>The dungeon <yellow>" + dungeon.name + " <green>will be starting in 5 seconds");
		}
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//spawn mobs
		for(Mob mob : dungeon.mobs) {
			Entity entity = dungeon.getWorld().spawnEntity(mob.getLocation(), mob.getType());
			LivingEntity living = (LivingEntity) entity;
			EntityEquipment ee = living.getEquipment();
			
			ee.setHelmet(new ItemStack(mob.getHelm(), 1));
			ee.setChestplate(new ItemStack(mob.getChest(), 1));
			ee.setLeggings(new ItemStack(mob.getPants(), 1));
			ee.setBoots(new ItemStack(mob.getFeet(), 1));

			ee.setItemInHand(new ItemStack(Material.SIGN, 1));
			
			entities.add(entity);
		}
		
		for(Player player : players) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Dungeon " + dungeon.getName() + " has started. Good luck!");
		}
	}
	
	public boolean containsPlayer(Player player) {
		//This will be an alive player
		return players.contains(player);
	}
	
	public boolean containsMob(Entity e) {
		return entities.contains(e);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public void removeAllMobs() {
		for(Entity entity : entities) {
			entity.remove();
		}
	}
	
	public boolean hasPlayers() {
		return players.size() != 0;
	}
	
	public void notifyDeath(Player player) {
		if(containsPlayer(player)) {
			players.remove(player);
			
			if(players.size() == 0 && entities.size() != 0) {
				//Dungeon over all players have died
				for(Player p : originalPlayers) {
					if(p.isOnline())
						DeityAPI.getAPI().getChatAPI().sendPlayerMessage(p, "DeityDungeons", "All players have died while fighting " + dungeon.getName() + ". Better luck next time!");
				}
								
				removeAllMobs();
				
				//Abandon
				DeityDungeons.getRunningDungeons().remove(this);
				DeityDungeons.getRunningDungeonNames().remove(this.dungeon.getName());
			}
		}
	}
	
	public void notifyDeathOfMob(Entity e) {
		if(entities.contains(e)) {
			entities.remove(e);
			
			if(entities.size() == 0) {
				//Players have won
				for(Player p : originalPlayers) {
					if(p.isOnline())
						DeityAPI.getAPI().getChatAPI().sendPlayerMessage(p, "DeityDungeons", "Congratulations! You have won!");
				}
				
				//Abandon
				DeityDungeons.getRunningDungeons().remove(this);
				DeityDungeons.getRunningDungeonNames().remove(this.dungeon.getName());
			}
		}
	}
}