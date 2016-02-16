package apie.math;

import apie.debug.Debug;

public class VectorXf {
	
	//The values stored in the vector 
	private float[] vals;
	
	/**
	 * 
	 * A generic vector class.
	 * 
	 * @param f - the components to add to this vector
	 */
	public VectorXf(float ... f) {
		vals = f;
	}
	
	/**
	 * 
	 * Returns the vectors distance from origin.
	 * 
	 * @return the distance from origin
	 */
	public float getMagnitude() {
		float s = 0;
		for(float f : vals) {
			s += f * f;
		}
		return (float)Math.sqrt((double)s);
	}
	
	/**
	 * 
	 * Returns the vectors length, but squared
	 * 
	 * @return the length*length 
	 */
	public float getMagnitudeSquared() {
		float m = 0;
		for(float f : vals) {
			m += f * f;
		}
		return (float) m;
	}
	
	
	/**
	 * 
	 * Returns the N-th vector element.
	 * 
	 * @param n - the index to fetch a component from
	 * @return the N-th vector element
	 */
	public float getN(int n) {
		if(n < 0 || vals.length < n) {
			Debug.log("value not in range", 3);
		}
		return vals[n];
	}
	
	
	/**
	 * 
	 * Returns each component of this vector represented as a float array.
	 * 
	 * @return the components of this vector
	 */
	public float[] getData() {
		return vals;
	}
	
	/**
	 * 
	 * Tells you how many dimensions the vector has.
	 * 
	 * @return how many dimensions this vector has
	 */
	public int getDimension() {
		return vals.length;
	}
	
	/**
	 * 
	 * Sets the N-th vector element
	 * 
	 * @param n - the index of the component to set
	 * @param f - the value to set
	 */
	public void setN(int n, float f) {
		if(n < 0 || vals.length < n) {
			Debug.log("value not in range", 3);
		}
		vals[n] = f;
	}
	
	/**
	 * 
	 * Adds two vectors to eachother
	 * 
	 * @param v - the vector you want to add to this vector
	 */
	public VectorXf add(VectorXf v) {
		float smallest = (v.vals.length < this.vals.length) ? v.vals.length : this.vals.length;
		
		for(int i = 0; i < smallest; i++) {
			this.vals[i] += v.vals[i];
		}
		return this;
	}
	
	/**
	 * 
	 * Subtracts a vector from this one.
	 * 
	 * @param v - the vector you want to subtract from this one
	 */
	public VectorXf sub(VectorXf v) {
		float smallest = (v.vals.length < this.vals.length) ? v.vals.length : this.vals.length;
		
		for(int i = 0; i < smallest; i++) {
			this.vals[i] -= v.vals[i];
		}
		return this;
	}
	
	/**
	 * 
	 * Returns the dot product of the the give vector and this one.
	 * 
	 * @param v - the vector you want to dot with this vector
	 */
	public float dot(VectorXf v) {
		float smallest = (v.vals.length < this.vals.length) ? v.vals.length : this.vals.length;
		float s = 0;
		for(int i = 0; i < smallest; i++) {
			s += this.vals[i] * v.vals[i];
		}	
		return s;
	}
	
	/**
	 * 
	 * Scales this vector with the given scalar.
	 * 
	 * @param f - the scalar to scale with
	 * @return this vector instance
	 */
	public VectorXf scale(float f) {
		for(int i = 0; i < vals.length; i++) {
			this.vals[i] *= f;
		}
		return this;
	}
	
	/**
	 * 
	 * Returns a copy of this vector.
	 * 
	 * @return a copy of this vector instance
	 */
	public VectorXf clone() {
		
		switch (this.vals.length) {
			case 1:
				return new VectorXf(this.vals[0]);
			case 2:
				return new VectorXf(this.vals[0], this.vals[1]);
			case 3:
				return new VectorXf(this.vals[0], this.vals[1], this.vals[2]);
			case 4:
				return new VectorXf(this.vals[0], this.vals[1], this.vals[2], this.vals[3]);
			case 5:
				return new VectorXf(this.vals[0], this.vals[1], this.vals[2], this.vals[3], this.vals[4]);
			default:
				break;
		}
		
		return new VectorXf(this.vals);
	}
	
	/**
	 * Returns a vector with unit length
	 * 
	 * @return returns a vector with length 1
	 */
	public VectorXf normalize() {
		return this.scale(1/this.getMagnitude());
	}
	
	
	/**
	 * 
	 * Returns a String representation of this vectors dimensions.
	 * 
	 * @return a String representation of this vectors dimensions
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(float f : this.vals) {
			sb.append(f + "\t");
		}
		return sb.subSequence(0, sb.length() - 1).toString();
	}
}
