package apie.math;

public class Vector3f extends VectorXf {
	
	/**
	 * 
	 * Constructs a new Vector3f.
	 * 
	 * @param x - the initial x component of this vector
	 * @param y - the initial y component of this vector
	 * @param z - the initial z component of this vector
	 */
	public Vector3f(float x, float y, float z) {
		super(x, y, z);
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
	 * Gets the z component of this vector.
	 * 
	 * @return the z component of this vector
	 */
	public float getZ() {
		return this.getN(2);
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
	
	/**
	 * 
	 * Sets the z component of this vector.
	 * 
	 * @param z - the new z value to be set
	 */
	public void setZ(float z) {
		setN(2, z);
	}
}