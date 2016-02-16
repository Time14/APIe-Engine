package apie.math;

public class Vector2f extends VectorXf {
	
	/**
	 * 
	 * Constructs a new Vector2f with components(0,0).
	 * 
	 */
	public Vector2f() {
		this(0, 0);
	}
	
	/**
	 * 
	 * Constructs a new Vector2f.
	 * 
	 * @param x - the initial x component of this vector
	 * @param y - the initial y component of this vector
	 */
	public Vector2f(float x, float y) {
		super(x, y);
	}
	
	/**
	 * 
	 * Gets the x component of this vector.
	 * 
	 * @return the x component of this vector
	 */
	public float getX() {
		return this.getN(0);
	}
	
	/**
	 * 
	 * Gets the y component of this vector.
	 * 
	 * @return the y component of this vector
	 */
	public float getY() {
		return this.getN(1);
	}
	
	/**
	 * 
	 * Sets the x component of this vector.
	 * 
	 * @param x - the new x value to be set
	 */
	public void setX(float x) {
		setN(0, x);
	}
	
	/**
	 * 
	 * Sets the y component of this vector.
	 * 
	 * @param y - the new y value to be set
	 */
	public void setY(float y) {
		setN(1, y);
	}
}