package work.srthex7.mcraft.commons.jologram.handler.version;

import java.util.List;

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
import net.minecraft.server.v1_7_R4.WatchableObject;
import net.minecraft.util.gnu.trove.map.TIntObjectMap;
import work.srthex7.mcraft.commons.jologram.handler.HologramHandler;
import work.srthex7.mcraft.commons.jologram.util.Reflection;

public class v1_7_R4_PH implements HologramHandler {

	// ==================
	//	Reescribir esta cosa 
	// ==================
	
	Packet[] convertTo17(PacketPlayOutSpawnEntityLiving packet) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		int id = (int) Reflection.getValue("a", packet);
		int x = (int) Reflection.getValue("c", packet);
		int y = (int) Reflection.getValue("d", packet) + 12;
		int z = (int) Reflection.getValue("e", packet);
		
		DataWatcher readWatcher = (DataWatcher) Reflection.getValue("l", packet);
		TIntObjectMap<?> datavalues = (TIntObjectMap<?>) Reflection.getValue("dataValues", readWatcher);
		
		String text = (String) ((WatchableObject)datavalues.get(2)).b();
		
		Packet[] packets = new Packet[3];
		
		int witherId = id-1;
		
		PacketPlayOutSpawnEntity skull = new PacketPlayOutSpawnEntity();
			Reflection.setField("a", skull, witherId);
			Reflection.setField("b", skull, (int) x);
			Reflection.setField("c", skull, (int)((y/32.0D + 54.45)*32.0D));
			Reflection.setField("d", skull, (int) z);
			Reflection.setField("j", skull, 66);
		
		PacketPlayOutSpawnEntityLiving horse = new PacketPlayOutSpawnEntityLiving();
			DataWatcher watcher = new DataWatcher(null);
			int horseId = id;
			watcher.a(0, (byte)0);
			watcher.a(1, (short)300);
			watcher.a(2, text); // custom name
			watcher.a(10, text); // custom name
			watcher.a(3, (byte)1); // is custom name visible
			watcher.a(11, (byte)1); // is custom name visible
			watcher.a(4, (byte)1); // is silent
			watcher.a(5, (byte)1); // no gravity
			watcher.a(12, -1700000);
			
			Reflection.setField("a", horse, horseId);
			Reflection.setField("b", horse, 100);
			Reflection.setField("c", horse, x);
			Reflection.setField("d", horse, y);
			Reflection.setField("e", horse, z);
			Reflection.setField("l", horse, watcher);
		 
		PacketPlayOutAttachEntity attach = new PacketPlayOutAttachEntity();
			Reflection.setField("b", attach, horseId);
			Reflection.setField("c", attach, witherId);
		
		packets[0] = skull;
		packets[1] = horse;
		packets[2] = attach;
		
		return packets;
	}
	
	Packet[] convertTo17(PacketPlayOutEntityTeleport packet) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		int id = (int) Reflection.getValue("a", packet);
		int x = (int) Reflection.getValue("b", packet);
		int y = (int) Reflection.getValue("c", packet) + 12;
		int z = (int) Reflection.getValue("d", packet);
		
		Packet[] packets = new Packet[2];
		
		PacketPlayOutEntityTeleport horse = new PacketPlayOutEntityTeleport(
				id,
				x,
				y,
				z,
				(byte) 0,
				(byte) 0,
				false,
				false);
		
		PacketPlayOutEntityTeleport skull = new PacketPlayOutEntityTeleport(
				id-1,
				x,
				(int)((y/32.0D + 54.45)*32.0D),
				z,
				(byte) 0,
				(byte) 0,
				false,
				false);
		
		packets[0] = horse;
		packets[1] = skull;
		return packets;
	}
	
	Packet convertTo17(PacketPlayOutEntityMetadata packet) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		String text = (String) ((WatchableObject)((List<?>)Reflection.getValue("b", packet)).get(2)).b();
		
		DataWatcher watcher = new DataWatcher(null);
		int id = (int) Reflection.getValue("a", packet);
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
	public Object createHologramPacket(int id, Location location, String string) {
		try {
			
			PacketPlayOutSpawnEntityLiving stand = new PacketPlayOutSpawnEntityLiving();
			
			DataWatcher watcher = new DataWatcher(null);
				watcher.a(0, (byte) 0x20); //invisible
				watcher.a(2, string); // custom name
				watcher.a(3, (byte)1); // is custom name visible
				watcher.a(10, (byte)16);
			Reflection.setField("a", stand, id);
			Reflection.setField("b", stand, 30);
			Reflection.setField("c", stand, (int) (location.getX() * 32.0D));
			Reflection.setField("d", stand, (int) (location.getY() * 32.0D));
			Reflection.setField("e", stand, (int) (location.getZ() * 32.0D));
			Reflection.setField("l", stand, watcher);

			return stand;
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void sendPacket(Player player, Object packet) {
		
		if (is18(player)) {
			((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet) packet);
			
		} else if (packet instanceof PacketPlayOutSpawnEntityLiving){
			
			try {
				Packet[] packets = convertTo17((PacketPlayOutSpawnEntityLiving) packet);
				
				for (Object obj : packets) {
					((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet) obj);
				}
				
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
		} else if (packet instanceof PacketPlayOutEntityTeleport) {
		
			try {
				Packet[] packets = convertTo17((PacketPlayOutEntityTeleport) packet);
				
				for (Object obj : packets) {
					((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet) obj);
				}
				
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (packet instanceof PacketPlayOutEntityMetadata) {
			
			try {
				Packet packets = convertTo17((PacketPlayOutEntityMetadata) packet);
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(packets);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
		
		
			((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet) packet);
		}
		
	}
	
    public byte n(boolean flag) { // CraftBukkit - public
        byte b0 = 0;

        if (flag) {
            b0 = (byte) (b0 | 16);
        } else {
            b0 &= -17;
        }

        return b0;
    }

	public boolean is18(Player player) {
		return (((CraftPlayer)player).getHandle().playerConnection.networkManager.getVersion() >= 28); //47
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
		return new PacketPlayOutEntityTeleport(
				id,
				(int) (to.getX() * 32.0D),
				(int) (to.getY() * 32.0D),
				(int) (to.getZ() * 32.0D),
				(byte) ((int) (to.getYaw() * 256.0F / 360.0F)),
				(byte) ((int) (to.getPitch() * 256.0F / 360.0F)),
				false,
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
}
