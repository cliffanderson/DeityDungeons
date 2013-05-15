package com.imdeity.deitydungeons;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.records.DatabaseResults;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;

public class DungeonManager {
	//Main storage of all dungeons
	public static Map<String, Dungeon> dungeons;

	//Map of selected dungeons for each player
	public static Map<Player, Dungeon> selectedDungeons;

	//Map of selected mobs for each player
	public static Map<Player, Mob> selectedMobs;

	//Don't ever want to mess up the table name
	public static final String DUNGEON_LIST = "`dungeon_list`";

	//Loads all dungeons into memory from database
	public static void loadAllDungeons() {
		//Define maps so same method can be used for reloading dungeons
		//Redefining the maps basically clears them
		dungeons = new HashMap<String, Dungeon>();
		selectedDungeons = new HashMap<Player, Dungeon>();
		selectedMobs = new HashMap<Player, Mob>();

		//Load all dungeon names into an arraylist
		ArrayList<String> dungeonNames = new ArrayList<String>();
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM ?", DUNGEON_LIST);
		if(results != null && results.hasRows()) {

			for(int i = 0; i < results.rowCount(); i++) {
				try {
					String name = results.getString(i, "name");
					dungeonNames.add(name);

					DatabaseResults mobResults = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM ?", DeityDungeons.tableName(name));
					Dungeon dungeon = new Dungeon(name);
					for(int j = 0; j < mobResults.rowCount(); j++) {
						try {
							String mobName = results.getString(j, "name");
							EntityType type = EntityType.fromName(results.getString(j, "type"));
							int health = results.getInteger(j, "health");
							int helm = results.getInteger(j, "helm");
							int chest = results.getInteger(j, "chest");
							int pants = results.getInteger(j, "pants");
							int feet = results.getInteger(j, "feet");
							Mob mob = new Mob(mobName, type, health, helm, chest, pants, feet, dungeon);
							dungeon.addMob(mob);
						}catch(SQLDataException e) {
							DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "Unable to load mob from dungeon " + name);
							e.printStackTrace();
						}
					}
					//Finally put the dungeon object into a map with the key being the name
					dungeons.put(name, dungeon);

				} catch (SQLDataException e) {
					DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "Unable to load dungeons!");
					e.printStackTrace();
				}
			}
		}
	}

	//Checks if the dungeon with the given name exists in memory
	public static boolean dungeonExists(String name) {
		if(getDungeonByName(name) == null) {
			return false;
		}else{
			return true;
		}
	}

	//Returns the dungeon with the given name
	//If it doesn't exist, it returns null
	public static Dungeon getDungeonByName(String dungeonName) {
		if(dungeons.containsKey(dungeonName)) {
			return dungeons.get(dungeonName);
		}else{
			return null;
		}
	}

	//Returns the player's selected mob
	//If it doesn't exist, it returns null
	public static Mob getPlayersMob(Player player) {
		if(selectedMobs.containsKey(player)) {
			return selectedMobs.get(player);
		}else{
			return null;
		}
	}

	//Returns the player's selected dungeon
	//If it doesn't exist, it returns null
	public static Dungeon getPlayersDungeon(Player player) {
		if(selectedDungeons.containsKey(player)) {
			return selectedDungeons.get(player);
		}else{
			return null;
		}
	}

	//Creates a dungeon
	public static void createDungeon(Player p, String name, int playerAmount) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO ? (`name`, `number_of_players`, `x`, `y`, `z`, `yaw`, `pitch`, `mob_x`, `mob_y`, `mob_z`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				DUNGEON_LIST, name, playerAmount, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), (int)p.getLocation().getYaw(), (int)p.getLocation().getPitch(), -1, -1, -1);

		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS ? (`id` INT(16) NOT NULL AUTO INCREMENT, `name` VARCHAR(32) NOT NULL, `type` VARCHAR(32) NOT NULL, `health` INT(16) NOT NULL, " +
				"`helm` INT(8) NOT NULL, `chest` INT(8) NOT NULL, `pants` INT(8) NOT NULL, `feet` INT(8) NOT NULL)", DeityDungeons.tableName(name));
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(p, "DeityDungeons", "<aqua>Dungeon <red>" + name + "<aqua> has been created! Please use the <red>/dungeon setmobspawn <aqua> command to change the mob spawn location.");

	}

	//Adds a mob to a dungeon
	//Both in the mysql database and in the
	//dungeon object in memory
	public static void addMobToDungeon(Mob mob) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO ? (`name`, `type`, `health`, `helm`, `chest`, `pants`, `feet`) VALUES (?, ?, ?, ?, ?, ?, ?)", DeityDungeons.tableName(mob.getDungeon().getName()) + mob.getName(), mob.getType(), mob.getHealth(), mob.getHelm(), mob.getChest(), mob.getPants(), mob.getFeet());

		//for dungeon in memory
		mob.getDungeon().addMob(mob);
	}

	//Removes a mob from a dungeon
	//Both in the mysql database and from the 
	//dungeon object in memory
	public static void removeMobFromDungeon(Mob mob) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("DELETE FROM ? WHERE `name`=?", DeityDungeons.tableName(mob.getDungeon().getName()), mob.getName());

		//for dungeon in memory
		mob.getDungeon().removeMob(mob);
	}	

	//Used to set attributes for mobs
	//Used for everything from health to chestplates
	public static void setMobAttribute(Mob mob, String attribute, Object value) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE ? SET ?=? WHERE `name`=?", DeityDungeons.tableName(mob.getDungeon().getName()), attribute, value, mob.getName());

	}

	//Used to check if a player has a selected dungeon
	public static boolean playerHasDungeon(Player player) {
		return getPlayersDungeon(player) != null;
	}

	//Used to check if a player has a selected mob
	public static boolean playerHasMob(Player player) {
		return getPlayersMob(player) != null;
	}

	//Easy way to check is user input is valid
	//All armors are METALTYPE_ARMORTYPE
	//For example: DIAMOND_CHESTPLATE
	public static Material getMaterialByName(String material, String armor) {
		Material mat = Material.getMaterial(material + "_" + armor);
		if(mat == null) {
			return null;
		}else{
			return mat;
		}
	}


}