package work.srthex7.mcraft.commons.jologram.instance;

import org.bukkit.plugin.java.JavaPlugin;

public class JologramInstance {
	
	private JavaPlugin plugin;
	private double renderDistance = -1;
	private double hologramIndentation = 0.2;
	private long checkTime = 20l;
	
	JologramInstance(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public static JologramInstance newInstance(JavaPlugin plugin) {
		return new JologramInstance(plugin);
	}
	
	public JologramInstance renderDistance(double renderDistance) {
		this.renderDistance = renderDistance*renderDistance;
		return this;
	}
	
	public JologramInstance hologramIndentation(double hologramIndentation) {
		this.hologramIndentation = hologramIndentation;
		return this;
	}
	
	public JologramInstance checkTime(long ticks) {
		this.checkTime = ticks;
		return this;
	}
	
	public Jologram build() {
		
		Jologram jologram = new Jologram(this.plugin, this.renderDistance, this.hologramIndentation, this.checkTime);
		
		if (this.renderDistance > 0) {
			new JologramInner(jologram).runTask(this.checkTime);
		}
		
		return jologram;
	}
	
}
