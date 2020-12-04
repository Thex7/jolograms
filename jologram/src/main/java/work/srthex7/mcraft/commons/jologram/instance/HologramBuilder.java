package work.srthex7.mcraft.commons.jologram.instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

import work.srthex7.mcraft.commons.jologram.hologram.CraftHologram;
import work.srthex7.mcraft.commons.jologram.hologram.CraftRawHologram;
import work.srthex7.mcraft.commons.jologram.hologram.Hologram;
import work.srthex7.mcraft.commons.jologram.hologram.RawHologram;

public class HologramBuilder {
		
	private Jologram jologram;
	private Location location;
	private List<String> lines;
	private double indentation;
	
	public HologramBuilder(Jologram jologram) {
		this.jologram = jologram;
		this.indentation = jologram.getHologramIndentation();
	}
	
	public HologramBuilder location(Location location) {
		this.location = location;
		return this;
	}
	
	public HologramBuilder indentation(double indentation) {
		this.indentation = indentation;
		return this;
	}
	
	public HologramBuilder lines(List<String> lines) {
		this.lines = lines;
		return this;
	}
	
	public HologramBuilder lines(String... lines) {
		this.lines = new ArrayList<String>(Arrays.asList(lines));
		return this;
	}
	
	public Hologram build() {
		List<RawHologram> bHolo = new ArrayList<>();
		
		for (String line : lines) {
			bHolo.add(new CraftRawHologram(line));
		}
		
		this.lines = null;
		
		CraftHologram craftHologram = new CraftHologram(location, indentation, bHolo);
		jologram.addCraftHologram(craftHologram);
		return craftHologram;
	}
}