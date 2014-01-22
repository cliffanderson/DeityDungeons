package com.imdeity.deitydungeons.obj;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public class Mob {
	EntityType type;
	int id, health, x, y, z, amount;
	ArmorMaterial helm, chest, pants, feet;
	Dungeon dungeon;
	Location location;
	World world;
	
	//If the mob should target the nearest player automatically, or leave it to minecraft
	//False be default
	boolean target;
	
	Material weapon;
	
	public Mob(int id, EntityType type, int health, ArmorMaterial helm, ArmorMaterial chest, ArmorMaterial pants, ArmorMaterial feet, Dungeon dungeon, int x, int y, int z, boolean target, int amount, Material weapon) {
		this.id = id;
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
				
		location = new Location(world, x, y, z);
		
		this.target = target;
		
		this.amount = amount;
		
		this.weapon = weapon;
	}
	
	public Material getWeapon() {
		return this.weapon;
	}
	
	public void setWeapon(Material weapon) {
		this.weapon = weapon;
	}
		
	public int getAmount() {
		return amount;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
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
	
	public ArmorMaterial getHelm() {
		return helm;
	}
	
	public void setHelm(ArmorMaterial helm) {
		this.helm = helm;
	}
	
	public ArmorMaterial getChest() {
		return chest;
	}
	
	public void setChest(ArmorMaterial chest) {
		this.chest = chest;
	}
	
	public ArmorMaterial getPants() {
		return pants;
	}
	
	public void setPants(ArmorMaterial pants) {
		this.pants = pants;
	}
	
	public ArmorMaterial getFeet() {
		return feet;
	}
	
	public void setFeet(ArmorMaterial feet) {
		this.feet = feet;
	}
	
	public Dungeon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	
	public boolean shouldTarget() {
		return target;
	}
	
	public void setShouldTarget(boolean target) {
		this.target = target;
	}
}