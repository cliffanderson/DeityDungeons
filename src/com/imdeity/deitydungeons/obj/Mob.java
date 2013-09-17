package com.imdeity.deitydungeons.obj;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public class Mob {
	String name;
	EntityType type;
	int health, helm, chest, pants, feet, x, y, z, delay;
	Dungeon dungeon;
	Location location;
	World world;
	
	public Mob(String name, EntityType type, int health, int helm, int chest, int pants, int feet, Dungeon dungeon, int x, int y, int z, int delay) {
		this.name = name;
		this.type = type;
		this.health = health;
		this.helm = helm;
		this.chest = chest;
		this.pants = pants;
		this.feet = feet;
		this.dungeon = dungeon;
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = dungeon.getWorld();
		
		this.delay = delay;
		
		location = new Location(world, x, y, z);
	}
	
	public Location getLocation() {
		return location;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		location = new Location(world, x, y, z);
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		location = new Location(world, x, y, z);
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		location = new Location(world, x, y, z);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public void setType(EntityType type) {
		this.type = type;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHelm() {
		return helm;
	}
	
	public void setHelm(int helm) {
		this.helm = helm;
	}
	
	public int getChest() {
		return chest;
	}
	
	public void setChest(int chest) {
		this.chest = chest;
	}
	
	public int getPants() {
		return pants;
	}
	
	public void setPants(int pants) {
		this.pants = pants;
	}
	
	public int getFeet() {
		return feet;
	}
	
	public void setFeet(int feet) {
		this.feet = feet;
	}
	
	public Dungeon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
}
