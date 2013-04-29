package com.imdeity.deitydungeons;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.records.DatabaseResults;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;

public class DungeonManager {
	//Main storage of all dungeons
	public static Map<String, Dungeon> dungeons = new HashMap<String, Dungeon>();
	
	//Map of selected dungeons for each player
	public static Map<Player, Dungeon> selectedDungeons = new HashMap<Player, Dungeon>();
	
	//Don't ever want to mess up the table name
	public static final String DUNGEON_LIST = "`dungeon_list`";


	public static void loadAllDungeons() {
		ArrayList<String> dungeonNames = new ArrayList<String>();
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM ?", DUNGEON_LIST);
		if(results != null && results.hasRows()) {
			try {
				String name = results.getString(0, "name");
				dungeonNames.add(name);
			} catch (SQLDataException e) {
				DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "Unable to load dungeons!");
				e.printStackTrace();
			}
		}

		for(String dungeonName : dungeonNames) {
			DatabaseResults mobResults = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM ?", DeityDungeons.tableName(dungeonName));
			Dungeon dungeon = new Dungeon(dungeonName);
			for(int i = 0; i < mobResults.rowCount(); i++) {
				try {
					String name = results.getString(i, "name");
					EntityType type = EntityType.fromName(results.getString(i, "type"));
					int health = results.getInteger(i, "health");
					int helm = results.getInteger(i, "helm");
					int chest = results.getInteger(i, "chest");
					int pants = results.getInteger(i, "pants");
					int feet = results.getInteger(i, "feet");
					Mob mob = new Mob(name, type, health, helm, chest, pants, feet);
					dungeon.add(mob);
				}catch(SQLDataException e) {
					DeityAPI.getAPI().getChatAPI().outSevere("DeityDungeons", "Unable to load mob from dungeon " + dungeonName);
					e.printStackTrace();
				}
			}

			dungeons.put(dungeonName, dungeon);
		}
	}
	
	public static boolean dungeonExists(String name) {
		if(getDungeonByName(name) == null) {
			return false;
		}else{
			return true;
		}
	}

	public static Dungeon getDungeonByName(String dungeonName) {
		if(dungeons.containsKey(dungeonName)) {
			return dungeons.get(dungeonName);
		}else{
			return null;
		}
	}

	public static void createDungeon(Player p, String name, int playerAmount) {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("INSERT INTO ? (`name`, `number_of_players`, `x`, `y`, `z`, `yaw`, `pitch`, `mob_x`, `mob_y`, `mob_z`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				DUNGEON_LIST, name, playerAmount, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), (int)p.getLocation().getYaw(), (int)p.getLocation().getPitch(), -1, -1, -1);
	
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(p, "DeityDungeons", "<aqua>Dungeon <red>" + name + "<aqua> has been created! Please use the <red>/dungeon setmobspawn <aqua> command to change the mob spawn location.");
	}

}
