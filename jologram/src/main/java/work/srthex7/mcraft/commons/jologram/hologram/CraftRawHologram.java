package work.srthex7.mcraft.commons.jologram.hologram;

import java.util.Random;

import org.bukkit.Location;

import work.srthex7.mcraft.commons.jologram.instance.JologramInner;

public class CraftRawHologram extends RawHologram {

	int id;
	String text;
	Location location;
	
	Object packet;
	Object destroyPacket;
	
	Location oldLocation;
	String oldText;
	
	Object renamePacket;
	Object teleportPacket;
	
	public CraftRawHologram(String text) {
		this(null, text);
	}
	
	public CraftRawHologram(Location location, String text) {
		this.id = new Random().nextInt();
		this.location = location;
		this.text = text;
		this.destroyPacket = JologramInner.getHologramHandler().destroyHologramPacket(id);
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		this.text = text;
		this.renamePacket = JologramInner.getHologramHandler().renameHologramPacket(id, text);
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public void setLocation(Location location) {
		
		if (this.location == null) {
			this.teleportPacket = JologramInner.getHologramHandler().teleportHologramPacket(id, location);
		} else if (!this.location.equals(location)){
			this.teleportPacket = JologramInner.getHologramHandler().teleportHologramPacket(id, location);	
		}
		
		this.location = location;
	}

	@Override
	public Object getPacket() {
		// TODO Auto-generated method stub
		return packet;
	}

	@Override
	public void buildPacket() {
		if (!location.equals(oldLocation) || !oldText.equals(text)) {
			packet = JologramInner.getHologramHandler().createHologramPacket(id, location, text);
			oldLocation = location;
			oldText = text;
		}
	}

	@Override
	public Object getDestroyPacket() {
		// TODO Auto-generated method stub
		return destroyPacket;
	}

	@Override
	public Object getRenamePacket() {
		// TODO Auto-generated method stub
		return this.renamePacket;
	}

	@Override
	public Object getTeleportPacket() {
		// TODO Auto-generated method stub
		return this.teleportPacket;
	}
}
