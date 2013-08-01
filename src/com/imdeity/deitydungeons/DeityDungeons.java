package com.imdeity.deitydungeons;

import java.util.ArrayList;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityPlugin;
import com.imdeity.deitydungeons.cmd.DungeonCommandHandler;
import com.imdeity.deitydungeons.cmd.MobCommandHandler;
import com.imdeity.deitydungeons.listeners.DungeonListener;
import com.imdeity.deitydungeons.obj.RunningDungeon;

public class DeityDungeons extends DeityPlugin {
	
	public static DeityDungeons plugin;
	public static ArrayList<RunningDungeon> runningDungeons = new ArrayList<RunningDungeon>();
	
	@Override
	protected void initCmds() {
		registerCommand(new DungeonCommandHandler("DeityDungeons", "dungeon"));
		registerCommand(new MobCommandHandler("DeityDungeons", "mob"));
	}
	
	@Override
	protected void initConfig() {
		
	}
	
	@Override
	protected void initDatabase() {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS `dungeon_list` (" +
				"`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR (64) NOT NULL, " +
				"`number_of_players` INT (16) NOT NULL, `world` VARCHAR (64) NOT NULL," +
				"`x` INT (8) NOT NULL, `y` INT (8) NOT NULL, `z` INT (8) NOT NULL," +
				"`yaw` INT (8) NOT NULL, `pitch` INT (8) NOT NULL)");
		
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

	public static String tableName(String suffix) {
		return "`dungeon_" + suffix + "`";
	}
}
