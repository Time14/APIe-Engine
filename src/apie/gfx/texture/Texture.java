package apie.gfx.texture;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import apie.library.Library;
import apie.util.Loader;
import apie.util.Util;

public class Texture {
	
	public final Library<Texture> LIB = new Library<>("TextureLibrary");
	
	protected int id;
	protected int width;
	protected int height;
	
	/**
	 * 
	 * Constructs a new empty texture. {@link #loadTexture(String, boolean)} must be called before usage.
	 * 
	 */
	public Texture() {}
	
	/**
	 * 
	 * Constructs a new texture from the specified image path. Must be PNG-format.
	 * 
	 * @param path - the path to the desired PNG-image
	 */
	public Texture(String path) {
		this(path, false);
	}
	
	/**
	 * 
	 * Constructs a new texture from the specified image path. Must be PNG-format.
	 * 
	 * @param path - the path to the desired PNG-image
	 * @param repeat - whether to repeat or clip the S/T coordinates
	 */
	public Texture(String path, boolean repeat) {
		loadTexture(path, repeat);
	}
	
	/**
	 * 
	 * Loads a texture from the desired path. Must be PNG-format.
	 * 
	 * @param path - the path to the desired PNG-image
	 * @param repeat - whether to repeat or clip the S/T coordinates
	 * @return this texture instance
	 */
	public Texture loadTexture(String path, boolean repeat) {
		try {
			BufferedImage image = Loader.loadImage(path);
			
			int width = image.getWidth();
			int height = image.getHeight();
			
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		
			genTexture(pixels, repeat, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * 
	 * Generates a texture from an integer array.
	 * An IllegalArgumentException is thrown if the width and height does not match the array length.
	 * 
	 * @param pixels - the pixel data to use
	 * @param repeat - whether to repeat or clip the S/T coordinates
	 * @param width - the width of the pixel data
	 * @param height - the height of the pixel data
	 * @return this texture instance
	 */
	public Texture genTexture(int[] pixels, boolean repeat, int width, int height) {
		this.width = width;
		this.height = height;
		
		if(pixels.length != width * height)
			throw new IllegalArgumentException("Pixel array not compatible with specified width and height");
		
		id = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		if(repeat) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		}
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, Util.toIntBuffer(pixels));
		
		System.out.println("Generated new texture with ID: " + id);
		
		return this;
	}
	
	/**
	 * 
	 * Binds this texture to the desired texture target index for render usage.
	 * 
	 * @param texTarget - the desired texture target index, must be in range [0-31]
	 * @return this texture instance
	 */
	public Texture bind(int texTarget) {
		
		if(texTarget > 31 || texTarget < 0) {
			throw new IllegalArgumentException("Texture target must be in range 0 - 31");
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + texTarget);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		return this;
	}
	
	/**
	 * 
	 * Binds this texture to texture target index 0 for render usage.
	 * 
	 * @return this texture instance
	 */
	public Texture bind() {
		return bind(0);
	}
	
	/**
	 * 
	 * Sets the specified texture parameters.
	 * 
	 * @param param - the parameter value
	 * @param pnames - the parameters to set. One of: GL_DEPTH_STENCIL_TEXTURE_MODE, GL_TEXTURE_BASE_LEVEL, GL_TEXTURE_COMPARE_FUNC, GL_TEXTURE_COMPARE_MODE, GL_TEXTURE_LOD_BIAS, GL_TEXTURE_MIN_FILTER, GL_TEXTURE_MAG_FILTER, GL_TEXTURE_MIN_LOD, GL_TEXTURE_MAX_LOD, GL_TEXTURE_MAX_LEVEL, GL_TEXTURE_SWIZZLE_R, GL_TEXTURE_SWIZZLE_G, GL_TEXTURE_SWIZZLE_B, GL_TEXTURE_SWIZZLE_A, GL_TEXTURE_WRAP_S, GL_TEXTURE_WRAP_T, or GL_TEXTURE_WRAP_R
	 * @return this texture instance
	 */
	public Texture setParameters(int param, int... pnames) {
		bind();
		for(int i : pnames)
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, i, param);
		return this;
	}
	
	/**
	 * 
	 * Unbinds all currently bound textures.
	 * 
	 */
	public static final void unbindAll() {
		for(int i = 0; i < 32; i++)
			unbind(i);
	}
	
	/**
	 * 
	 * Unbinds the texture currently bound to index 0.
	 * 
	 */
	public static final void unbind() {
		unbind(0);
	}
	
	/**
	 * 
	 * Unbinds the texture currently bound to the specified index.
	 * 
	 * @param target - the index to unbind
	 */
	public static final void unbind(int target) {

		if(target > 31 || target < 0) {
			throw new InvalidParameterException("Target must be in range 0 - 31");
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + target);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * 
	 * Returns the pixel width of this texture.
	 * 
	 * @return the pixel width of this texture
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * Returns the pixel height of this texture.
	 * 
	 * @return the pixel height of this texture
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * 
	 * Returns the ID of this texture.
	 * 
	 * @return the ID of this texture
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * 
	 * Destroys the texture. Releases the allocated system resources for future use.
	 * 
	 */
	public void destroy() {
		GL11.glDeleteTextures(id);
		System.out.println("Destroyed texture with ID: " + id);
	}
	
	//Texture library handling
	
	private static final Library<Texture> textureLibrary = new Library<>("TextureLibrary");
	
	/**
	 * 
	 * Registers a texture to the specified key.
	 * 
	 * @param key - the key to register to
	 * @param texture - the texture to register
	 */
	public static final void register(String key, Texture texture) {
		textureLibrary.put(key, texture);
	}
	
	/**
	 * 
	 * Returns the texture with the specified key.
	 * 
	 * @param key - the key of the texture
	 * @return the texture with the specified key
	 */
	public static final Texture get(String key) {
		return textureLibrary.get(key);
	}
	
	/**
	 * 
	 * Returns the dynamic texture with the specified key, or null if the texture is not dynamic.
	 * 
	 * @param key - the key of the dynamic texture
	 * @param close - whether or not to clone the dynamic texture
	 * @return the dynamic texture with the specified key
	 */
	public static final DynamicTexture getDT(String key, boolean clone) {
		if(get(key) instanceof DynamicTexture)
			return clone ? ((DynamicTexture) get(key)).clone() : (DynamicTexture) get(key);
		
		return null;
	}
	
	public static final Texture DEFAULT_TEXTURE = new Texture("res/texture/default_texture.png");
	
	static {
		register("DEFAULT", DEFAULT_TEXTURE);
	}
}