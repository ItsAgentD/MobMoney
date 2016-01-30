package com.itsagentd.mobmoney.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.itsagentd.mobmoney.MobEconomy;
import com.itsagentd.mobmoney.MobMoney;

import net.milkbowl.vault.economy.Economy;

public class LEntityDeath implements Listener {
	
	private static HashMap<UUID, Boolean> spawnerList = new HashMap<UUID, Boolean>();
	
	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
		
		if (e.getSpawnReason() == SpawnReason.SPAWNER) {
			
			spawnerList.put(e.getEntity().getUniqueId(), true);
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {

		if (event.getEntity() instanceof LivingEntity) {

			LivingEntity entity = event.getEntity();
			Economy econ = MobEconomy.getEcon();

			FileConfiguration config = MobMoney.config;

			String name = null;
			double amount = 0;

			Player player = entity.getKiller();

			if (player == null)
				return;
			
			boolean spawnedFromSpawner = false;
			// Check if the mob was spawned via a Spawner
			if (spawnerList.get(entity.getUniqueId()) != null ) {
				spawnedFromSpawner = true;
			}

			if (spawnedFromSpawner == true && config.getBoolean("general.moneyfromspawners") == true) {
				spawnerList.remove(entity.getUniqueId());
				return;
			}
			switch (entity.getName()) {

			case "Bat":
				amount = config.getDouble("amounts.passive.bat");
				break;

			case "Blaze":
				amount = config.getDouble("amounts.aggressive.blaze");
				break;

			case "Cave Spider":
				amount = config.getDouble("amounts.aggressive.cavespider");
				break;

			case "Chicken":
				amount = config.getDouble("amounts.passive.chicken");
				break;

			case "Cow":
				amount = config.getDouble("amounts.passive.cow");
				break;

			case "Creeper":
				amount = config.getDouble("amounts.aggressive.creeper");
				break;

			case "Ender Dragon":
				amount = config.getDouble("amounts.aggressive.enderdragon");
				break;

			case "Enderman":
				amount = config.getDouble("amounts.aggressive.enderman");
				break;

			case "Endermite":
				amount = config.getDouble("amounts.aggressive.endermite");
				break;

			case "Ghast":
				amount = config.getDouble("amounts.aggressive.ghast");
				break;

			case "Giant":
				amount = config.getDouble("amounts.aggressive.giant");
				break;

			case "Guardian":
				if (entity instanceof Guardian && ((Guardian) entity).isElder() == true) {
					// Elder Guardian
					name = "Elder Guardian";
					amount = config.getDouble("amounts.aggressive.elderguardian");
				} else {
					// Normal Guardian
					amount = config.getDouble("amounts.aggressive.guardian");
				}
				break;

			case "Horse":
				amount = config.getDouble("amounts.passive.horse");
				break;

			case "Iron Golem":
				amount = config.getDouble("amounts.aggressive.irongolem");
				break;

			case "Magma Cube":
				amount = config.getDouble("amounts.aggressive.magmacube");
				break;

			case "Mooshroom":
				amount = config.getDouble("amounts.passive.mooshroom");
				break;

			case "Ocelot":
				amount = config.getDouble("amounts.passive.ocelot");
				break;

			case "Pig":
				amount = config.getDouble("amounts.passive.pig");
				break;

			case "Rabbit":
				if (entity instanceof Rabbit && ((Rabbit) entity).getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY) {
					// Killer Rabbit
					name = "Killer Rabbit";
					amount = config.getDouble("amounts.aggressive.killerrabbit");
				} else {
					// Normal Rabbit
					amount = config.getDouble("amounts.passive.rabbit");
				}
				break;

			case "Sheep":
				amount = config.getDouble("amounts.passive.sheep");
				break;

			case "Silverfish":
				amount = config.getDouble("amounts.aggressive.silverfish");
				break;

			case "Skeleton":
				if (entity instanceof Skeleton && ((Skeleton) entity).getSkeletonType() == SkeletonType.WITHER) {
					// Wither Skeleton
					name = "Wither Skeleton";
					amount = config.getDouble("amounts.aggressive.witherskeleton");
				} else {
					// Normal Skeleton
					amount = config.getDouble("amounts.aggressive.skeleton");
				}
				break;

			case "Slime":
				amount = config.getDouble("amounts.aggressive.slime");
				break;

			case "Spider":
				amount = config.getDouble("amounts.aggressive.spider");
				break;

			case "Squid":
				amount = config.getDouble("amounts.passive.squid");
				break;

			case "Villager":
				amount = config.getDouble("amounts.passive.villager");
				break;

			case "Witch":
				amount = config.getDouble("amounts.aggressive.witch");
				break;

			case "Wither":
				amount = config.getDouble("amounts.aggressive.wither");
				break;

			case "Wolf":
				amount = config.getDouble("amounts.aggressive.wolf");
				break;

			case "Zombie":
				amount = config.getDouble("amounts.aggressive.zombie");
				break;

			case "Zombie Pig":
				amount = config.getDouble("amounts.aggressive.zombiepig");
				break;
			}

			if (name == null)
				name = entity.getName();

			if (amount > 0) {
				econ.depositPlayer(player, amount);

				if (config.getBoolean("general.consoleoutput"))
					MobMoney.log.info(
							player.getDisplayName() + " killed a " + name + " and received " + amount + " currency.");
			}
		}
	}
}
