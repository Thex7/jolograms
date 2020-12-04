package work.srthex7.mcraft.commons.jologram.instance;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import work.srthex7.mcraft.commons.jologram.handler.HologramHandler;

public class JologramInner implements Listener {
	
	private Jologram jologram;
	
	JologramInner(Jologram jologram) {
		this.jologram = jologram;
		Bukkit.getPluginManager().registerEvents(this, jologram.getJavaPlugin());
	}

	public void runTask(long checkTime) {
		Bukkit.getScheduler().runTaskTimer(jologram.getJavaPlugin(), () -> {
			jologram.getCraftHolograms().forEach(craftHologram -> {
				craftHologram.getWatchers().forEach((player, show) -> {
					double aproxDistance = player.getLocation().distanceSquared(craftHologram.getLocation());
					System.out.println(Math.sqrt(aproxDistance));
					
					if (aproxDistance > jologram.getRenderDistance() && show) {						
						craftHologram.setVisibility(player, false);
					} else if (aproxDistance <= jologram.getRenderDistance() && !show) {
						craftHologram.setVisibility(player, true);
					}
					

					craftHologram.teleport(player.getLocation().add(0, 2, 0));
				});
			});
		}, 20*5, checkTime);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		jologram.getCraftHolograms().forEach(holo -> {
			if (holo.getWatchers().containsKey(event.getPlayer())) {
				holo.removePlayer(event.getPlayer());
			}
		});
	}
	
	private static HologramHandler hologramHandler;
	
	public static HologramHandler getHologramHandler() {
		if (hologramHandler == null) {		
			hologramHandler = HologramHandler.newInstance();
		}
		return hologramHandler;
	}
}
