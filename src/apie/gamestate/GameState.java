package apie.gamestate;

public abstract class GameState {
	
	public final String NAME;
	
	/**
	 * 
	 * Initializes this GameState with a name.
	 * 
	 * @param name - the name of this GameState
	 */
	public GameState(String name) {
		NAME = name;
	}
	
	/**
	 * 
	 * Called each time this GameState is entered.
	 * 
	 */
	public abstract void init();
	
	/**
	 * 
	 * Called for each keyboard event passed since the last GameStateManager update.
	 * 
	 * @param window - the window that received the event
	 * @param key - the keyboard key that was pressed or released
	 * @param scancode - the system-specific scancode of the key
	 * @param action - the key action. One of: GLFW.GLFW_PRESS, GLFW.GLFW_RELEASE, GLFW.GLFW_REPEAT
	 * @param mods - bitfield describing which modifiers keys were held down
	 */
	public abstract void onKeyboard(long window, int key, int scancode, int action, int mods);
	
	/**
	 * 
	 * Called for each mouse event passed since the last GameStateManager update. 
	 * 
	 * @param window - the window that received the event
	 * @param button - the mouse button that was pressed or released
	 * @param action - the button action. One of: GLFW.GLFW_PRESS, GLFW.GLFW_RELEASE, GLFW.GLFW_REPEAT
	 * @param mods - bitfield describing which modifiers keys were held down
	 */
	public abstract void onMouse(long window, int button, int action, int mods);
	
	/**
	 * 
	 * Called each update from the GameStateManager.
	 * 
	 * @param dt - the delta time passed since the last update
	 */
	public abstract void update(float dt);
	
	/**
	 * 
	 * Called each GameStateManager update directly after the {@link #update(float) update()} method.
	 * 
	 */
	public abstract void draw();
	
	/**
	 * 
	 * Called when the GameStateManager enters another GameState and thus just before this GameState is exited.
	 * 
	 */
	public abstract void exit();
}