package com.imdeity.deitydungeons.obj;

public enum ArmorPiece {
	HELMET("HELMET"), CHESTPLATE("CHESTPLATE"), LEGGINGS("LEGGINGS"), BOOTS("BOOTS");
	
	private String name;
	
	private ArmorPiece(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static ArmorPiece getByName(String name) {
		for(ArmorPiece ap : ArmorPiece.values()) {
			if(ap.getName().equalsIgnoreCase(name)) {
				return ap;
			}
		}
		
		return null;
	}
}
