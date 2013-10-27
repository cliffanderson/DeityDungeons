package com.imdeity.deitydungeons.cmd.dungeon;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deitydungeons.DeityDungeons;
import com.imdeity.deitydungeons.DungeonManager;
import com.imdeity.deitydungeons.obj.ArmorMaterial;
import com.imdeity.deitydungeons.obj.Dungeon;
import com.imdeity.deitydungeons.obj.Mob;

public class DungeonAddMobCommand extends DeityCommandReceiver {

	@Override
	public boolean onConsoleRunCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerRunCommand(Player player, String[] args) {
		//dungeon addmob dungeon skeleton 5
		//dungeon addmob dungeon zombie
				
		if(args.length == 2 && DungeonManager.dungeonExists(args[0]) && EntityType.fromName(args[1]) != null) {
			EntityType type = EntityType.fromName(args[1]);
			Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
			
			DungeonManager.addMobToDungeon(new Mob(0, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false));
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>A " + type.getName() + " was added to <red>" + dungeon.getName());
			return true;
		}
		
		if(args.length == 3 && DungeonManager.dungeonExists(args[0]) && EntityType.fromName(args[1]) != null && DeityDungeons.isInt(args[2])) {
			EntityType type = EntityType.fromName(args[1]);
			Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
			
			for(int i = 0; i < Integer.parseInt(args[2]); i++) {
				DungeonManager.addMobToDungeon(new Mob(0, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false));
			}
			
			String plural = Integer.parseInt(args[2]) == 1 ? "" : "s";
			String plural2 = Integer.parseInt(args[2]) == 1 ? " was" : " were"; 
			
			DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>"+ Integer.parseInt(args[2]) + type.getName() + plural + plural2 + " added to <red>" + dungeon.getName());
			
			return true;
		}
		
		
		DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "Usage: /dungeon addmob <type> [amount]");
		return true;
	}

}