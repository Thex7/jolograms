package work.srthex7.mcraft.commons.jologram.handler.version;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import work.srthex7.mcraft.commons.jologram.handler.HologramHandler;
import work.srthex7.mcraft.commons.jologram.util.Reflection;

public class v1_7_R4 implements HologramHandler {

	@Override
	public Object createHologramPacket(int id, Location location, String string) {
		Object[] packets = null;
		try {
			
			packets = new Object[3];
			
			int witherId = id-1;
			
			PacketPlayOutSpawnEntity skull = new PacketPlayOutSpawnEntity();
				Reflection.setField("a", skull, witherId);
				Reflection.setField("b", skull, (int) ((location.getX()) * 32.0D));
				Reflection.setField("c", skull, (int) ((location.getY() + 54.45) * 32.0D));
				Reflection.setField("d", skull, (int) ((location.getZ()) * 32.0D));
				Reflection.setField("j", skull, 66);
			
			PacketPlayOutSpawnEntityLiving horse = new PacketPlayOutSpawnEntityLiving();
				DataWatcher watcher = new DataWatcher(null);
				int horseId = id;
				watcher.a(0, (byte)0);
				watcher.a(1, (short)300);
				watcher.a(2, string); // custom name
				watcher.a(10, string); // custom name
				watcher.a(3, (byte)1); // is custom name visible
				watcher.a(11, (byte)1); // is custom name visible
				watcher.a(4, (byte)1); // is silent
				watcher.a(5, (byte)1); // no gravity
				watcher.a(12, -1700000);
				
				Reflection.setField("a", horse, horseId);
				Reflection.setField("b", horse, 100);
				Reflection.setField("c", horse, (int) location.getX() * 32);
				Reflection.setField("d", horse, (int) location.getY() * 32);
				Reflection.setField("e", horse, (int) location.getZ() * 32);
				Reflection.setField("l", horse, watcher);
			 
			PacketPlayOutAttachEntity attach = new PacketPlayOutAttachEntity();
				Reflection.setField("b", attach, horseId);
				Reflection.setField("c", attach, witherId);
			
			packets[0] = skull;
			packets[1] = horse;
			packets[2] = attach;
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		return packets;
	}
	
	@Override
	public Object destroyHologramPacket(int id) {
		// TODO Auto-generated method stub
		return new PacketPlayOutEntityDestroy(id);
	}

	@Override
	public Object moveHologramPacket(int id, Location to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object teleportHologramPacket(int id, Location to) {
		
		Packet[] packets = new Packet[2];
		
		PacketPlayOutEntityTeleport horse = new PacketPlayOutEntityTeleport(
				id,
				(int) (to.getX() * 32.0D),
				(int) (to.getY() * 32.0D),
				(int) (to.getZ() * 32.0D),
				(byte) 0,
				(byte) 0,
				false,
				false);
		
		PacketPlayOutEntityTeleport skull = new PacketPlayOutEntityTeleport(
				id-1,
				(int) (to.getX() * 32.0D),
				(int)((to.getY() + 54.45)*32.0D),
				(int) (to.getZ() * 32.0D),
				(byte) 0,
				(byte) 0,
				false,
				false);
		
		packets[0] = horse;
		packets[1] = skull;
		return packets;
	}

	@Override
	public Object renameHologramPacket(int id, String text) {
		DataWatcher watcher = new DataWatcher(null);
		watcher.a(0, (byte)0);
		watcher.a(1, (short)300);
		watcher.a(2, text); // custom name
		watcher.a(10, text); // custom name
		watcher.a(3, (byte)1); // is custom name visible
		watcher.a(11, (byte)1); // is custom name visible
		watcher.a(4, (byte)1); // is silent
		watcher.a(5, (byte)1); // no gravity
		watcher.a(12, -1700000);
		return new PacketPlayOutEntityMetadata(id, watcher, true);
	}

	@Override
	public void sendPacket(Player player, Object packet) {
		
		if (packet instanceof Packet[]) {
			
			for (Packet obj : (Packet[])packet) {
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(obj);
			}
			
		} else {
			((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet) packet);
		}
		
	}
}
