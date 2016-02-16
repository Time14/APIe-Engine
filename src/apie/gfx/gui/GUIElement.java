package apie.gfx.gui;

import apie.entity.Entity;

public abstract class GUIElement extends Entity {
	
	protected boolean mouseOver = false;
	
	protected GUI gui;
	
	//On click
	
	private GUIEvent clickEvent;
	
	/**
	 * 
	 * Performs a click event on this GUI element.
	 * 
	 * @param x - the x coordinate of the click event
	 * @param y - the y coordinate of the click event
	 */
	public final void click(float x, float y) {
		onClick(x, y);
		if(clickEvent != null)
			clickEvent.fire();
	}
	
	/**
	 * 
	 * Called when a click event is simulated.
	 * 
	 * @param x - the x coordinate of the click event
	 * @param y - the y coordinate of the click event
	 */
	protected abstract void onClick(float x, float y);
	
	/**
	 * 
	 * Sets an action to perform when a click event is fired.
	 * 
	 * @param e - the action to perform
	 * @return this GUI element instance
	 */
	public final GUIElement setClickEvent(GUIEvent e) {
		clickEvent = e;
		
		return this;
	}
	
	//Key press
	
	/**
	 * 
	 * Performs a key trigger event on this GUI element.
	 * 
	 * @param key - the triggered key
	 * @param mods - the triggered key's mods
	 * @param action - the action that triggered the key
	 */
	protected final void triggerKey(int key, int mods, int action) {
		onKeyTrigger(key, mods, action);
	}
	
	/**
	 * 
	 * Called when a key trigger event is simulated.
	 * 
	 * @param key - the triggered key
	 * @param mods - the triggered key's mods
	 * @param action - the action that triggered the key
	 */
	public abstract void onKeyTrigger(int key, int mods, int action);
	
	//On mouse in
	
	private GUIEvent mouseInEvent;
	
	/**
	 * 
	 * Performs a mouse in event on this GUI element.
	 * 
	 */
	public final void mouseIn() {
		onMouseIn();
		if(mouseInEvent != null)
			mouseInEvent.fire();
	}
	
	/**
	 * 
	 * Called when a mouse in event is simulated.
	 * 
	 */
	public abstract void onMouseIn();
	
	/**
	 * 
	 * Sets an action to perform when a mouse in event is fired.
	 * 
	 * @param e - the action to perform
	 * @return this GUI elements instance
	 */
	public final GUIElement setMouseInEvent(GUIEvent e) {
		mouseInEvent = e;
		
		return this;
	}
	
	//On mouse out
	
	private GUIEvent mouseOutEvent;
	
	/**
	 * 
	 * Performs a mouse out event on this GUI element.
	 * 
	 */
	public final void mouseOut() {
		onMouseOut();
		if(mouseOutEvent != null)
			mouseOutEvent.fire();
	}
	
	/**
	 * 
	 * Called when a mouse out event is simulated.
	 * 
	 */
	public abstract void onMouseOut();
	
	/**
	 * 
	 * Sets an action to perform when a mouse out event is fired.
	 * 
	 * @param e - the action to perform
	 * @return this GUI element instance
	 */
	public final GUIElement setMouseOutEvent(GUIEvent e) {
		mouseOutEvent = e;
		
		return this;
	}
	
	//On update
	
	private GUIEvent updateEvent;
	
	/**
	 * 
	 * Updates this GUI element.
	 * 
	 * @param dt - the time passed since the previous frame
	 */
	public final void update(float dt) {
		onUpdate(dt);
		if(updateEvent != null)
			updateEvent.fire();
	}
	
	/**
	 * 
	 * Called when this GUI element is updated.
	 * 
	 * @param dt - the time passed since the previous frame
	 */
	public abstract void onUpdate(float dt);
	
	/**
	 * 
	 * Sets an action to perform when this GUI element is updated.
	 * 
	 * @param e - the action to perform
	 * @return this GUI element instance
	 */
	public final GUIElement setUpdateEvent(GUIEvent e) {
		updateEvent = e;
		
		return this;
	}
	
	//On draw
	
	private GUIEvent drawEvent;
	
	/**
	 * 
	 * Draws this entity.
	 * 
	 */
	public final void draw() {
		onDraw();
		if(drawEvent != null)
			drawEvent.fire();
	}
	
	/**
	 * 
	 * Called when this GUI element is drawn.
	 * 
	 */
	public abstract void onDraw();
	
	/**
	 * 
	 * Sets an action to perform when this GUI element is drawn.
	 * 
	 * @param e - the action to perform
	 * @return this GUI element instance
	 */
	public final GUIElement setDrawEvent(GUIEvent e) {
		drawEvent = e;
		
		return this;
	}
	
	//Is mouse over
	
	/**
	 * 
	 * Returns whether or not the mouse cursor is currently above this GUI element.
	 * 
	 * @return true if the cursor is currently above this GUI element.
	 */
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	//set GUI
	
	/**
	 * 
	 * Sets the parent GUI for this element.
	 * 
	 * @param gui - the parent GUI of this element
	 */
	public void setGUI(GUI gui) {
		this.gui = gui;
	}
	
	/**
	 * 
	 * Currently not used.
	 * 
	 */
	protected void destroy() {
		
	}
}