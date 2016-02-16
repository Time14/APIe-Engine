package apie.util;

import org.lwjgl.glfw.GLFW;

public final class Time {
	
	private static float prevTime = getTime();
	
	private static float deltaTime;
	
	private static int fps;
	private static int fpsCount;
	private static float deltaStack;
	
	/**
	 * 
	 * Called internally by the Game class' main loop to update delta time
	 * as well as the FPS count.
	 * 
	 */
	public static final void update() {
		float currentTime = getTime();
		deltaTime = currentTime - prevTime;
		prevTime = currentTime;
		
		deltaStack += deltaTime;
		fpsCount++;
		if(deltaStack > 1) {
			fps = --fpsCount;
			deltaStack = 0;
			fpsCount = 0;
		}
	}
	
	/**
	 * 
	 * Returns how many seconds have passed since the program started.
	 * 
	 * @return the amount of seconds passed since program start.
	 */
	public static final float getTime() {
		return (float)GLFW.glfwGetTime();
	}
	
	/**
	 * 
	 * Returns the amount of seconds passed since the previous frame.
	 * 
	 * @return the amount of seconds passed since the previous frame
	 */
	public static final float getDelta() {
		return deltaTime;
	}
	
	/**
	 * 
	 * Returns have many frames there were the previous second.
	 * 
	 * @return how many frames there were the previous second.
	 */
	public static final int getFPS() {
		return fps;
	}
}