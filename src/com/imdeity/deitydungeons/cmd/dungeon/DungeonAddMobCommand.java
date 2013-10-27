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
		//dungeon addmob dungeon skeleton 5			3
		//dungeon addmob dungeon zombie				2

		//dungeon addmob Zombie 5					2
		//dungeon addmob Skeleton					1

		if(args.length < 1) {
			DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "Usage: /dungeon addmob [dungeon] <type> [amount]");
			return true;
		}

		if(DungeonManager.dungeonExists(args[0]) && EntityType.fromName(args[1]) != null) {
			Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
			EntityType type = EntityType.fromName(args[1]);

			Mob mob = new Mob(0, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false);

			if(args.length == 2) { //dungeon addmob dungeon zombie


				DungeonManager.addMobToDungeon(mob);
				dungeon.addMob(mob);
				
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Your requested mobs were added to the dungeon");
				return true;


			}else if(args.length == 3 && DeityDungeons.isInt(args[2])) { //dungeon addmob dungeon zombie 5
				
				for(int i = 0; i < Integer.parseInt(args[2]); i++) {
					DungeonManager.addMobToDungeon(mob);
					dungeon.addMob(mob);
				}
				
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Your requested mobs were added to the dungeon");
				return true;

			}else{
				DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "Usage: /dungeon addmob [dungeon] <type> [amount]");
				return true;
			}


		}else if(DungeonManager.playerHasDungeon(player) && EntityType.fromName(args[0]) != null){
			Dungeon dungeon = DungeonManager.getPlayersDungeon(player);
			EntityType type = EntityType.fromName(args[0]);
			
			Mob mob = new Mob(0, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false);

			if(args.length == 1) { //dungeon addmob zombie 

				DungeonManager.addMobToDungeon(mob);
				dungeon.addMob(mob);
				
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Your requested mobs were added to the dungeon");
				return true;
				
			}else if(args.length == 2 && DeityDungeons.isInt(args[1])) { //dungeon addmob zombie 5
				
				for(int i = 0; i < Integer.parseInt(args[1]); i++) { 
					DungeonManager.addMobToDungeon(mob);
					dungeon.addMob(mob);
				}
				
				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Your requested mobs were added to the dungeon");
				return true;
				

			}else{
				DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "Usage: /dungeon addmob [dungeon] <type> [amount]");
				return true;
			}
		}else{
			DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "Usage: /dungeon addmob [dungeon] <type> [amount]");
			return true;
		}
	}
}