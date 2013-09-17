package com.imdeity.deitydungeons.obj;

public enum ArmorMaterial {
	AIR("AIR"), LEATHER("LEATHER"), CHAINMAIL("CHAINMAIL"), IRON("IRON"), GOLD("GOLD"), DIAMOND("DIAMOND");

	private String name;

	private ArmorMaterial(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static ArmorMaterial getByName(String name) {
		for(ArmorMaterial am : ArmorMaterial.values()) {
			if(am.getName().equals(name)) {
				return am;
			}
		}

		return null;
	}

}
