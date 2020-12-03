package work.srthex7.mcraft.commons.jologram.hologram;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import work.srthex7.mcraft.commons.jologram.instance.JologramInner;

public class CraftHologram extends Hologram {
	
	Location location;
	
	private List<RawHologram> lines;
	
	double indentation;
	
	Set<Player> viewers;
	
	public CraftHologram(Location location, double indentation, List<RawHologram> lines) {
		this.location = location;
		this.lines = lines;
		this.viewers = new HashSet<>();
		this.indentation = indentation;
	}
	
	@Override
	public void set(List<String> lines) {
		destroy();
		lines.forEach(line -> {
			this.lines.add(new CraftRawHologram(line));
		});
	}
	
	@Override
	public void setLine(int index, String text) {
		RawHologram raw = removeLinePacket(index);
		lines.get(index).setText(text);
		raw.buildPacket();
		sendLinePacket(index);
	}

	@Override
	public void addLine(String text) {
		lines.add(new CraftRawHologram(text));
	}

	@Override
	public void removeLine(int index) {
		removeLinePacket(index);
		lines.remove(index);
	}

	@Override
	public void resize() {
		
		Location top = location.clone().add(0, indentation * lines.size(), 0);
		
		for (RawHologram raw : lines) {
			raw.setLocation(top.clone());
			raw.buildPacket();
			top.subtract(0, indentation, 0);
		}
	}

	@Override
	public void indentation(double indentation) {
		this.indentation = indentation;
	}

	@Override
	public void destroy() {
		for (Player player : viewers) {
			destroy(player);
		}
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(Location location) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Set<Player> getViewers() {
		return viewers;
	}
	
	@Override
	public void send(Player player) {
		for (RawHologram raw : lines) {
			JologramInner.getHologramHandler().sendPacket(player, raw.getPacket());
		}
		viewers.add(player);
	}
	
	@Override
	public void destroy(Player player) {
		for (RawHologram raw : lines) {
			JologramInner.getHologramHandler().sendPacket(player, raw.getDestroyPacket());
		}
	}
	
	@Deprecated
	@Override
	public void update() {
		
		for (Player player : viewers) {
			destroy(player);
			send(player);
		}
		
	}

	@Override
	public void sendAll() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			send(player);
		}
	}

	@Override
	public void clear() {
		this.destroy();
		this.lines.clear();
	}
	
	private RawHologram removeLinePacket(int index) {
		RawHologram raw = lines.get(index);
		viewers.forEach(player -> {
			JologramInner.getHologramHandler().sendPacket(player, raw.getDestroyPacket());
		});
		return raw;
	}
	
	private RawHologram sendLinePacket(int index) {
		RawHologram raw = lines.get(index);
		viewers.forEach(player -> {
			JologramInner.getHologramHandler().sendPacket(player, raw.getPacket());
		});
		return raw;
	}
}
