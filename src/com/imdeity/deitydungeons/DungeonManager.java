package com.imdeity.deitydungeons;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	//Main storage of all dungeons
	public static Map<String, Dungeon> dungeons;

	//Map of selected dungeons for each player
	public static Map<Player, Dungeon> selectedDungeons;

	//Loads all dungeons into memory from database
	public static void loadAllDungeons() {
		//Define maps so same method can be used for reloading dungeons
		//Redefining the maps basically clears them
		dungeons = new HashMap<String, Dungeon>();

		//Redefine these to prevent players having selected
		//Objects that are no longer valid
		selectedDungeons = new HashMap<Player, Dungeon>();

		//All database queries
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_list`");
		DatabaseResults mobResults = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_info`");

		//Load all dungeon names into an arraylist
		if(results != null && results.hasRows()) {

			for(int i = 0; i < results.rowCount(); i++) {
				try {
					int dungeonID = results.getInteger(i, "id");
					String dungeonName = results.getString(i, "name");

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

					int fx = results.getInteger(i, "finish_x");
					int fy = results.getInteger(i, "finish_y");
					int fz = results.getInteger(i, "finish_z");

					Dungeon dungeon = new Dungeon(dungeonID, dungeonName, world, x, y, z, yaw, pitch, fx, fy, fz);
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
						
						if(getDungeonByID(dungeonID) == null) {
							//Dungeon with that id does not exist
							DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "The dungeon with dungeon ID " + dungeonID + " does not exist");
							continue;
						}

						int mobid = mobResults.getInteger(i, "id");
						String typeName = mobResults.getString(i, "type");
						EntityType type = EntityType.fromName(typeName);
						int health = mobResults.getInteger(i, "health");
						String helm = mobResults.getString(i, "helm");
						String chest = mobResults.getString(i, "chest");
						String pants = mobResults.getString(i, "legs");
						String feet = mobResults.getString(i, "boots");
						int mobx = mobResults.getInteger(i, "x");
						int moby = mobResults.getInteger(i, "y");
						int mobz = mobResults.getInteger(i, "z");
						boolean target = mobResults.getInteger(i, "target") == 0 ? false : true;
						int amount = mobResults.getInteger(i, "amount");
						System.out.println();
						

						Mob mob = new Mob(mobid, type, health, ArmorMaterial.getByChar(helm.charAt(0)), ArmorMaterial.getByChar(chest.charAt(0)), ArmorMaterial.getByChar(pants.charAt(0)), ArmorMaterial.getByChar(feet.charAt(0)), getDungeonByID(dungeonID), mobx, moby, mobz, target, amount);

						System.out.println("loaded amount: " + amount);
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
	public static void createDungeon(Player player, String name) {
		int dungeonID = 0;

		//see if there is a row in the table, if there is set the dungeonID
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_list` ORDER BY `id` DESC LIMIT 1");

		if(results != null && results.hasRows()) {
			try {
				dungeonID = results.getInteger(0, "id") + 1;
				System.out.println("dungeon id of newly created dungeon: " + dungeonID);
				System.out.println("dungeonid: " + dungeonID);
			} catch (SQLDataException e) {
				DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "There was an SQL error while creating a dungeon");
				e.printStackTrace();
			}
		}


		//Put dungeon info in the main dungeon list table
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO `dungeon_list` (`name`, `world`, `x`, `y`, `z`, `yaw`, `pitch`, `finish_x`, `finish_y`, `finish_z`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				name, player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), (int)player.getLocation().getYaw(), (int)player.getLocation().getPitch(), -1, -1, -1);


		//reload all dungeons
		loadAllDungeons();
		
		//memory for player
		selectedDungeons.put(player, getDungeonByName(name));
	}

	//Creates a mob for a dungeon
	//Both in the mysql database and in the
	//dungeon object in memory
	public static void addMobToDungeon(Mob mob) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO `dungeon_info` (`dungeon_id`, `type`, `health`, `x`, `y`, `z`, `helm`, `chest`, `legs`, `boots`, `amount`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				mob.getDungeon().getID(), mob.getType().getName(), mob.getHealth(), mob.getX(), mob.getY(), mob.getZ(), mob.getHelm().getChar(), mob.getChest().getChar(), mob.getPants().getChar(), mob.getFeet().getChar(), mob.getAmount());

		//for dungeon in memory
		mob.getDungeon().addMob(mob);
	}

	//Removes a mob from a dungeon
	//Both in the mysql database and from the 
	//dungeon object in memory
	public static void removeMobFromDungeon(Mob mob, Player player) {
		//sql
		DeityAPI.getAPI().getDataAPI().getMySQL().write("DELETE FROM `dungeon_info` WHERE `id`=?", mob.getID());

		//for dungeon in memory
		mob.getDungeon().removeMob(mob);
	}	

	//Used to set attributes for mobs
	//Used for everything from health to chestplates
	public static void setMobArmor(Mob mob, ArmorMaterial material, ArmorPiece armor) {
		switch(armor) {
		case HELMET:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `helm`=? WHERE `id`=?", ArmorMaterial.getChar(material), mob.getID());
			mob.setHelm(material);
			break;
		case CHESTPLATE:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `chest`=? WHERE `id`=?", ArmorMaterial.getChar(material), mob.getID());
			mob.setChest(material);
			break;
		case LEGGINGS:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `legs`=? WHERE `id`=?", ArmorMaterial.getChar(material), mob.getID());
			mob.setPants(material);
			break;
		case BOOTS:
			DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `boots`=? WHERE `id`=?", ArmorMaterial.getChar(material), mob.getID());
			mob.setFeet(material);
			break;
		}
	}

	public static void setMobHealth(Mob mob, int health) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `health`=? WHERE `id`=?", health, mob.getID());

		mob.setHealth(health);
	}

	public static void setMobType(Mob mob, EntityType type) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `type`=? WHERE `id`=?", type.getName(), mob.getID());

		mob.setType(type);
	}

	public static void setMobSpawn(Mob mob, int x, int y, int z) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `x`=?, `y`=?, `z`=? WHERE `id`=?", x, y, z, mob.getID());

		mob.setX(x);
		mob.setY(y);
		mob.setZ(z);
	}

	public static void setMobTarget(Mob mob, boolean target) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_info` SET `target`=? WHERE `id`=?", target, mob.getID());

		mob.setTarget(target);
	}

	//Used to check if a player has a selected dungeon
	public static boolean playerHasDungeon(Player player) {
		return getPlayersDungeon(player) != null;
	}
	
	//If a player is CURRENTLY running a dungeon, and has not finished yet
	public static boolean playerIsRunningDungeon(Player player) {
		for(RunningDungeon d : DeityDungeons.getRunningDungeons()) {
			if(d.containsPlayer(player)) return true;
		}

		return false;
	}
	
	//gets the dungeon that a player was a part of, regardless is they have finished or not
	public static RunningDungeon getPlayersRunningDungeon(Player player) {
		for(RunningDungeon d : DeityDungeons.getRunningDungeons()) {
			if(d.getOriginalPlayers().contains(player)) {
				return d;
			}
		}
		
		return null;
	}
	
	public static void endDungeon(RunningDungeon running) {
		//kill all living mobs
		running.removeAllMobs();
		
		//"end" dungeon
		DeityDungeons.getRunningDungeons().remove(running);
		DeityDungeons.getRunningDungeonNames().remove(running.getDungeon().getName());
		
		//add record in database
		addDungeonRunRecord(running.getDungeon(), running.getStart(), running.getOriginalPlayers());
	}

	public static void addDungeonRunRecord(Dungeon dungeon, Date start, ArrayList<Player> players) {
		String playerList = "";

		for(Player player : players) {
			playerList += player.getName() + ",";
		}

		playerList = playerList.substring(0, playerList.length() - 1);

		Date now = new Date();

		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO `dungeon_log` (`dungeon_id`, `players`, `start`, `end`) VALUES (?, ?, ?, ?)", dungeon.getID(), playerList, start, now);
	}

	public static void setDungeonFinish(Dungeon dungeon, int x, int y, int z) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("UPDATE `dungeon_list` SET `finish_x`=?, `finish_y`=?, `finish_z`=? WHERE `id`=?", x, y, z, dungeon.getID());

		//memory
		dungeon.setDungeonFinish(x, y, z);
	}

	public static void startDungeon(final Dungeon dungeon, final Player[] players) {
		for(Player player : players) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<yellow>The dungeon <red>" + dungeon.getName() + " <yellow>has started, good luck!");
			player.teleport(dungeon.getSpawn());
		}

		DeityDungeons.getRunningDungeons().add(new RunningDungeon(dungeon, players));
		DeityDungeons.getRunningDungeonNames().add(dungeon.getName());
	
		Cloud.onDungeonStart(players, dungeon.getID());
	}

	public static void deleteDungeon(Dungeon dungeon) {
		//Remove dungeon in memory
		dungeons.remove(dungeon.getName());

		//sql dungeon list
		DeityAPI.getAPI().getDataAPI().getMySQL().write("DELETE FROM `dungeon_list` WHERE `id`=? ", dungeon.getID());

		//sql dungeon info (mob list)
		DeityAPI.getAPI().getDataAPI().getMySQL().write("DELETE FROM `dungeon_info` WHERE `dungeon_id`=?", dungeon.getID());	
	}
}