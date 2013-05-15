package com.imdeity.deitydungeons.obj;

import org.bukkit.entity.EntityType;

public class Mob {
	String name;
	EntityType type;
	int health, helm, chest, pants, feet;
	Dungeon dungeon;
	
	public Mob(String name, EntityType type, int health, int helm, int chest, int pants, int feet, Dungeon dungeon) {
		this.name = name;
		this.type = type;
		this.health = health;
		this.helm = helm;
		this.chest = chest;
		this.pants = pants;
		this.feet = feet;
		this.dungeon = dungeon;
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
	
	
}
