package apie.gfx.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;

import apie.library.Library;
import apie.util.Loader;

public class SpriteSheet {

	private Texture[][] textures;
	
	private int totalWidth;
	private int totalHeight;
	
	private int spritesX;
	private int spritesY;
	
	private int frameWidth;
	private int frameHeight;
	
	/**
	 * 
	 * Constructs a new sprite sheet with the specified dimensions.
	 * 
	 * @param spritesY - the total amount of sprites on the x-axis
	 * @param spritesX - the total amount of sprites on the y-axis
	 * @param frameWidth - the width of each sprite
	 * @param frameHeight - the height of each sprite
	 */
	public SpriteSheet(int spritesX, int spritesY, int frameWidth, int frameHeight) {
		this.spritesX = spritesX;
		this.spritesY = spritesY;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		textures = new Texture[spritesY][spritesX];
	}
	
	/**
	 * 
	 * Generates textures for each sprite.
	 * 
	 * @param path - the path of the sprite sheet
	 * @return this sprite sheet instance
	 */
	public SpriteSheet loadTexture(String path) {
		
		try {
			BufferedImage image = Loader.loadImage(path);
			totalWidth = image.getWidth();
			totalHeight = image.getHeight();
			
			int[] raw = new int[totalWidth * totalHeight];
			image.getRGB(0, 0, totalWidth, totalHeight, raw, 0, totalWidth);
			
			int[][] pixels = new int[totalHeight][totalWidth];
			
			for(int i = 0; i < totalHeight; i++) {
				for(int j = 0; j < totalWidth; j++) {
					pixels[i][j] = raw[j + i * totalWidth];
				}
			}
			
			for(int i = 0; i < spritesY; i++) {
				for(int j = 0; j < spritesX; j++) {
					int[] data = new int[frameWidth * frameHeight];
					for(int k = 0; k < frameHeight; k++) {
						for(int l = 0; l < frameWidth; l++) {
							data[l + k * frameWidth] = pixels[k + i * frameHeight][l + j * frameWidth];
						}
					}
					textures[i][j] = new Texture().genTexture(data, false, frameWidth, frameHeight);
				}
			}
			
		} catch (IOException e) {
			System.err.println("Failed to load texture at \""+path+"\"!");
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * 
	 * Binds the specified sprite for render usage.
	 * 
	 * @param tileX - the x coordinate of the sprite to render
	 * @param tileY - the y coordinate of the sprite to render
	 * @param target - the desired texture target index
	 * @return this sprite sheet instance
	 */
	public SpriteSheet bind(int tileX, int tileY, int target) {
		textures[tileY][tileX].bind(target);
		
		return this;
	}
	
	/**
	 * 
	 * Sets the sprite on the specified position.
	 * 
	 * @param texture - the texture to set
	 * @param x - the x coordinate of the sprite to set
	 * @param y - the y coordinate of the sprite to set
	 * @return this sprite sheet instance
	 */
	public SpriteSheet setTexture(Texture texture, int x, int y) {
		textures[y][x] = texture;
		
		return this;
	}
	
	/**
	 * 
	 * Returns the sprite on the specified coordinates.
	 * 
	 * @param tileX - the x coordinate of the sprite
	 * @param tileY - the y coordinate of the sprite
	 * @return the sprite on the specified coordinates
	 */
	public Texture getTexture(int tileX, int tileY) {
		return textures[tileY][tileX];
	}
	
	/**
	 * 
	 * Returns the amount of sprites on the x-axis of this sprite sheet.
	 * 
	 * @return the amount of sprites on the x-axis of this sprite sheet
	 */
	public int getSpritesX() {
		return spritesX;
	}
	
	/**
	 * 
	 * Returns the amount of sprites on the y-axis of this sprite sheet.
	 * 
	 * @return the amount of sprites on the y-axis of this sprite sheet
	 */
	public int getSpritesY() {
		return spritesY;
	}
	
	//SpriteSheet library handling
	
	private static final Library<SpriteSheet> spriteSheetLibrary = new Library<>("SpriteSheetLibrary");
	
	/**
	 * 
	 * Registers a sprite sheet to the specified key.
	 * 
	 * @param key - the key to register to
	 * @param spriteSheet - the sprite sheet to register
	 */
	public static final void register(String key, SpriteSheet spriteSheet) {
		spriteSheetLibrary.put(key, spriteSheet);
	}
	
	/**
	 * 
	 * Returns the sprite sheet with the specified key.
	 * 
	 * @param key - the key of the sprite sheet
	 * @return the sprite sheet with the specified key
	 */
	public static final SpriteSheet get(String key) {
		return spriteSheetLibrary.get(key);
	}
	
}