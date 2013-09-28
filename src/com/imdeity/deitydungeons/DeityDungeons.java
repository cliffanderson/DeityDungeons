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
	
	public static DeityDungeons plugin;
	private static ArrayList<RunningDungeon> runningDungeons = new ArrayList<RunningDungeon>();
	private static ArrayList<String> runningDungeonNames = new ArrayList<String>();
	
	public static long DUNGEON_DELAY = 0;
	
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
		if(!this.getConfig().contains("default-dungeon-delay")) {
			this.getConfig().set("default-dungeon-delay", 5000);
			try {
				this.getConfig().save(new File(this.getDataFolder(), "config.yml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DUNGEON_DELAY = this.getConfig().getLong("default-dungeon-delay");
		
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
				"`name` VARCHAR (32) NOT NULL, `type` VARCHAR (32) NOT NULL, `health` INT (16) NOT NULL, `delay` INT (16) NOT NULL, " +
				"`x` INT (16) NOT NULL, `y` INT (16) NOT NULL, `z` INT (16) NOT NULL, `helm` INT (16) NOT NULL, " +
				"`chest` INT (16) NOT NULL, `legs` INT (16) NOT NULL, `boots` INT (16) NOT NULL)");
		
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
