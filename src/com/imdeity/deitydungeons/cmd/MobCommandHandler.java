package com.imdeity.deitydungeons.cmd;

import com.imdeity.deityapi.api.DeityCommandHandler;
import com.imdeity.deitydungeons.cmd.mob.MobSelectCommand;
import com.imdeity.deitydungeons.cmd.mob.MobSetChestCommand;
import com.imdeity.deitydungeons.cmd.mob.MobSetFeetCommand;
import com.imdeity.deitydungeons.cmd.mob.MobSetHealthCommand;
import com.imdeity.deitydungeons.cmd.mob.MobSetHelmCommand;
import com.imdeity.deitydungeons.cmd.mob.MobSetPantsCommand;
import com.imdeity.deitydungeons.cmd.mob.MobSetSpawn;
import com.imdeity.deitydungeons.cmd.mob.MobSetTypeCommand;

public class MobCommandHandler extends DeityCommandHandler {

	public MobCommandHandler(String pluginName, String baseCommandName) {
		super(pluginName, baseCommandName);
	}

	@Override
	protected void initRegisteredCommands() {
		this.registerCommand("select", new String[]{}, "<mob name>", "Selects the given mob in the selected dungeon", new MobSelectCommand(), "mob.select");
		this.registerCommand("settype", new String[]{"type"}, "<entity type>", "Sets the type of the mob", new MobSetTypeCommand(), "mob.settype");
		this.registerCommand("sethealth", new String[]{"health"}, "<health (2 = 1 heart)>", "Sets the health of the mob", new MobSetHealthCommand(), "mob.sethealth");
		this.registerCommand("sethelm", new String[]{"helm"}, "<leather|iron|gold|diamond|none>", "Sets the helmet for the mob", new MobSetHelmCommand(), "mob.sethelm");
		this.registerCommand("setchest", new String[]{"chest"}, "<leather|iron|gold|diamond|none>", "Sets the chestplate for the mob", new MobSetChestCommand(), "mob.setchest");
		this.registerCommand("setpants", new String[]{"pants"}, "<leather|iron|gold|diamond|none>", "Sets the leggings for the mob", new MobSetPantsCommand(), "mob.setpants");
		this.registerCommand("setfeet", new String[]{"feet", "setboots", "boots"}, "<leather|iron|gold|diamond|none>", "Sets the boots for the mob", new MobSetFeetCommand(), "mob.setfeet");
		this.registerCommand("setspawn", new String[]{}, "", "Sets the mob's spawn location to the user's location", new MobSetSpawn(), "mob.setspawn");
	}

}