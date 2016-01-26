package com.itsagentd.mobmoney;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.itsagentd.mobmoney.listeners.LEntityDeath;
import com.itsagentd.mobmoney.util.ConfigFile;

public class MobMoney extends JavaPlugin {
	
	public final static Logger log = Logger.getLogger("Minecraft");
	public static FileConfiguration config;
	
	@Override
	public void onEnable() {
		ConfigFile configfile = new ConfigFile();
		configfile.loadConfig("config.yml", this);
		
		config = configfile.getConfig();
		
		MobEconomy.initializeEconomy(getServer());
		
		getServer().getPluginManager().registerEvents(new LEntityDeath(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
