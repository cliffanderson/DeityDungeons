package com.imdeity.deitydungeons.obj;

import java.util.ArrayList;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deitydungeons.DungeonManager;

public class Dungeon {
	
	ArrayList<Mob> mobs = new ArrayList<Mob>();
	String name;
	
	public Dungeon(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean setName(String s) {
		return DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE ? SET `name`=?", DungeonManager.DUNGEON_LIST, s);
	}
	
	public void addMob(Mob mob) {
		mobs.add(mob);
	}
	
	public void removeMob(Mob mob) {
		mobs.remove(mob);
	}
	
	public boolean hasMob(String name) {
		for(Mob mob : mobs) {
			if(mob.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public Mob getMobByName(String name) {
		for(Mob mob : mobs) {
			if(mob.getName().equals(name)) {
				return mob;
			}
		}
		return null;
	}
}
