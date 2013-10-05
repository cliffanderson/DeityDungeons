package com.imdeity.deitydungeons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityPlugin;
import com.imdeity.deitydungeons.cmd.DungeonCommandHandler;
import com.imdeity.deitydungeons.cmd.MobCommandHandler;
import com.imdeity.deitydungeons.listeners.DungeonListener;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DeityDungeons extends DeityPlugin {
	/*
	 * Leather
	 * Iron
	 * Gold
	 * Chain
	 * Diamond
	 * 
	 */

	public static DeityDungeons plugin;
	private static ArrayList<RunningDungeon> runningDungeons = new ArrayList<RunningDungeon>();
	private static ArrayList<String> runningDungeonNames = new ArrayList<String>();

	//The delay in seconds before the mobs are spawned in a dungeon
	public static int DUNGEON_DELAY;

	//The distance in blocks a player must within the finish point for the dungeon to be completed
	public static int FINISH_DISTANCE;
	
	//The distance a player must be from a mob for it to spawn
	public static int MOB_SPAWN_DISTANCE;

	public static synchronized ArrayList<RunningDungeon> getRunningDungeons() {
		return runningDungeons;
	}

	public static ArrayList<String> getRunningDungeonNames() {
		return runningDungeonNames;
	}

	@Override
	protected void initCmds() {
		registerCommand(new DungeonCommandHandler("DeityDungeons", "dungeon"));
		registerCommand(new MobCommandHandler("DeityDungeons", "mob"));
	}

	@Override
	protected void initConfig() {
		if(!this.getConfig().contains("dungeon-delay")) {
			this.getConfig().set("dungeon-delay", 5);
		}
		
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

		DUNGEON_DELAY = this.getConfig().getInt("dungeon-delay");
		FINISH_DISTANCE = this.getConfig().getInt("dungeon-finish-distance");
		MOB_SPAWN_DISTANCE = this.getConfig().getInt("mob-spawn-distance");
	}

	@Override
	protected void initDatabase() {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `dungeon_list` (" +
				"`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `dungeon_id` INT (16) NOT NULL, " +
				"`name` VARCHAR (64) NOT NULL, `world` VARCHAR (64) NOT NULL, " +
				"`x` INT (8) NOT NULL, `y` INT (8) NOT NULL, `z` INT (8) NOT NULL," +
				"`yaw` INT (8) NOT NULL, `pitch` INT (8) NOT NULL, `fx` INT (8) NOT NULL," +
				"`fy` INT (8) NOT NULL, `fz` INT (8) NOT NULL)");

		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `dungeon_info` (" +
				"`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `dungeon_id` INT (16) NOT NULL, " +
				"`name` VARCHAR (32) NOT NULL, `type` VARCHAR (32) NOT NULL, `health` INT (16) NOT NULL," +
				"`x` INT (16) NOT NULL, `y` INT (16) NOT NULL, `z` INT (16) NOT NULL, `helm` VARCHAR (1) NOT NULL, " +
				"`chest` VARCHAR (1) NOT NULL, `legs` VARCHAR (1) NOT NULL, `boots` VARCHAR (1) NOT NULL, `target` INT (1) NOT NULL DEFAULT 0)");

		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `dungeon_log` (" +
				"`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `dungeon` VARCHAR (64) NOT NULL, `players` TEXT NOT NULL, " +
				"`start` TIMESTAMP NOT NULL, `end` TIMESTAMP NOT NULL," +
				"`seconds` INT (16) NOT NULL)");	

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