package work.srthex7.mcraft.commons.jologram.util;

import org.bukkit.Bukkit;

public class VersionUtil {

	private static String version;
	
	public static String getServerVersion() {
		if (version == null) {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		}
		return version;
	}
	
}
