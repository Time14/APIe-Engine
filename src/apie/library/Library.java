package apie.library;

import java.util.HashMap;

public class Library <T> {
	
	public final String NAME;
	
	private final HashMap<String, T> ELEMENTS;
	
	/**
	 * 
	 * Creates a new empty library for data storage and labeling.
	 * 
	 */
	public Library(String name) {
		NAME = name;
		ELEMENTS = new HashMap<>();
	}
	
	/**
	 * 
	 * Puts a value into this library with the specified key.
	 * 
	 * @param key - the key to the specified value
	 * @param value - the value to be stored
	 * @return this library instance
	 */
	public final Library<T> put(String key, T value) {
		ELEMENTS.put(key, value);
		return this;
	}
	
	
	/**
	 * 
	 * Retrieves a value with the specified key.
	 * 
	 * If there is no value with the given key, this method will return null.
	 * 
	 * @param key - the key to fetch the value from
	 * @return the value of the specified key
	 */
	public final T get(String key) {
		return ELEMENTS.get(key);
	}
	
	/**
	 * 
	 * Clears the content of this library.
	 * 
	 */
	public final void destroy() {
		ELEMENTS.clear();
	}
}