package com.imdeity.deitydungeons;

import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.records.DatabaseResults;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonManager {
	//Main storage of all dungeons
	public static Map<String, Dungeon> dungeons;

	//Map of selected dungeons for each player
	public static Map<Player, Dungeon> selectedDungeons;

	//Map of selected mobs for each player
	public static Map<Player, Mob> selectedMobs;

	//Loads all dungeons into memory from database
	public static void loadAllDungeons() {
		//Define maps so same method can be used for reloading dungeons
		//Redefining the maps basically clears them
		dungeons = new HashMap<String, Dungeon>();

		//Redefine these to prevent players having selected
		//Objects that are no longer valid
		selectedDungeons = new HashMap<Player, Dungeon>();
		selectedMobs = new HashMap<Player, Mob>();

		//Load all dungeon names into an arraylist
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_list`");
		System.out.println(results.rowCount());
		if(results != null && results.hasRows()) {

			for(int i = 0; i < results.rowCount(); i++) {
				try {
					String dungeonName = results.getString(i, "name");
					int numberOfPlayers = results.getInteger(i, "number_of_players");

					//If the world is null, it cannot be found. Make sure to warn the console
					String worldName = results.getString(i, "world");
					World world;
					try {
						world = DeityDungeons.plugin.getServer().getWorld(worldName);
					}catch(NullPointerException e) {
						DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "The world " + worldName + " cannot be found!");
						continue;
						//return;
					}


					int x = results.getInteger(i, "x");
					int y = results.getInteger(i, "y");
					int z = results.getInteger(i, "z");
					int yaw = results.getInteger(i, "yaw");
					int pitch = results.getInteger(i, "pitch");

					Dungeon dungeon = new Dungeon(dungeonName, numberOfPlayers, world, x, y, z, yaw, pitch);

					//Load the table for this dungeon
					//Results will return null if there are no rows
					DatabaseResults mobResults = null;
					mobResults = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM " + DeityDungeons.tableName(dungeonName));
					
					//Load all the mobs for the dungeon
					for(int j = 0; j < mobResults.rowCount(); j++) {
						try {
							String mobName = results.getString(j, "name");
							EntityType type = EntityType.fromName(results.getString(j, "type"));
							int health = results.getInteger(j, "health");
							int helm = results.getInteger(j, "helm");
							int chest = results.getInteger(j, "chest");
							int pants = results.getInteger(j, "pants");
							int feet = results.getInteger(j, "feet");
							int mobx = results.getInteger(j, "x");
							int moby = results.getInteger(j, "y");
							int mobz = results.getInteger(j, "z");
							System.out.println("done try making mob");
							Mob mob = new Mob(mobName, type, health, helm, chest, pants, feet, dungeon, mobx, moby, mobz);
							
							//Add the mob to the dungeon
							dungeon.addMob(mob);
							
							//debug
							System.out.println("added " + mobName);
						}catch(SQLDataException e) {
							DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "Unable to load mob from dungeon " + dungeonName);
							e.printStackTrace();
						}
					}


					//Finally put the dungeon object into a map with the key being the name
					dungeons.put(dungeonName, dungeon);
					//System.out.println("PUT " + name + " AND " + dungeon + "     WITH " + dungeon.getName());
					DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "Dungeon " + dungeonName + " loaded");

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
		//Put dungeon info in the main dungeon list table
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO `dungeon_list` (`name`, `number_of_players`, `world`, `x`, `y`, `z`, `yaw`, `pitch`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 
				name, playerAmount, p.getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), (int)p.getLocation().getYaw(), (int)p.getLocation().getPitch());

		//Table for the mob info
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS " + DeityDungeons.tableName(name) + " (`id` INT(16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(32) NOT NULL, `type` VARCHAR(32) NOT NULL, `health` INT(16) NOT NULL, " +
				"`helm` INT(8) NOT NULL, `chest` INT(8) NOT NULL, `pants` INT(8) NOT NULL, `feet` INT(8) NOT NULL, `x` INT(8) NOT NULL, `y` INT(8) NOT NULL, `z` INT(8) NOT NULL)");
		
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(p, "DeityDungeons", "<aqua>Dungeon <red>" + name + "<aqua> has been created! Use the <red>/dungeon addmob <aqua> command to add mobs to the dungeon");

		//add to mem
	}

	//Adds a mob to a dungeon
	//Both in the mysql database and in the
	//dungeon object in memory
	public static void addMobToDungeon(Mob mob) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO " + DeityDungeons.tableName(mob.getDungeon().getName()) + " (`name`, `type`, `health`, `helm`, `chest`, `pants`, `feet`, `x`, `y`, `z`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", mob.getName(), mob.getType().getName(), mob.getHealth(), mob.getHelm(), mob.getChest(), mob.getPants(), mob.getFeet(), mob.getX(), mob.getY(), mob.getZ());

		//for dungeon in memory
		mob.getDungeon().addMob(mob);
	}

	//Removes a mob from a dungeon
	//Both in the mysql database and from the 
	//dungeon object in memory
	public static void removeMobFromDungeon(Mob mob, Player player) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("DELETE FROM " + DeityDungeons.tableName(mob.getDungeon().getName()) + " WHERE `name`=?", mob.getName());

		//for dungeon in memory
		mob.getDungeon().removeMob(mob);

		//Make sure a player doesn't have a mob selected that doesn't exist
		if(selectedMobs.containsValue(mob)) {
			selectedMobs.remove(player);
		}
	}	

	//Used to set attributes for mobs
	//Used for everything from health to chestplates
	public static void setMobAttribute(Mob mob, String attribute, Object value) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE " + DeityDungeons.tableName(mob.getDungeon().getName()) + " SET ?=? WHERE `name`=?", attribute, value, mob.getName());
		//TODO: ?
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
		//Lets be nice, all material names are all capital letters
		material.toUpperCase();
		armor.toUpperCase();

		Material mat = Material.getMaterial(material + "_" + armor);
		if(mat == null) {
			return null;
		}else{
			return mat;
		}
	}

	public static boolean playerIsRunningDungeon(Player player) {
		for(RunningDungeon d : DeityDungeons.runningDungeons) {
			if(d.containsPlayer(player)) return true;
		}

		return false;
	}
}