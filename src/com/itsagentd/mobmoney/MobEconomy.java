package com.itsagentd.mobmoney;

import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class MobEconomy {
	
	private static Economy econ = null;
		
	public static void initializeEconomy(Server server) {
		
		setupEconomy(server);
	}
	
	private static boolean setupEconomy(Server server) {
		
        RegisteredServiceProvider<Economy> economyProvider = server.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }

	public static Economy getEcon() {
		
		return econ;
	}
}
