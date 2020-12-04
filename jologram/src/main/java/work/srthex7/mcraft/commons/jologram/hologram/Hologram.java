package work.srthex7.mcraft.commons.jologram.hologram;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Hologram {

	/**
	 * Devuelve los jugadores a los que va dirigidos este holograma
	 * y si es visible para ellos
	 * @return
	 */
	Map<Player, Boolean> getWatchers();

	/**
	 * Devuelve la localización del holograma
	 * @return
	 */
	Location getLocation();

	/**
	 * Agrega a un jugador
	 */
	void addPlayer(Player player);

	/**
	 * Quita un jugador
	 * @param player
	 */
	void removePlayer(Player player);

	/**
	 * Teletransporta el holograma a la ubicación dada
	 * @param location
	 */
	void teleport(Location location);

	/**
	 * Establece las lineas del holograma y las actualiza
	 * @param lines
	 */
	void setLines(List<String> lines);

	/**
	 * Establece el texto de una linea en el holograma
	 * Produce una excepción si el holograma no contiene la linea
	 * con el indice dado
	 * @param index indice de la linea
	 * @param text texto que asignaremos
	 */
	void setLine(int index, String text);

	/**
	 * Agrega una linea al holograma
	 * @param text
	 */
	void addLine(String text);

	/**
	 * Quita una linea del holograma
	 * @param index
	 */
	void removeLine(int index);

	/**
	 * Elimina todas las lineas del holograma
	 */
	void clear();

	/**
	 * Establece la visibilidad de un holograma para un jugador
	 * @param player
	 * @param visible
	 */
	void setVisibility(Player player, boolean visible);

	/**
	 * Comprueba si el jugador puede ver el holograma
	 * @param player
	 * @return
	 */
	boolean isVisible(Player player);

}