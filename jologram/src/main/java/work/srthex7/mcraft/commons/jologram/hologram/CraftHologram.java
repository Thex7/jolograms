package work.srthex7.mcraft.commons.jologram.hologram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import work.srthex7.mcraft.commons.jologram.instance.JologramInner;

public class CraftHologram implements Hologram {
	
	//Visibilidad
	private Map<Player, Boolean> visible = new HashMap<>();
	
	//Localización del holograma
	private Location location;
	
	//Separación entre lineas
	private double indentation;
	
	//Lo que vamos a enviar
	private List<RawHologram> lines;

	public CraftHologram(Location location, double indentation, List<RawHologram> lines) {
		this.location = location;
		this.indentation = indentation;
		this.lines = lines;
		this.visible = new HashMap<>();
		
		this.resize();
		this.update();
	}


	/**
	 * Devuelve los jugadores a los que va dirigidos este holograma
	 * y si es visible para ellos
	 * @return
	 */
	@Override
	public Map<Player, Boolean> getWatchers() {
		return this.visible;
	}
	
	/**
	 * Devuelve la localización del holograma
	 * @return
	 */
	@Override
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Agrega a un jugador
	 */
	@Override
	public void addPlayer(Player player) {
		this.show(player);
		this.visible.put(player, true);
	}
	
	/**
	 * Quita un jugador
	 * @param player
	 */
	@Override
	public void removePlayer(Player player) {
		this.hide(player);
		this.visible.remove(player);
	}
	
	/**
	 * Teletransporta el holograma a la ubicación dada
	 * @param location
	 */
	@Override
	public void teleport(Location location)	{
		this.location = location;
		this.resize();
		this.teleportRawHologram();
		this.update();
	}
	
	/**
	 * Establece las lineas del holograma y las actualiza
	 * @param lines
	 */
	@Override
	public void setLines(List<String> lines) {
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
	
	
	/**
	 * Establece el texto de una linea en el holograma
	 * Produce una excepción si el holograma no contiene la linea
	 * con el indice dado
	 * @param index indice de la linea
	 * @param text texto que asignaremos
	 */
	@Override
	public void setLine(int index, String text) {
		RawHologram raw = this.lines.get(index);
		raw.setText(text);
		this.renameRawHologram();
	}

	/**
	 * Agrega una linea al holograma
	 * @param text
	 */
	@Override
	public void addLine(String text) {
		this.lines.add(new CraftRawHologram(text));
		this.resize();
		this.teleportRawHologram();
		this.renameRawHologram();
	}

	/**
	 * Quita una linea del holograma
	 * @param index
	 */
	@Override
	public void removeLine(int index) {
		RawHologram raw = this.lines.remove(index);
		destroyRawHologram(raw);
		this.resize();
		this.teleportRawHologram();
	}
	
	/**
	 * Elimina todas las lineas del holograma
	 */
	@Override
	public void clear() {
		this.lines.forEach(rawHologram -> {
			destroyRawHologram(rawHologram);
		});
		this.lines = new ArrayList<>();
	}
	
	/**
	 * Establece la visibilidad de un holograma para un jugador
	 * @param player
	 * @param visible
	 */
	@Override
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
	@Override
	public boolean isVisible(Player player) {
		return this.visible.containsKey(player) && this.visible.get(player).booleanValue();
	}
	
	
	// ==================================
	//			Metodos Internos
	// ==================================
	
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
	 * Reconstruye los paquetes del holograma
	 */
	private void update() {
		this.lines.forEach(raw -> {
			raw.buildPacket();
		});
	}
	
	/**
	 * Muestra el holograma a un jugador
	 * Internamente esto vuelve a reenviar los paquetes al jugador
	 * @param player
	 */
	private void show(Player player) {
		this.lines.forEach(raw -> {
			JologramInner.getHologramHandler().sendPacket(player, raw.getPacket());
		});
	}
	
	/**
	 * Oculta el holograma al jugador
	 * Internamente envia paquetes que destruyen la entidad en el cliente
	 * @param player
	 */
	private void hide(Player player) {
		this.lines.forEach(raw -> {
			JologramInner.getHologramHandler().sendPacket(player, raw.getDestroyPacket());
		});
	}
	
	/**
	 * Crea un holograma base
	 * @param raw
	 */
	private void createRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getPacket());
			}
		});
	}
	
	/**
	 * Destruye un holograma base
	 * @param raw
	 */
	private void destroyRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getDestroyPacket());
			}
		});
	}
	
	/**
	 * Envia la linea del holograma a todos los visualizadores
	 * @param raw
	 */
	private void renameRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getRenamePacket());
			}
		});
	}
	
	/**
	 * Envia el holograma a todos los visualizadores
	 */
	private void renameRawHologram() {
		visible.forEach((player, show) -> {
			if (show) {
				lines.forEach(raw -> {
					JologramInner.getHologramHandler().sendPacket(player, raw.getRenamePacket());
				});
			}
		});
	}
	
	/**
	 * Teletransporta un holograma a su respectiva ubicación
	 * @param raw
	 */
	private void teleportRawHologram(RawHologram raw) {
		visible.forEach((player, show) -> {
			if (show) {
				JologramInner.getHologramHandler().sendPacket(player, raw.getTeleportPacket());
			}
		});
	}
	
	/**
	 * Teletransporta todos los hologramas base a su respectiva ubicación
	 */
	private void teleportRawHologram() {
		visible.forEach((player, show) -> {
			if (show) {
				lines.forEach(raw -> {
					JologramInner.getHologramHandler().sendPacket(player, raw.getTeleportPacket());
				});
			}
		});
	}
}
