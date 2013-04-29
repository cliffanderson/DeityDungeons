package com.imdeity.deitydungeons.cmd;

import com.imdeity.deityapi.api.DeityCommandHandler;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonAddMobCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonCreateCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonSelectCommand;

public class DungeonCommandHandler extends DeityCommandHandler {

	public DungeonCommandHandler(String pluginName, String baseCommandName) {
		super(pluginName, baseCommandName);
	}

	@Override
	protected void initRegisteredCommands() {
		this.registerCommand("", new String[]{}, "<dungeon name>", "Selects a dungeon", new DungeonSelectCommand(), "dungeon.select");
		this.registerCommand("create", new String[]{}, "<dungeon name>", "Create a dungeon at the players location", new DungeonCreateCommand(), "dungeon.create");
		this.registerCommand("addmob", new String[]{}, "<mob name> <mob type>", "Adds the mob with the given name and type to the dungeon", new DungeonAddMobCommand(), "dungeon.addmob");
	}

}
