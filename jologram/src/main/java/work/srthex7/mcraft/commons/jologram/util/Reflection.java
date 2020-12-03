package work.srthex7.mcraft.commons.jologram.util;

import java.lang.reflect.Field;

public class Reflection {

	private static Field setAccessible(String field, Object object) throws NoSuchFieldException, SecurityException {
		Field result = object.getClass().getDeclaredField(field);
		result.setAccessible(true);
		return result;
	}
	
	public static void setField(String field, Object object, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Reflection.setAccessible(field, object).set(object, value);
	}
	
	public static Object getValue(String field, Object object) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		return Reflection.setAccessible(field, object).get(object);
	}
}
