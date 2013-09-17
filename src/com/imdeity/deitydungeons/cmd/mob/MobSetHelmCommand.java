package com.imdeity.deitydungeons.cmd.mob;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.ArmorMaterial;
import com.imdeity.deitydungeons.obj.ArmorPiece;
import com.imdeity.deitydungeons.obj.Mob;

public class MobSetHelmCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		if(args.length != 1) {
			return false;
		}
		
		if(!DungeonManager.playerHasMob(player)) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You must first select a mob.");
			return true;
		}
		
		String materialName = args[0].toUpperCase();
		ArmorMaterial material = ArmorMaterial.getByName(materialName);
		
		if(material == null) {
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "Invalid material.");
			return true;
		}
		
		Mob mob = DungeonManager.getPlayersMob(player);
		
		DungeonManager.setMobArmor(mob, material, ArmorPiece.HELMET);
		
		DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "You have the set the chestplate for the mob " + mob.getName() + " to " + material.getName().toLowerCase());

		
		return true;
	}

}
