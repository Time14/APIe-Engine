package apie.gfx.gui;

import java.util.ArrayList;

import apie.Game;
import apie.debug.Debug;
import apie.math.Vector2f;

public class GUI {
	
	private ArrayList<GUIElement> guiElements;
	
	private Game game;
	
	/**
	 * 
	 * Creates a new empty graphical user interface.
	 * 
	 */
	public GUI() {
		guiElements = new ArrayList<>();
	}
	
	/**
	 * 
	 * Adds GUI elements to this gui.
	 * 
	 * @param elements - the gui elements to add
	 * @return this GUI instance
	 */
	public GUI addElements(GUIElement... elements) {
		for(GUIElement element : elements) {
			guiElements.add(element);
			element.setGUI(this);
		}
		
		return this;
	}
	
	/**
	 * 
	 * Performs a click event at the specified coordinates.
	 * 
	 * @param coords - the coordinates to click at
	 * @return this GUI instance
	 */
	public GUI click(Vector2f coords) {
		for(GUIElement e : guiElements) {
			if(e.contains(coords)) {
				e.click(coords.getX(), coords.getY());
			}
		}
		
		return this;
	}
	
	/**
	 * 
	 * Performs a key trigger event.
	 * 
	 * @param key - the triggered key
	 * @param mods - the triggered key's mods
	 * @param action - the action that triggered the key
	 * @return this GUI instance
	 */
	public GUI triggerKey(int key, int mods, int action) {
		for(GUIElement e : guiElements)
			e.triggerKey(key, mods, action);
		
		return this;
	}
	
	/**
	 * 
	 * Performs a click event at the specified coordinates.
	 * 
	 * @param x - the x coordinate to click at
	 * @param y - the y coorinate to click at
	 * @return this GUI instance
	 */
	public GUI click(float x, float y) {
		return click(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Draws all GUI elements.
	 * 
	 * @return this GUI instance
	 */
	public GUI draw() {
		
		for(GUIElement e : guiElements)
			e.draw();
		
		return this;
	}
	
	/**
	 * 
	 * Updates all GUI elements in this GUI.
	 * 
	 * @param tick - the time passed since the previous frame
	 * @param mousePos - the current coordinates of the mouse cursor
	 * @return this GUI instance
	 */
	public GUI update(float tick, Vector2f mousePos) {
		for(GUIElement e : guiElements) {
			if(e.contains(mousePos) && !e.mouseOver) {
				e.mouseIn();
				e.mouseOver = true;
			} else if(!e.contains(mousePos) && e.mouseOver) {
				e.mouseOut();
				e.mouseOver = false;
			}
				
			e.update(tick);
		}
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GUIElement> ArrayList<T> get(T type) {
		return (ArrayList<T>) get(type.getClass());
	}
	
	/**
	 * 
	 * Returns all currently contained GUI elements of a type as a list.
	 * 
	 * @param type - the class of the GUI elements to fetch
	 * @return the retrieves list
	 */
	@SuppressWarnings("unchecked")
	public <T extends GUIElement> ArrayList<T> get(Class<T> type) {
		
		ArrayList<T> result = new ArrayList<>();
		
		for(GUIElement e : guiElements) {
			if(e.getClass().getSuperclass() == type)
				result.add(((T)e));
		}
		
		return result;
	}
	
}