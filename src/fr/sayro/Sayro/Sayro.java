package fr.sayro.Sayro;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Sayro extends JavaPlugin {

	@Override
	public void onDisable() {
	
	}
	
	@Override
	public void onEnable() {
		
	PluginManager pm = getServer().getPluginManager();
	
	pm.registerEvents(new SayroListener(this), this);
	
	}
}
	
	

