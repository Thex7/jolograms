package work.srthex7.mcraft.commons.jologram.instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import work.srthex7.mcraft.commons.jologram.hologram.CraftHologram;
import work.srthex7.mcraft.commons.jologram.hologram.CraftRawHologram;
import work.srthex7.mcraft.commons.jologram.hologram.Hologram;
import work.srthex7.mcraft.commons.jologram.hologram.RawHologram;

public class Jologram {
	
	private JavaPlugin plugin;
	private double hologramIndentation = 0.2;
	private double renderDistance;
	private long checkTime = 20;
	private Set<CraftHologram> craftHolograms;
	
	Jologram(JavaPlugin plugin, double renderDistance, double hologramIndentation, long checkTime) { 
		this.plugin = plugin;
		this.hologramIndentation = hologramIndentation;
		this.renderDistance = renderDistance;
		this.checkTime = checkTime;
		this.craftHolograms = ConcurrentHashMap.newKeySet();
	}
	
	public JavaPlugin getJavaPlugin() {
		return this.plugin;
	}
	
	public double getRenderDistance() {
		return this.renderDistance;
	}
	
	public double getHologramIndentation() {
		return this.hologramIndentation;
	}
	
	public long getCheckTime() {
		return this.checkTime;
	}
	
	public Set<CraftHologram> getCraftHolograms() {
		return craftHolograms;
	}
	
	// =======================================
	//		Metodos para crear hologramas
	// =======================================
	
	public HologramBuilder createHologram() {
		return new HologramBuilder(this);
	}
	
	public Hologram createHologram(Location location, double indentation, List<String> lines) {
		List<RawHologram> raw = new ArrayList<>();
		for (String line : lines) {
			raw.add(new CraftRawHologram(line));
		}
		
		CraftHologram craftHologram = new CraftHologram(location, indentation, raw);
		
		addCraftHologram(craftHologram);
		
		return craftHologram;
	}
	
	public Hologram createHologram(Location location, List<String> lines) {
		return createHologram(location, hologramIndentation, lines);
	}
	
	public Hologram createHologram(Location location, double indentation, String... lines) {
		return createHologram(location, indentation, Arrays.asList(lines));
	}
	
	public Hologram createHologram(Location location, String... lines) {
		return createHologram(location, hologramIndentation, Arrays.asList(lines));
	}
	
	
	
	// =======================================
	//			Metodos internos
	// =======================================
	
	protected void addCraftHologram(CraftHologram craftHologram) {
		this.craftHolograms.add(craftHologram);
	}
	
	private void removeCraftHologram(CraftHologram craftHologram) {
		this.craftHolograms.add(craftHologram);
	}
	
}
