package com.imdeity.deitydungeons.cmd;

import com.imdeity.deityapi.api.DeityCommandHandler;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonAddMobCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonCreateCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonListCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonRemoveMobCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonSelectCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonSetRewardCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonStartCommand;

public class DungeonCommandHandler extends DeityCommandHandler {

	public DungeonCommandHandler(String pluginName, String baseCommandName) {
		super(pluginName, baseCommandName);
	}

	@Override
	protected void initRegisteredCommands() {
		this.registerCommand("select", new String[]{}, "<dungeon name> <amount of players>", "Selects a dungeon", new DungeonSelectCommand(), "dungeon.select");
		this.registerCommand("create", new String[]{}, "<dungeon name>", "Create a dungeon at the players location", new DungeonCreateCommand(), "dungeon.create");
		this.registerCommand("addmob", new String[]{"add"}, "<mob name> <mob type>", "Adds the mob with the given name and type to the dungeon", new DungeonAddMobCommand(), "dungeon.add");
		this.registerCommand("removemob", new String[]{"remove"}, "<mob>", "Removes a mob from a dungeon", new DungeonRemoveMobCommand(), "dungeon.remove");
		this.registerCommand("start", new String[]{}, "<dungeon name>", "Starts the specified dungeon", new DungeonStartCommand(), "dungeon.start");
		this.registerCommand("list", new String[]{"l"}, "", "Lists the created dungeons", new DungeonListCommand(), "dungeon.list");
		this.registerCommand("setreward", new String[]{"reward"}, "<amount of money>", "Sets the reward for completing the dungeon", new DungeonSetRewardCommand(), "dungeon.setreward");
	}

}
