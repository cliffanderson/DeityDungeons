package com.imdeity.deitydungeons.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.imdeity.deityapi.DeityAPI;

//Class to store all dungeon data in memory
public class Dungeon {
	
	public ArrayList<Mob> mobs = new ArrayList<Mob>();
	public Map<Location, ArrayList<ItemStack>> items;
	
	int dungeonID;
	String name;
	World world;
	int x;
	int y;
	int z;
	int yaw;
	int pitch;
	Location location;
	Location finish;
	
	public Dungeon(int dungeonID, String name, World world, int x, int y, int z, int yaw, int pitch, int fx, int fy, int fz, HashMap<Location, ArrayList<ItemStack>> items) {
		this.dungeonID = dungeonID;
		this.name = name;
		this.world = world;
		this.location = new Location(world, x, y, z, yaw, pitch);
		this.finish = new Location(world, fx, fy, fz);
		this.items = items;
	}

	public Map<Location,ArrayList<ItemStack>> getItems() {
		return this.items;
	}
	
	public Location getSpawn() {
		return location;
	}
	
	public ArrayList<Mob> getMobs() {
		return mobs;
	}
	
	public World getWorld() {
		return world;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean setName(String newName) {
		return DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_list` SET `name`=? WHERE `name`=?", newName, name);
	}
	
	public void addMob(Mob mob) {
		mobs.add(mob);
	}
	
	public void removeMob(Mob mob) {
		mobs.remove(mob);
	}
	
	public boolean hasMob(int id) {
		for(Mob mob : mobs) {
			if(mob.getID() == id) {
				return true;
			}
		}
		return false;
	}
	
	public Mob getMobByID(int id) {
		for(Mob mob : mobs) {
			if(mob.getID() == id) {
				return mob;
			}
		}
		return null;
	}
	
	public int getID() {
		return dungeonID;
	}
	
	public void setDungeonFinish(int x, int y, int z) {
		this.finish = new Location(this.world, x, y, z);
	}
	
	public int getFinishX() {
		return finish.getBlockX();
	}
	
	public int getFinishY() {
		return finish.getBlockY();
	}
	
	public int getFinishZ() {
		return finish.getBlockZ();
	}
	
	public Location getFinish() {
		return finish;
	}
}
