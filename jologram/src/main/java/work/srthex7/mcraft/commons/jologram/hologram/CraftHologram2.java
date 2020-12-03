package work.srthex7.mcraft.commons.jologram.hologram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import work.srthex7.mcraft.commons.jologram.instance.JologramInner;

public class CraftHologram2 {
	
	//Visibilidad
	private Map<Player, Boolean> visible = new HashMap<>();
	
	//Localización del holograma
	private Location location;
	
	//Separación entre lineas
	private double indentation;
	
	//Lo que vamos a enviar
	private List<RawHologram> lines;

	public CraftHologram2(Location location, double indentation, List<RawHologram> lines) {
		this.location = location;
		this.indentation = indentation;
		this.lines = lines;
		this.visible = new HashMap<>();
		
		this.resize();
		this.update();
	}

	/**
	 * Establece las lineas del holograma y las actualiza
	 * @param lines
	 */
	public void set(List<String> lines) {
		if (this.lines.size() > lines.size()) {
			int diff = this.lines.size() - lines.size();
			while (diff > 0) {
				int index = this.lines.size()-1;
				RawHologram raw = this.lines.remove(index);
				this.destroyRawHologram(raw);
				diff--;
			}
		
		} else if (this.lines.size() < lines.size()) {
			int diff = lines.size() - this.lines.size();
			while (diff > 0) {
				
				RawHologram raw = new CraftRawHologram(this.location, ChatColor.RED + "");
				raw.buildPacket();
				createRawHologram(raw);
				
				this.lines.add(raw);
				diff--;
			}
		}
		
		for (int index = 0; index < lines.size(); index++) {
			this.lines.get(index).setText(lines.get(index));
		}
		
		this.resize();
		
		this.teleportRawHologram();
		this.renameRawHologram();
	}

	public Map<Player, Boolean> getWatchers() {
		return this.visible;
	}
	
	
	/**
	 * Establece una linea del holograma
	 * @param index
	 * @param text
	 */
	public void setLine(int index, String text) {
		RawHologram raw = this.lines.get(index);
		raw.setText(text);
	}

	/**
	 * Agrega una linea al holograma
	 * @param text
	 */
	public void addLine(String text) {
		this.lines.add(new CraftRawHologram(text));
	}

	/**
	 * Quita una linea del holograma
	 * @param index
	 */
	public void removeLine(int index) {
		RawHologram raw = this.lines.remove(index);
		destroyRawHologram(raw);
	}
	
	/**
	 * Elimina todas las lineas del holograma
	 */
	public void clear() {
		this.lines = new ArrayList<>();
	}

	/**
	 * Acomoda las lineas del holograma 
	 * Las lineas pueden quedar vacias luego de una eliminación
	 * este metodo se encarga de reacomodar las lineas para evitar esto
	 */
	public void resize() {
		Location top = location.clone().add(0, indentation * lines.size(), 0);
		
		for (RawHologram raw : lines) {
			raw.setLocation(top.clone());
			top.subtract(0, indentation, 0);
		}
	}

	/**
	 * Establece el espacio de separación de las lineas
	 * @param indentation
	 */
	public void indentation(double indentation) {
		this.indentation = indentation;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Reconstruye los paquetes del holograma
	 */
	public void update() {
		this.lines.forEach(raw -> {
			raw.buildPacket();
		});
	}

	/**
	 * Agrega a un jugador
	 */
	public void addPlayer(Player player) {
		this.show(player);
		this.visible.put(player, true);
	}
	
	/**
	 * Quita un jugador
	 * @param player
	 */
	public void removePlayer(Player player) {
		this.hide(player);
		this.visible.remove(player);
	}
	
	/**
	 * Muestra el holograma a un jugador
	 * Internamente esto vuelve a reenviar los paquetes al jugador
	 * @param player
	 */
	public void show(Player player) {
		this.lines.forEach(raw -> {
			JologramInner.getHologramHandler().sendPacket(player, raw.getPacket());
		});
	}
	
	/**
	 * Oculta el holograma al jugador
	 * Internamente envia paquetes que destruyen la entidad en el cliente
	 * @param player
	 */
	public void hide(Player player) {
		this.lines.forEach(raw -> {
			JologramInner.getHologramHandler().sendPacket(player, raw.getDestroyPacket());
		});
	}
	
	public void createRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getPacket());
			}
		});
	}

	public void destroyRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getDestroyPacket());
			}
		});
	}
	
	/**
	 * Establece la visibilidad de un holograma para un jugador
	 * @param player
	 * @param visible
	 */
	public void setVisibility(Player player, boolean visible) {
		this.visible.put(player, visible);
		if (visible) {
			show(player);
		} else {
			hide(player);
		}
	}
	
	/**
	 * Comprueba si el jugador puede ver el holograma
	 * @param player
	 * @return
	 */
	public boolean isVisible(Player player) {
		return this.visible.containsKey(player) && this.visible.get(player).booleanValue();
	}
	
	/**
	 * Envia la linea del holograma a todos los visualizadores
	 * @param raw
	 */
	public void renameRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getRenamePacket());
			}
		});
	}
	
	/**
	 * Envia el holograma a todos los visualizadores
	 */
	public void renameRawHologram() {
		visible.forEach((player, show) -> {
			if (show) {
				lines.forEach(raw -> {
					JologramInner.getHologramHandler().sendPacket(player, raw.getRenamePacket());
				});
			}
		});
	}
	
	public void teleportRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getTeleportPacket());
			}
		});
	}
	
	public void teleportRawHologram() {
		visible.forEach((player, show) -> {
			if (show) {
				lines.forEach(raw -> {
					JologramInner.getHologramHandler().sendPacket(player, raw.getTeleportPacket());
				});
			}
		});
	}
}
