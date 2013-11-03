package com.imdeity.deitydungeons.cmd.dungeon;

import java.sql.SQLDataException;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;
import com.imdeity.deityapi.records.DatabaseResults;
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


		//get next mob id
		//default 1 as all mysql auto increment columns start at 1
		int id = 1;

		//see if there is a row in the table, if there is set the dungeonID
		DatabaseResults results = DeityAPI.getAPI().getDataAPI().getMySQL().readEnhanced("SELECT * FROM `dungeon_info` ORDER BY `id` DESC LIMIT 1");

		if(results != null && results.hasRows()) {
			try {
				id = results.getInteger(0, "id") + 1;
			} catch (SQLDataException e) {
				DeityAPI.getAPI().getChatAPI().out("DeityDungeons", "There was an SQL error while adding a mob to a dungeon");
				e.printStackTrace();
			}
		}


		if(DungeonManager.dungeonExists(args[0]) && EntityType.fromName(args[1]) != null) {
			Dungeon dungeon = DungeonManager.getDungeonByName(args[0]);
			EntityType type = EntityType.fromName(args[1]);


			Mob mob = new Mob(id, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false, 1);

			if(args.length == 2) { //dungeon addmob dungeon zombie


				DungeonManager.addMobToDungeon(mob);

				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Your requested mobs were added to the dungeon");
				return true;


			}else if(args.length == 3 && DeityDungeons.isInt(args[2])) { //dungeon addmob dungeon zombie 5


				mob = new Mob(id, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false, Integer.parseInt(args[2]));


				DungeonManager.addMobToDungeon(mob);


				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Your requested mobs were added to the dungeon");
				return true;

			}else{
				DeityAPI.getAPI().getChatAPI().sendPlayerError(player, "DeityDungeons", "Usage: /dungeon addmob [dungeon] <type> [amount]");
				return true;
			}


		}else if(DungeonManager.playerHasDungeon(player) && EntityType.fromName(args[0]) != null){
			Dungeon dungeon = DungeonManager.getPlayersDungeon(player);
			EntityType type = EntityType.fromName(args[0]);


			Mob mob = new Mob(id, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false, 1);

			if(args.length == 1) { //dungeon addmob zombie 

				DungeonManager.addMobToDungeon(mob);

				DeityAPI.getAPI().getChatAPI().sendPlayerMessage(player, "DeityDungeons", "<green>Your requested mobs were added to the dungeon");
				return true;

			}else if(args.length == 2 && DeityDungeons.isInt(args[1])) { //dungeon addmob zombie 5


				mob = new Mob(id, type, 0, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, ArmorMaterial.AIR, dungeon, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), false, Integer.parseInt(args[1]));

				DungeonManager.addMobToDungeon(mob);


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