package apie.math;

/**
 * 
 * This is a class representation of a 4x4 matrix specified in column-major order.
 * 
 * @author Alfred Sporre
 *
 */

public class Matrix4f {
	
	public static final int SIZE = Float.SIZE * 8 * 16;
	
	public float[] matrix = new float[16];
	
	
	/**
	 * 
	 * Sets all the values within this matrix to 0
	 * 
	 * @return this matrix instance
	 */
	public Matrix4f clear() {
		for(int i = 0; i < 16; i++) {
			matrix[i] = 0;
		}
		
		return this;
	}
	
	/**
	 * 
	 * Loads the identity matrix.
	 * 
	 * @return this matrix instance
	 */
	public Matrix4f loadIdentity() {
		clear();
		
		matrix[0 + 0 * 4] = 1;
		matrix[1 + 1 * 4] = 1;
		matrix[2 + 2 * 4] = 1;
		matrix[3 + 3 * 4] = 1;
		
		return this;
	}
	
	/**
	 * 
	 * Multiplies this matrix with the specified right one.
	 * 
	 * @param right - the right matrix to multiply with
	 * @return this matrix instance
	 */
	public Matrix4f multiply(Matrix4f right) {
		float[] result = new float[16];
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 4; k++) {
					result[i*4+j] += matrix[k+i*4] * right.toFloatArray()[j+k*4];
				}
			}
		}
		
		matrix = result;
		return this;
	}
	
	/**
	 * 
	 * Translates this matrix with the specified vector.
	 * 
	 * @param vec2 - the vector to translate with
	 * @return this matrix instance
	 */
	public Matrix4f translate(Vector2f vec2) {
		return translate(new Vector3f(vec2.getX(), vec2.getY(), 0));
	}
	
	/**
	 * 
	 * Translates this matrix with the specified vector.
	 * 
	 * @param vec3 - the vector to translate with
	 * @return this matrix instance
	 */
	public Matrix4f translate(Vector3f vec3) {
		matrix[0 + 3 * 4] += vec3.getX();
		matrix[1 + 3 * 4] += vec3.getY();
		matrix[2 + 3 * 4] += vec3.getZ();
		
		return this;
	}
	
	/**
	 * 
	 * Returns the value from the specified coordinates.
	 * 
	 * @param column - the column to get the value from
	 * @param row - the row to get the value from
	 * @return the value from the specified coordinates
	 */
	public float get(int column, int row) {
		return matrix[row + column * 4];
	}
	
	/**
	 * 
	 * Rotates this matrix on the z-axis.
	 * 
	 * @param angle - the angle specified in degrees
	 * @return this matrix instance
	 */
	public Matrix4f rotate(float angle) {
		
		float r = (float)Math.toRadians(angle);
		float sin = (float)Math.sin(r);
		float cos = (float)Math.cos(r);
		
		matrix[0 + 0 * 4] = cos;
		matrix[0 + 1 * 4] = -sin;
		matrix[1 + 0 * 4] = sin;
		matrix[1 + 1 * 4] = cos;
		
		return this;
	}
	
	/**
	 * 
	 * Scales this matrix with the specified vector.
	 * 
	 * @param scale - the vector to scale with
	 * @return this matrix instance
	 */
	public Matrix4f scale(Vector2f scale) {
		matrix[0 + 0 * 4] *= scale.getX();
		matrix[1 + 1 * 4] *= scale.getY();
		return this;
	}
	
	/**
	 * 
	 * Scales this matrix with the specified vector
	 * 
	 * @param scale - the vector to scale with
	 * @return this matrix instance
	 */
	public Matrix4f scale(Vector3f scale) {
		matrix[0 + 0 * 4] *= scale.getX();
		matrix[1 + 1 * 4] *= scale.getY();
		matrix[2 + 2 * 4] *= scale.getZ();
		return this;
	}
	
	/**
	 * 
	 * Sets all values of this matrix to the specified array. The length of the array must be 4*4.
	 * 
	 * @param values - the array to use for this matrix
	 * @return this matrix instance
	 */
	public Matrix4f init(float[] values) {
		matrix = values;
		
		return this;
	}
	
	/**
	 * 
	 * Sets a value to the specified matrix coordinates.
	 * 
	 * @param column - the column to set the value on
	 * @param row - the row to set the value on
	 * @param value - the value to be set
	 * @return this matrix instance
	 */
	public Matrix4f set(int column, int row, float value) {
		matrix[column*4+row] = value;
		
		return this;
	}
	
	/**
	 * 
	 * Returns a float array with this matrix' values.
	 * 
	 * @return a float array with this matrix' values
	 */
	public float[] toFloatArray() {
		return matrix;
	}
	
	/**
	 * 
	 * Returns a representative string of this matrix.
	 * 
	 * @return a representative string ot this matrix
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				str.append((matrix[i+j*4]));
				if(j < 3)
					str.append("\t");
			}
			str.append("\n");
		}
		
		return str.toString();
	}
	
	/**
	 * 
	 * Returns the identity matrix.
	 * 
	 * @return the identity matrix
	 */
	public static final Matrix4f IDENTITY() {
		return new Matrix4f().loadIdentity();
	}
}