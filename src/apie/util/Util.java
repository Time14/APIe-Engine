package apie.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import apie.math.Matrix4f;

public class Util {
	
	/**
	 * 
	 * Converts a matrix to a float buffer.
	 * 
	 * @param data - the matrix to convert
	 * @return the allocated buffer
	 */
	public static final FloatBuffer toFloatBuffer(Matrix4f data) {
		return toFloatBuffer(data.matrix);
	}
	
	/**
	 * 
	 * Converts an integer array to an int buffer.
	 * 
	 * @param data - the array to convert
	 * @return teh allocated buffer
	 */
	public static final IntBuffer toIntBuffer(int... data) {
		IntBuffer buffer = createIntBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * 
	 * Converts a float array to a float buffer.
	 * 
	 * @param data - the array to convert
	 * @return the allocated buffer
	 */
	public static final FloatBuffer toFloatBuffer(float... data) {
		FloatBuffer buffer = createFloatBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * 
	 * Allocates a new float buffer with the specified size.
	 * 
	 * @param size - the amount of floats to hold
	 * @return the allocated buffer
	 */
	public static final FloatBuffer createFloatBuffer(int size) {
		return createByteBuffer(size << 2).asFloatBuffer();
	}
	
	/**
	 * 
	 * Allocates a new int buffer with the specified size.
	 * 
	 * @param size - the amount of integers to hold
	 * @return the allocated buffer
	 */
	public static final IntBuffer createIntBuffer(int size) {
		return createByteBuffer(size << 2).asIntBuffer();
	}
	
	/**
	 * 
	 * Allocates a new double buffer with the specified size.
	 * 
	 * @param size - the amount of doubles to hold
	 * @return the allocated buffer
	 */
	public static final DoubleBuffer createDoubleBuffer(int size) {
		return createByteBuffer(size << 3).asDoubleBuffer();
	}
	
	/**
	 * 
	 * Allocates a new byte buffer with the specified size.
	 * 
	 * @param size - the amount of bytes to hold
	 * @return the allocated buffer
	 */
	public static final ByteBuffer createByteBuffer(int size) {
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
	}
}