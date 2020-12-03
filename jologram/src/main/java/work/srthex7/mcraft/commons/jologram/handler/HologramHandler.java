package work.srthex7.mcraft.commons.jologram.handler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import work.srthex7.mcraft.commons.jologram.handler.version.v1_7_R4;
import work.srthex7.mcraft.commons.jologram.handler.version.v1_7_R4_PH;
import work.srthex7.mcraft.commons.jologram.util.VersionUtil;

public interface HologramHandler {

	/**
	 * Crea un holograma
	 * @param id
	 * @param location
	 * @param string
	 * @return
	 */
	Object createHologramPacket(int id, Location location, String string);
	
	/**
	 * Crea un paquete que destruye un holograma
	 * @param id
	 * @return
	 */
	Object destroyHologramPacket(int id);
	
	/**
	 * Mueve un holograma con vectors
	 * @param id
	 * @param to
	 * @return
	 */
	Object moveHologramPacket(int id, Location to);
	
	/**
	 * Teletransporta un holograma a una posición dada
	 * @param id
	 * @param to
	 * @return
	 */
	Object teleportHologramPacket(int id, Location to);
	
	/**
	 * Cambia el nombre de un holograma
	 * @param id
	 * @param text
	 * @return
	 */
	Object renameHologramPacket(int id, String text);
	
	/**
	 * Envia el paquete a un jugador
	 * @param player
	 * @param packet
	 */
	void sendPacket(Player player, Object packet);
	
	/**
	 * Llama una instancia acorde a la version del servidor
	 * Note: Ejecutar este metodo es costoso ya que utiliza reflexión
	 * por lo que se recomienda guardar la instancia para su reutilización.
	 * @return TabHandler
	 */
	public static HologramHandler newInstance() {
		
		if (VersionUtil.getServerVersion().equals("v1_7_R4")) { //Soporte 1.7.10 & 1.7.10 Protocol Hack
			
			try {
				Class.forName("net.minecraft.server.v1_7_R4.NetworkManager").getMethod("getVersion", (Class<?>[]) null);
				return new v1_7_R4_PH();
			} catch (NoSuchMethodException e) {
				return new v1_7_R4();
			} catch (SecurityException | ClassNotFoundException e) {
			//	e.printStackTrace();
			}
			
		} else {
			try {
				return (HologramHandler) Class.forName("work.srthex7.hologram.handler.version." + VersionUtil.getServerVersion()).newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			//	e.printStackTrace();
			}
			
		}
		
		return null;
	}
}
