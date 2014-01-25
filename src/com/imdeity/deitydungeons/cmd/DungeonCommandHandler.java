package com.imdeity.deitydungeons.cmd;

import com.imdeity.deityapi.api.DeityCommandHandler;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonAddChestCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonAddMobCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonCreateCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonDeleteCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonDropCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonEditMobCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonListCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonRemoveMobCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonSelectCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonSetFinishCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonStartCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonStopCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonWarpCommand;
import com.imdeity.deitydungeons.cmd.dungeon.DungeonWarpFinishCommand;

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
		this.registerCommand("setfinish", new String[]{"finish"}, "", "Sets the finish point of the dungeon", new DungeonSetFinishCommand(), "dungeon.setfinish");
		this.registerCommand("delete", new String[]{}, "<dungeon name>", "DELETES an entire dungeon, including all of the mobs", new DungeonDeleteCommand(), "dungeon.delete");
		this.registerCommand("stop", new String[]{"s"}, "<dungeon name>", "Stops a dungeon", new DungeonStopCommand(), "dungeon.stop");
		this.registerCommand("editmob", new String[]{}, "<dungeon> <mobid> <helm> <chest> <legs> <boots> <health> <type> <target>", "Edits all of the attributes of a mob", new DungeonEditMobCommand(), "dungeon.editmob");
		this.registerCommand("warp", new String[]{"w"}, "<dungeon name>", "Warps to a dungeon", new DungeonWarpCommand(), "dungeon.warp");
		this.registerCommand("warpfinish", new String[]{"wf"}, "<dungeon name>", "Warps to a dungeon's finish", new DungeonWarpFinishCommand(), "dungeon.warpfinish");
		this.registerCommand("addchest", new String[]{"chest"}, "<dungeon name>", "Registers a chest", new DungeonAddChestCommand(), "dungeon.addchest");
		this.registerCommand("drop", new String[]{}, "", "Admin command", new DungeonDropCommand(), "dungeon.drop");
	}
}