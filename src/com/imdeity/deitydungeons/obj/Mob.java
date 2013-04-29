package com.imdeity.deitydungeons.obj;

import org.bukkit.entity.EntityType;

public class Mob {
	String name;
	EntityType type;
	int health, helm, chest, pants, feet;
	
	public Mob(String name, EntityType type, int health, int helm, int chest, int pants, int feet) {
		this.name = name;
		this.type = type;
		this.health = health;
		this.helm = helm;
		this.chest = chest;
		this.pants = pants;
		this.feet = feet;
	}
	
	public String getName() {
		return name;
	}

}
