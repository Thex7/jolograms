package work.srthex7.mcraft.commons.jologram.hologram;

import org.bukkit.Location;

public abstract class RawHologram {

	public abstract int getID();
	
	public abstract String getText();
	
	public abstract void setText(String text);

	public abstract Location getLocation();
	
	public abstract void setLocation(Location location);
	
	public abstract Object getPacket();
	
	public abstract Object getRenamePacket();
	
	public abstract Object getTeleportPacket();
	
	public abstract void buildPacket();
	
	public abstract Object getDestroyPacket();
	
	@Override
	public String toString() {
		return this.getID() + this.getText() + this.getLocation().toString();
	}
	
}
