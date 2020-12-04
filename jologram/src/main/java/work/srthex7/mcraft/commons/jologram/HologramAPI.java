package work.srthex7.mcraft.commons.jologram;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import work.srthex7.mcraft.commons.jologram.hologram.Hologram;
import work.srthex7.mcraft.commons.jologram.instance.HologramBuilder;
import work.srthex7.mcraft.commons.jologram.instance.Jologram;
import work.srthex7.mcraft.commons.jologram.instance.JologramInstance;

public class HologramAPI extends JavaPlugin implements Listener{
	
	Jologram jologram;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		jologram = JologramInstance.newInstance(this).renderDistance(10).hologramIndentation(0.2).checkTime(1).build();
	}
	
	@EventHandler
	public void onPlayerCommandPreProccess(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equals("/jolo")) {
			Hologram hologram = jologram.createHologram().location(event.getPlayer().getLocation()).lines("linea1", "linea2").build();
			hologram.addPlayer(event.getPlayer());
			
			event.setCancelled(true);
		}
		if (event.getMessage().equals("/jolo2")) {
			Hologram hologram = new HologramBuilder(jologram).location(event.getPlayer().getLocation()).lines("linea1", "linea2").build();
			hologram.addPlayer(event.getPlayer());
			
			event.setCancelled(true);
		}
	}
	
}
