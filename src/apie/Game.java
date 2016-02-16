package apie;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import apie.gamestate.GameStateManager;
import apie.input.InputManager;
import apie.util.Time;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

public class Game {
	
	//v-sync?
	public static final boolean VSYNC = false;
	
	//Decorated?
	public static final boolean DECORATED = true;
	
	private static boolean fullscreen = false;
	
	//Controls the main loop
	private volatile boolean running = false;
	
	//Window properties
	private String title;
	private int width, height;
	
	//The window handle
	private long window;
	
	
	private GLFWErrorCallback errorCallback;
	
	/**
	 * 
	 * Starts the game with the specified window properties.
	 * This process includes initializing the GLFW library as well as the GameStateManager.
	 * 
	 * @param title - the title of the window
	 * @param width - the width of the window
	 * @param height - the height of the window
	 */
	public final void run(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		init();
		loop();
		
		glfwDestroyWindow(window);
		glfwTerminate();
		errorCallback.release();
	}
	
	/**
	 * 
	 * Sets up the GLFW necessities.
	 * 
	 */
	private final void init() {
		
		//Prints the LWJGL version
		System.out.println("LWJGL " + Sys.getVersion());
		
		//Sets the error callback
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
		
		//Initializes GLFW
		if(glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Failed to initialize GLFW");
		
		//Loads default window properties
		glfwDefaultWindowHints();
		
		//Disable window visibility to hide loading phase
		glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
		
		//Disable resizability
		glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);
		
		//Decorate or undecorated
		glfwWindowHint(GLFW_DECORATED, DECORATED ? GL11.GL_TRUE : GL11.GL_FALSE);
		
		//Creates the window and assigns the handle
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		
		//Fetch monitor properties
		ByteBuffer vidmode  = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		//Center window
		glfwSetWindowPos(
				window,
				(GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2
		);
		
		//Make the OpenGL context current for this window
		glfwMakeContextCurrent(window);
		
		//Enables vsync depending on VSYNC boolean
		glfwSwapInterval(VSYNC ? 1 : 0);
		
		//Displays window
		glfwShowWindow(window);
		
		InputManager.loadInputs();		
	}
	
	/**
	 * 
	 * Creates the OpenGL context and runs the main loop.
	 * 
	 */
	private void loop() {
		
		//Creates the context
		GLContext.createFromCurrent();
		
		//Prints the OpenGL version
		System.out.println("OpenGL " + GL11.glGetString(GL11.GL_VERSION));
		
		//Sets the default clear color
		GL11.glClearColor(0, 0, 0, 1);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GameStateManager.init(this, window);
		GameStateManager.enterState("Main");
		
		
		//The main loop
		running = true;
		while(glfwWindowShouldClose(window) == GL11.GL_FALSE && running) {
			Time.update();
			GameStateManager.update(Time.getDelta());
		}
		
		InputManager.saveInputs();
	}
	
	/**
	 * 
	 * Returns this games window.
	 * 
	 * @return the window of this game
	 */
	public long getWindow() {
		return window;
	}
	
	/**
	 * 
	 * Enables / disables fullscreen mode for this game.
	 * <p>
	 * Must be called before initiation.
	 * 
	 * @param fullscreen - true if you wish to use fullscreen mode
	 */
	public void setFullscreen(boolean fullscreen) {
		Game.fullscreen = fullscreen;
	}
	
	/**
	 * 
	 * Returns the window width.
	 * 
	 * @return the window width
	 */
	public int getWindowWidth() {
		return width;
	}
	
	/**
	 * 
	 * Returns the window height.
	 * 
	 * @return the window height
	 */
	public int getWindowHeight() {
		return height;
	}
	
	/**
	 * 
	 * Stops the main loop after this frame.
	 * 
	 */
	public void stop() {
		running = false;
	}
}