package com.imdeity.deitydungeons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityPlugin;
import com.imdeity.deitydungeons.cmd.DungeonCommandHandler;
import com.imdeity.deitydungeons.listeners.DungeonListener;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DeityDungeons extends DeityPlugin {
	/*
	 * Leather
	 * Iron
	 * Gold
	 * Chain
	 * Diamond
	 */

	public static DeityDungeons plugin;
	private static ArrayList<RunningDungeon> runningDungeons = new ArrayList<RunningDungeon>();
	private static ArrayList<String> runningDungeonNames = new ArrayList<String>();

	//The distance in blocks a player must within the finish point for the dungeon to be completed
	public static int FINISH_DISTANCE;
	
	//The distance a player must be from a mob for it to spawn
	public static int MOB_SPAWN_DISTANCE;
	
	public static boolean dropCommandUsed = false;
	
	public static synchronized ArrayList<RunningDungeon> getRunningDungeons() {
		return runningDungeons;
	}

	public static ArrayList<String> getRunningDungeonNames() {
		return runningDungeonNames;
	}

	@Override
	public void onDisable() {
		for(RunningDungeon rd : DeityDungeons.getRunningDungeons()) {
			for(Entity e : rd.getSpawnedEntities()) {
				e.remove();
			}
		}
	}
	
	@Override
	protected void initCmds() {
		registerCommand(new DungeonCommandHandler("DeityDungeons", "dungeon"));
	}

	@Override
	protected void initConfig() {		
		if(!this.getConfig().contains("dungeon-finish-distance")) {
			this.getConfig().set("dungeon-finish-distance", 3);
		}
		
		if(!this.getConfig().contains("mob-spawn-distance")) {
			this.getConfig().set("mob-spawn-distance", 10);
		}
		
		try {
			this.getConfig().save(new File(this.getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		FINISH_DISTANCE = this.getConfig().getInt("dungeon-finish-distance");
		MOB_SPAWN_DISTANCE = this.getConfig().getInt("mob-spawn-distance");
	}

	@Override
	protected void initDatabase() {
		//List of all dungeons with there attributes
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `dungeon_list` (" +
				"`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
				"`name` VARCHAR (64) NOT NULL, `world` VARCHAR (64) NOT NULL, " +
				"`x` INT (8) NOT NULL, `y` INT (8) NOT NULL, `z` INT (8) NOT NULL," +
				"`yaw` INT (8) NOT NULL, `pitch` INT (8) NOT NULL, `finish_x` INT (8) NOT NULL," +
				"`finish_y` INT (8) NOT NULL, `finish_z` INT (8) NOT NULL)");

		//List of all mobs and their attributes
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `dungeon_info` (" +
				"`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `dungeon_id` INT (16) NOT NULL, " +
				"`type` VARCHAR (32) NOT NULL, `health` INT (16) NOT NULL," +
				"`x` INT (16) NOT NULL, `y` INT (16) NOT NULL, `z` INT (16) NOT NULL, `helm` VARCHAR (1) NOT NULL, " +
				"`chest` VARCHAR (1) NOT NULL, `legs` VARCHAR (1) NOT NULL, `boots` VARCHAR (1) NOT NULL, `target` INT (1) NOT NULL DEFAULT 0,"
				+ "`amount` INT (16) NOT NULL, `weapon` INT (16) NOT NULL)");

		//Log of dungeon runs
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `dungeon_log` (" +
				"`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `dungeon_id` INT (16) NOT NULL, `players` TEXT NOT NULL, " +
				"`start` TIMESTAMP NOT NULL, `end` TIMESTAMP NOT NULL)");	
		
		//Chest info
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `chest_info` (`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "`dungeon_id` INT (16) NOT NULL, `chest_x` INT (16) NOT NULL,`chest_y` INT (16) NOT NULL, `chest_z` INT (16) NOT NULL, "
				+ "`item_id` INT (16) NOT NULL, `amount` INT (16) NOT NULL)");

		DungeonManager.loadAllDungeons();
	}

	@Override
	protected void initInternalDatamembers() {

	}

	@Override
	protected void initLanguage() {

	}

	@Override
	protected void initListeners() {
		this.registerListener(new DungeonListener());
	}

	@Override
	protected void initPlugin() {
		plugin = this;
	}

	@Override
	protected void initTasks() {

	}

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}