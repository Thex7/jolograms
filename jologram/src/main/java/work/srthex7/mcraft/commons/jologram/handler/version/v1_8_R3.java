package work.srthex7.mcraft.commons.jologram.handler.version;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import work.srthex7.mcraft.commons.jologram.handler.HologramHandler;
import work.srthex7.mcraft.commons.jologram.util.Reflection;

public class v1_8_R3 implements HologramHandler {

	@Override
	public Object createHologramPacket(int id, Location location, String string) {
		PacketPlayOutSpawnEntityLiving stand = new PacketPlayOutSpawnEntityLiving();
		
		DataWatcher watcher = new DataWatcher(null);
		watcher.a(0, (byte) 0x20); //invisible
		watcher.a(2, string); // custom name
		watcher.a(3, (byte)1); // is custom name visible
		watcher.a(10, (byte)16);
		
		try {
			Reflection.setField("a", stand, id);
			Reflection.setField("b", stand, 30);
			Reflection.setField("c", stand, (int) (location.getX() * 32.0D));
			Reflection.setField("d", stand, (int) (location.getY() * 32.0D));
			Reflection.setField("e", stand, (int) (location.getZ() * 32.0D));
			Reflection.setField("l", stand, watcher);

			return stand;
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Object destroyHologramPacket(int id) {
		return new PacketPlayOutEntityDestroy(id);
	}

	@Override
	public Object moveHologramPacket(int id, Location to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object teleportHologramPacket(int id, Location to) {
		return new PacketPlayOutEntityTeleport(
				id,
				(int) (to.getX() * 32.0D),
				(int) (to.getY() * 32.0D),
				(int) (to.getZ() * 32.0D),
				(byte) ((int) (to.getYaw() * 256.0F / 360.0F)),
				(byte) ((int) (to.getPitch() * 256.0F / 360.0F)),
				false);
	}

	@Override
	public Object renameHologramPacket(int id, String text) {
		DataWatcher watcher = new DataWatcher(null);
		watcher.a(0, (byte) 0x20); //invisible
		watcher.a(2, text); // custom name
		watcher.a(3, (byte)1); // is custom name visible
		watcher.a(10, (byte)16);
		return new PacketPlayOutEntityMetadata(id, watcher, true);
	}
	
	@Override
	public void sendPacket(Player player, Object packet) {
		((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
	}

	

}
