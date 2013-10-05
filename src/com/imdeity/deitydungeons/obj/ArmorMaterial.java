package com.imdeity.deitydungeons.obj;

public enum ArmorMaterial {
	AIR("AIR", "A"), LEATHER("LEATHER", "L"), CHAINMAIL("CHAINMAIL", "C"), IRON("IRON", "I"), GOLD("GOLD", "G"), DIAMOND("DIAMOND", "D");

	private String name;
	private String c;
	
	private ArmorMaterial(String name, String c) {
		this.name = name;
		this.c = c;
	}

	public String getName() {
		return name;
	}
	
	public String getChar() {
		return c;
	}

	public static ArmorMaterial getByName(String name) {
		for(ArmorMaterial am : ArmorMaterial.values()) {
			if(am.getName().equals(name)) {
				return am;
			}
		}

		return null;
	}
	
	public static ArmorMaterial getByChar(char c) {
		switch(c) {
		case 'L': return LEATHER;
		case 'C': return CHAINMAIL;
		case 'I': return IRON;
		case 'G': return GOLD;
		case 'D': return DIAMOND;
		default: return AIR;
		}
	}
	
	public static String getChar(ArmorMaterial am) {
		switch(am) {
		case LEATHER:
			return "L";
		case CHAINMAIL:
			return "C";
		case IRON:
			return "I";
		case GOLD:
			return "G";
		case DIAMOND:
			return "D";
		default:
			return "A";
		}
	}
	
	public static String getMaterial(ArmorMaterial am) {
		switch(am) {
		case LEATHER:
			return "LEATHER";
		case CHAINMAIL:
			return "CHAINMAIL";
		case IRON:
			return "IRON";
		case GOLD:
			return "GOLD";
		case DIAMOND:
			return "DIAMOND";
		default:
			return "AIR";
		}
	}
}
