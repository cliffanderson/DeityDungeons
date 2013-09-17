package com.imdeity.deitydungeons;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.records.DatabaseResults;
import com.imdeity.deitydungeons.obj.ArmorMaterial;
import com.imdeity.deitydungeons.obj.ArmorPiece;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DungeonManager {
	/*
	 * `dungeon_list`
	 * 
	 * id
	 * dungeon_id
	 * name
	 * world
	 * x
	 * y
	 * z
	 * yaw
	 * pitch
	 * 
	 * 
	 * `dungeon_info`
	 * 
	 * id
	 * dungeon_id
	 * name
	 * type
	 * health
	 * x
	 * y
	 * z
	 * helm
	 * chest
	 * legs
	 * boots
	 * 
	 * 
	 * 
	 * 
	 */

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

		//All database queries
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_list`");
		DatabaseResults mobResults = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_info`");

		//Load all dungeon names into an arraylist
		if(results != null && results.hasRows()) {

			for(int i = 0; i < results.rowCount(); i++) {
				try {
					int dungeonID = results.getInteger(i, "dungeon_id");
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
					}

					int x = results.getInteger(i, "x");
					int y = results.getInteger(i, "y");
					int z = results.getInteger(i, "z");
					int yaw = results.getInteger(i, "yaw");
					int pitch = results.getInteger(i, "pitch");
					int reward = results.getInteger(i, "reward");

					Dungeon dungeon = new Dungeon(dungeonID, dungeonName, numberOfPlayers, world, x, y, z, yaw, pitch, reward);
					dungeons.put(dungeonName, dungeon);

					DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "Dungeon " + dungeonName + " loaded");
				}catch(SQLException e) {
					DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "There was an SQLException while loading a dungeon: ");
					e.printStackTrace();
				}
			}

			//Load all the mobs for all the dungeons

			if(mobResults != null && mobResults.hasRows()) {
				
				for(int i = 0; i < mobResults.rowCount(); i++) {
					try {
						int dungeonID = mobResults.getInteger(i, "dungeon_id");
						System.out.println(dungeonID);
						if(getDungeonByID(dungeonID) == null) {
							//Dungeon with that id does not exist
							DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "The dungeon with dungeon ID " + dungeonID + " does not exist");
							continue;
						}

						String mobName = mobResults.getString(i, "name");
						String typeName = mobResults.getString(i, "type");
						EntityType type = EntityType.fromName(typeName);
						int health = mobResults.getInteger(i, "health");
						int helm = mobResults.getInteger(i, "helm");
						int chest = mobResults.getInteger(i, "chest");
						int pants = mobResults.getInteger(i, "legs");
						int feet = mobResults.getInteger(i, "boots");
						int mobx = mobResults.getInteger(i, "x");
						int moby = mobResults.getInteger(i, "y");
						int mobz = mobResults.getInteger(i, "z");
						int delay = mobResults.getInteger(i, "delay");
						
						Mob mob = new Mob(mobName, type, health, helm, chest, pants, feet, getDungeonByID(dungeonID), mobx, moby, mobz, delay);

						//Add the mob to the dungeon
						getDungeonByID(dungeonID).addMob(mob);

					}catch(SQLException e) {
						DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "There was an SQLException while loading a mob: ");
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static Collection<Dungeon> getDungeons() {
		return dungeons.values();
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

	public static Dungeon getDungeonByID(int id) {
		for(Dungeon dungeon : dungeons.values()) {
			if(dungeon.getID() == id) {
				return dungeon;
			}
		}

		return null;
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
	public static void createDungeon(Player player, String name, int playerAmount) {
		int dungeonID = 0;
		//see if there is a row in the table, if there is set the dungeonID
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_list` ORDER BY `dungeon_id` LIMIT 1");

		if(results != null && results.hasRows()) {
			try {
				dungeonID = results.getInteger(0, "dungeon_id");
			} catch (SQLDataException e) {
				DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "There was an SQL error while creating a dungeon");
				e.printStackTrace();
			}
		}

		//Put dungeon info in the main dungeon list table
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO `dungeon_list` (`dungeon_id`, `name`, `number_of_players`, `world`, `x`, `y`, `z`, `yaw`, `pitch`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				dungeonID, name, playerAmount, player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), (int)player.getLocation().getYaw(), (int)player.getLocation().getPitch());

		//Table for the mob info
		//DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS " + DeityDungeons.tableName(name) + " (`id` INT(16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(32) NOT NULL, `type` VARCHAR(32) NOT NULL, `health` INT(16) NOT NULL, " +
		//		"`helm` INT(8) NOT NULL, `chest` INT(8) NOT NULL, `pants` INT(8) NOT NULL, `feet` INT(8) NOT NULL, `x` INT(8) NOT NULL, `y` INT(8) NOT NULL, `z` INT(8) NOT NULL)");



		//add to mem
		Dungeon dungeon = new Dungeon(dungeonID, name, playerAmount, player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), (int)player.getLocation().getYaw(), (int)player.getLocation().getPitch(), 50);
		dungeons.put(name, dungeon);
		
		selectedDungeons.put(player, dungeon);
	}

	//Adds a mob to a dungeon
	//Both in the mysql database and in the
	//dungeon object in memory
	public static void addMobToDungeon(Player player, Mob mob) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO `dungeon_info` (`dungeon_id`, `name`, `type`, `health`, `x`, `y`, `z`, `helm`, `chest`, `legs`, `boots`, `delay`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				mob.getDungeon().getID(), mob.getName(), mob.getType().getName(), mob.getHealth(), mob.getX(), mob.getY(), mob.getZ(), mob.getHelm(), mob.getChest(), mob.getPants(), mob.getFeet(), mob.getDelay());

		//for dungeon in memory
		mob.getDungeon().addMob(mob);
		selectedMobs.put(player, mob);
	}

	//Removes a mob from a dungeon
	//Both in the mysql database and from the 
	//dungeon object in memory
	public static void removeMobFromDungeon(Mob mob, Player player) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("DELETE FROM `dungeon_info` WHERE `dungeon_id`=? AND `name`=?", mob.getDungeon().getID(), mob.getName());

		//for dungeon in memory
		mob.getDungeon().removeMob(mob);

		//Make sure a player doesn't have a mob selected that doesn't exist
		if(selectedMobs.containsValue(mob)) {
			selectedMobs.remove(player);
		}
	}	

	//Used to set attributes for mobs
	//Used for everything from health to chestplates
	public static void setMobArmor(Mob mob, ArmorMaterial material, ArmorPiece armor) {
		switch(armor) {
		case HELMET:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `helm`=? WHERE `dungeon_id`=? AND `name`=?", Material.getMaterial(material.getName().toUpperCase() + "_" + armor.getName().toUpperCase()).getId(), mob.getDungeon().getID(), mob.getName());
			mob.setHelm(Material.getMaterial(material.getName().toUpperCase() + "_" + armor.getName().toUpperCase()).getId());
			break;
		case CHESTPLATE:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `chest`=? WHERE `dungeon_id`=? AND `name`=?", Material.getMaterial(material.getName().toUpperCase() + "_" + armor.getName().toUpperCase()).getId(), mob.getDungeon().getID(), mob.getName());
			mob.setChest(Material.getMaterial(material.getName().toUpperCase() + "_" + armor.getName().toUpperCase()).getId());
			break;
		case LEGGINGS:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `legs`=? WHERE `dungeon_id`=? AND `name`=?", Material.getMaterial(material.getName().toUpperCase() + "_" +armor.getName().toUpperCase()).getId(), mob.getDungeon().getID(), mob.getName());
			mob.setPants(Material.getMaterial(material.getName().toUpperCase() + "_" + armor.getName().toUpperCase()).getId());
			break;
		case BOOTS:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `boots`=? WHERE `dungeon_id`=? AND `name`=?", Material.getMaterial(material.getName().toUpperCase() + "_" +armor.getName().toUpperCase()).getId(), mob.getDungeon().getID(), mob.getName());
			mob.setFeet(Material.getMaterial(material.getName().toUpperCase() + "_" + armor.getName().toUpperCase()).getId());
			break;
		}
	}

	public static void setMobHealth(Mob mob, int health) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `health`=? WHERE `dungeon_id`=? AND `name`=?", health, mob.getDungeon().getID(), mob.getName());

		mob.setHealth(health);
	}

	public static void setMobType(Mob mob, EntityType type) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `type`=? WHERE `dungeon_id`=? AND `name`=?", type.getName(), mob.getDungeon().getID(), mob.getName());

		mob.setType(type);
	}

	public static void setMobSpawn(Mob mob, int x, int y, int z) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `x`=?, `y`=?, `z`=? WHERE `dungeon_id`=? AND `name`=?", x, y, z, mob.getDungeon().getID(), mob.getName());
	
		mob.setX(x);
		mob.setY(y);
		mob.setZ(z);
	}
	
	public static void setMobDelay(Mob mob, int delay) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `delay`=? WHERE `dungeon_id`=? AND `name`=?", delay, mob.getDungeon().getID(), mob.getName());
	
		mob.setDelay(delay);
	}

	//Used to check if a player has a selected dungeon
	public static boolean playerHasDungeon(Player player) {
		return getPlayersDungeon(player) != null;
	}

	//Used to check if a player has a selected mob
	public static boolean playerHasMob(Player player) {
		return getPlayersMob(player) != null;
	}
	
	public static boolean playerIsRunningDungeon(Player player) {
		for(RunningDungeon d : DeityDungeons.getRunningDungeons()) {
			if(d.containsPlayer(player)) return true;
		}

		return false;
	}

	public static void setDungeonReward(Dungeon dungeon, int reward) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_list` SET `reward`=?", reward);
		
		dungeon.setReward(reward);
	}
}