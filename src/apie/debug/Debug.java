package apie.debug;

import org.lwjgl.opengl.GL11;

import apie.math.Vector2f;
import apie.physics.Body;
import apie.physics.Collider;

public class Debug {
	
	/**
	 * 
	 * Draws a rectangle to the screen with give proportions.
	 * Should only be used for debugging.
	 * 
	 * @param x - the x coordinate of the center
	 * @param y - the y coordinate of the center
	 * @param w - the width of the rectangle
	 * @param h - the height of the rectangle
	 */
	public static void drawShape(float x, float y, Vector2f[] p) {
		
//		Log("Drawing a rectangle sub-optimally", 2);
		
		//Set color
		GL11.glColor3f(1, 1, 1);
		
		//Begin
		for(int i = 0; i < p.length; i++) {
			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + p[i].getX(),y + p[i].getY());
			GL11.glVertex2f(x + p[(i+1)%p.length].getX(),y + p[(i+1)%p.length].getY());
			GL11.glEnd();	
		}
	}
	
	/**
	 * 
	 * Draw a rectangle with the give Body's dimensions.
	 * 
	 * @param b - the body to draw
	 */
	public static void drawRect(Body b) {
		Vector2f[] p = new Vector2f[b.getCollider().getPoints().length];
		for (int i = 0; i < p.length; i++) {
			p[i] = new Vector2f(b.getCollider().getPoints()[i].getX() * b.getDim().getX(), b.getCollider().getPoints()[i].getY()* b.getDim().getY());
		}
		
		drawShape(b.getPos().getX(), b.getPos().getY(), p);
	}
	
	/**
	 * 
	 * Prints the message and where it was called.
	 * 
	 * @param message - the message you wish to print.
	 * @param i  - the depth of the caller function.
	 */
	public static void log(Object message, int i) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		System.out.println("[" + ste[i].getFileName() + " @ " + ste[i].getLineNumber() + "] " + message);
	}
	
	/**
	 * 
	 * Prints the message and where it was called.
	 * 
	 * @param message - the message you wish to print.
	 */
	public static void log(Object message) {
		int i = 2;
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		System.out.println("[" + ste[i].getFileName() + " @ " + ste[i].getLineNumber() + "] " + message);
	}
	
	/**
	 * 
	 * Prints the messages and where it was called.
	 * 
	 * @param messages - the messages you wish to print
	 */
	public static void log(Object... messages) {
		
		StringBuilder sb = new StringBuilder();
		
		for(Object o : messages) {
			sb.append(o).append("\t");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		Debug.log(sb.toString());
	}
}
