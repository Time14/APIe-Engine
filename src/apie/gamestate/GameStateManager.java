package apie.gamestate;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCharModsCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import apie.Game;
import apie.input.InputManager;
import apie.library.Library;

public final class GameStateManager {
	
	private static Library<GameState> gameStateLibrary = new Library<>("GameStateLibrary");
	
	private static GLFWKeyCallback keyCallback;
	private static GLFWMouseButtonCallback mouseCallback;
	private static GLFWCharModsCallback charCallback;
	
	private static Game game;
	
	private static long window;
	
	private static GameState currentState;
	
	/**
	 * 
	 * Called once by the Game class to set essentials values for core functionality.
	 * 
	 * @param game - the current Game instance of this application
	 * @param window - the main window handle
	 */
	public static void init(Game game, long window) {
		GameStateManager.game = game;
		GameStateManager.window = window;
		GLFW.glfwSetKeyCallback(window, keyCallback);
		GLFW.glfwSetMouseButtonCallback(window, mouseCallback);
		GLFW.glfwSetCharModsCallback(window, charCallback);
	}
	
	/**
	 * 
	 * Registers a new GameState to this GameStateManager.
	 * 
	 * @param gs - the GameState to register
	 */
	public static void registerState(GameState gs) {
		gameStateLibrary.put(gs.NAME, gs);
	}
	
	/**
	 * 
	 * Exits the current GameState and initiates a new one with the corresponding key specified.
	 * 
	 * @param key - the key to fetch the GameState from
	 */
	public static void enterState(String key) {
		enterState(gameStateLibrary.get(key));
	}
	
	
	/**
	 * 
	 * Exits the current GameState and initiates the one specified.
	 * 
	 * @param gs - the new GameState
	 */
	public static void enterState(GameState gs) {
		if(currentState != null)
			currentState.exit();
		
		currentState = gs;
		
		currentState.init();
	}
	
	/**
	 * 
	 * Updates the currently activated GameState.
	 * 
	 * @param dt - the delta time
	 */
	public static void update(float dt) {
		InputManager.update();
		GLFW.glfwPollEvents();
		currentState.update(dt);
		currentState.draw();
		GLFW.glfwSwapBuffers(window);
	}
	
	public static final GameState getGameState(String key) {
		return gameStateLibrary.get(key);
	}
	
	static {
		
		charCallback = new GLFWCharModsCallback() {
			@Override
			public void invoke(long window, int codepoint, int mods) {
				InputManager.queueChar(codepoint);
			}
		};
		
		keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				InputManager.updateInput(key, scancode, mods, action);
				currentState.onKeyboard(window, key, scancode, action, mods);
			}
		};
		
		mouseCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				currentState.onMouse(window, button, action, mods);
			}
		};
	}
	
	public static final Game getGame() {
		return game;
	}
}