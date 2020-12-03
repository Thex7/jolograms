package work.srthex7.mcraft.commons.jologram;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import work.srthex7.mcraft.commons.jologram.hologram.CraftHologram2;
import work.srthex7.mcraft.commons.jologram.instance.HologramBuilder;
import work.srthex7.mcraft.commons.jologram.instance.Jologram;
import work.srthex7.mcraft.commons.jologram.instance.JologramBuilder;

public class HologramAPI extends JavaPlugin implements Listener{
	
	Jologram jologram;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	//	this.hologramTask();
		jologram = JologramBuilder.newInstance(this).renderDistance(10).hologramIndentation(0.2).checkTime(1).build();
	}
	
	@EventHandler
	public void onPlayerCommandPreProccess(PlayerCommandPreprocessEvent event) {
		/*
		if (event.getMessage().equals("/raw")) {
			RawHologram raw = new CraftRawHologram(event.getPlayer().getLocation(), "Hologram TEST");
			raw.buildPacket();
			hologramHandler.sendPacket(event.getPlayer(), raw.getPacket());
			event.getPlayer().sendMessage("Holograma generado");
			event.setCancelled(true);
		}
		
		if (event.getMessage().equals("/rawbow")) {
			RawHologram raw = new CraftRawHologram(event.getPlayer().getLocation(), "Hologram Rainbow");
			raw.buildPacket();

			hologramHandler.sendPacket(event.getPlayer(), raw.getPacket());
			event.getPlayer().sendMessage("Holograma generado");
			
			new BukkitRunnable() {
				@Override
				public void run() {
					raw.setText("Random: " + Math.random());
					hologramHandler.sendPacket(event.getPlayer(), raw.getRenamePacket());
				}
			}.runTaskTimer(this, 0l, 0l);
			
			event.setCancelled(true);
		}
		
		if (event.getMessage().equals("/holo")) {
			Hologram holo = new CraftHologram(event.getPlayer().getLocation(), 0.25, new ArrayList<>());
			holo.addLine("Line 1");
			holo.addLine("Line 2");
			holo.addLine("final line");
			holo.resize();
			holo.send(event.getPlayer());
			event.getPlayer().sendMessage("Holograma generado");
			event.setCancelled(true);
		}
		
		if (event.getMessage().equals("/global")) {
			Hologram holo = new CraftHologram(event.getPlayer().getLocation(), 0.25, new ArrayList<>());
			holo.addLine("Line 1");
			holo.addLine("Line 2");
			holo.addLine("final line");
			holo.resize();
			for (Player player : Bukkit.getOnlinePlayers()) {
				holo.send(player);
			}
			event.getPlayer().sendMessage("Holograma generado");
			event.setCancelled(true);
		}
		
		if (event.getMessage().equals("/i")) {
			Hologram holo = new CraftHologram2(event.getPlayer().getLocation(), 0.25, new ArrayList<>());
			holo.addLine("Line 1");
			holo.addLine("Line 2");
			holo.addLine("Line 3");
			holo.addLine("final line");
			holo.resize();
			holo.update();
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				holo.send(player);
			}
			event.getPlayer().sendMessage("Holograma generado");
			
			List<String> content = Arrays.asList("------", "<random>", "------");
			
			Bukkit.getScheduler().runTaskTimer(this, () -> {
				
				List<String> result = new ArrayList<>();
				
				for (String s : content) {
					result.add(s.replaceAll("<random>", Math.random()+""));
				}
				
				holo.set(result);
				event.getPlayer().sendMessage("UPDATE");
			}, 60l, 0l);
			event.setCancelled(true);
		}
		*/
		if (event.getMessage().equals("/jolo")) {
			CraftHologram2 hologram = jologram.createHologram().location(event.getPlayer().getLocation()).lines("linea1", "linea2").build();
			hologram.addPlayer(event.getPlayer());
			
			event.setCancelled(true);
		}
		if (event.getMessage().equals("/jolo2")) {
			CraftHologram2 hologram = new HologramBuilder(jologram).location(event.getPlayer().getLocation()).lines("linea1", "linea2").build();
			hologram.addPlayer(event.getPlayer());
			
			event.setCancelled(true);
		}
	}
	/*
	private static HologramHandler hologramHandler;
	
	public static HologramHandler getHologramHandler() {
		if (hologramHandler == null) {		
			hologramHandler = HologramHandler.newInstance();
		}
		return hologramHandler;
	}
	
	void hologramTask() {
		Bukkit.getScheduler().runTaskTimer(this, () -> {
			CraftHologram2.getHolograms().forEach(craftHologram -> {
				craftHologram.getViewers().forEach(player -> {
					double aproxDistance = HologramUtil.aproxDistance(player.getLocation(), craftHologram.getLocation());
					System.out.println(aproxDistance);

					if (aproxDistance > 10.0 && craftHologram.isVisible(player)) {						
						craftHologram.setVisibility(player, false);
					} else if (aproxDistance <= 10 && !craftHologram.isVisible(player)) {
						craftHologram.setVisibility(player, true);
					}
				});
			});
		}, 20l, 20l);
	}
	*/
}
