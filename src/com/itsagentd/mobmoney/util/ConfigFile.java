package com.itsagentd.mobmoney.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.itsagentd.mobmoney.MobMoney;

public class ConfigFile {
	
	private static FileConfiguration config;
	private static File file;
	private String filename;
	private MobMoney mobmoney;

	public void loadConfig(String filename, MobMoney mobmoney) {
		
		file = new File(mobmoney.getDataFolder(), filename);
		this.filename = filename;
		this.mobmoney = mobmoney;
		
		if (!file.exists())
			createConfig(filename, mobmoney);
		
		config = new YamlConfiguration();
		try {
			
			config.load(file);
			MobMoney.log.info("[MobMoney] Configuration file loaded successfully!");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	private void createConfig(String filename, MobMoney mobmoney) {
		
		file.getParentFile().mkdirs();
		copy(mobmoney.getResource(filename));
	}

	private void copy(InputStream resource) {
		
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			
			while((len = resource.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
			out.close();
			resource.close();
			
			MobMoney.log.info("[MobMoney] Configuration file missing. Copied defaults!");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getConfig() {
		
		return config;
	}
	
	public void saveConfig() {
		
		try {
			
			config.save(file);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void reloadConfig() {
		loadConfig(this.filename, this.mobmoney);
	}
}
