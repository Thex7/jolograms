package work.srthex7.mcraft.commons.jologram.hologram;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Hologram {
	
	/**
	 * Establece la lista como holograma
	 * @param lines
	 */
	public abstract void set(List<String> lines);
	
	/**
	 * Establece el texto de una linea del holograma
	 * @param index
	 * @param string
	 */
	public abstract void setLine(int index, String text);
	
	/**
	 * Añade una nueva linea al holograma
	 * @param string
	 */
	public abstract void addLine(String text);
	
	/**
	 * Elimina una linea del holograma
	 * @param index
	 */
	public abstract void removeLine(int index);
	
	/**
	 * Elimina todas las lineas
	 */
	public abstract void clear();
	
	/**
	 * Reordena el holograma (Si hay huecos por eliminación de linea)
	 * los rellena
	 */
	public abstract void resize();
	
	/**
	 *	Espaciado entre las lineas
	 */
	public abstract void indentation(double indentation);
	
	/**
	 * Elimina el holograma de todos los jugadores
	 */
	public abstract void destroy();
	
	/**
	 * Devuelve la localización del holograma
	 * @return
	 */
	public abstract Location getLocation();
	
	/**
	 * Establece una localización para el holograma
	 * @param location
	 */
	public abstract void setLocation(Location location);
	
	/**
	 * Devuelve a todos los jugadores que pueden ver este holograma
	 * @return
	 */
	public abstract Set<Player> getViewers();
	
	/**
	 * Envia el holograma a un jugador
	 * @param player
	 */
	public abstract void send(Player player);
	
	/**
	 * Envia el holograma a todos los jugadores
	 */
	public abstract void sendAll();
	
	/**
	 * Envia un paquete que destruye el holograma para el jugador
	 * @param player
	 */
	public abstract void destroy(Player player);
	
	/**
	 * Actualiza el holograma para todos los visualizadores
	 */
	public abstract void update();
	
}