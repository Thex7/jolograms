package work.srthex7.mcraft.commons.jologram.util;

import java.util.Random;

import org.bukkit.Location;

public class HologramUtil {

	public static int rndInt (int min, int max) {
		Random r = new Random();
		int i = r.nextInt((max-min) + 1) + min;
		return i;
	}
	
	public static int generateRandomInteger() {
		Random r = new Random();
		int i = r.nextInt(Integer.MAX_VALUE);
		return i;
	}
	
	public static double aproxDistance(Location location1, Location location2) {
		return Math.abs(location1.getX() - location2.getX()) /*+ Math.abs(location1.getY() - location2.getY())*/ + Math.abs(location1.getZ() - location2.getZ());
	}
	
}
