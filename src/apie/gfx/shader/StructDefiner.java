package apie.gfx.shader;

public interface StructDefiner {
	
	/**
	 * 
	 * Returns the data types of this struct in their corresponding order.
	 * 
	 * @return the data types of this struct in their corresponding order
	 */
	public GLSLType[] getStructure();
	
	/**
	 * 
	 * Returns the names of this struct's components in their corresponding order.
	 * 
	 * @return the names of this struct's components in their corresponding order
	 */
	public String[] getComponentNames();
	
	/**
	 * 
	 * Returns the data components of this struct in their corresponding order.
	 * 
	 * @return the data components of this struct in their corresponding order
	 */
	public float[] getData();
	
	/**
	 * 
	 * Used to compose representations of GLSL structs for shader uniform input.
	 * 
	 * @author SK
	 *
	 */
	public enum GLSLType {
		FLOAT(1), INT(1), DOUBLE(1), VEC2(2), VEC3(3), VEC4(4), MAT4(16);
		
		public final int LENGTH;
		
		private GLSLType(int length) {
			LENGTH = length;
		}
	}
}