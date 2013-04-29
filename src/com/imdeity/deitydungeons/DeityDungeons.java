package com.imdeity.deitydungeons;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityPlugin;
import com.imdeity.deitydungeons.cmd.DungeonCommandHandler;

public class DeityDungeons extends DeityPlugin {
	
	public static DeityDungeons plugin;
	
	@Override
	protected void initCmds() {
		registerCommand(new DungeonCommandHandler("DeityDungeons", "dungeon"));
	}
	
	@Override
	protected void initConfig() {
		
	}
	
	@Override
	protected void initDatabase() {
		DeityAPI.getAPI().getDataAPI().getMySQL().write("CREATE TABLE IF NOT EXISTS ? " +
				"(`id` INT (16) NOT NULL AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR (64) NOT NULL, " +
				"`number_of_players` INT (16) NOT NULL, `world` VARCHAR (64) NOT NULL," +
				"`x` INT (8) NOT NULL, `y` INT (8) NOT NULL, `z` INT (8) NOT NULL" +
				"`yaw` INT (8) NOT NULL, `pitch` INT (8) NOT NULL," +
				
				"`mob_x` INT (8) NOT NULL, `mob_y` INT (8) NOT NULL, `mob_z` INT (8) NOT NULL)", DungeonManager.DUNGEON_LIST);
	}
	
	@Override
	protected void initInternalDatamembers() {
		
	}
	
	@Override
	protected void initLanguage() {
		
	}
	
	@Override
	protected void initListeners() {
		
	}
	
	@Override
	protected void initPlugin() {
		
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
		return "dungeons_" + suffix;
	}
}
