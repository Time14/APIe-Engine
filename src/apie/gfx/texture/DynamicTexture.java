package apie.gfx.texture;

public class DynamicTexture extends Texture {
	
	private SpriteSheet spritesheet;
	
	private int currentSpriteX;
	private int currentSpriteY;
	
	/**
	 * 
	 * Constructs an empty dynamic texture. {@link #setSpriteSheet(SpriteSheet)} must be called before usage.
	 * 
	 */
	public DynamicTexture() {}
	
	/**
	 * 
	 * Constructs a new dynamic texture from the specified texture.
	 * 
	 * @param texture - the texture to use
	 */
	public DynamicTexture(Texture texture) {
		this(new SpriteSheet(1, 1, texture.getWidth(), texture.getHeight()).setTexture(texture, 0, 0));
	}
	
	/**
	 * 
	 * Constructs a new dynamic texture from the specified sprite sheet.
	 * 
	 * @param spritesheet - the sprite sheet to use
	 */
	public DynamicTexture(SpriteSheet spritesheet) {
		this.spritesheet = spritesheet;
		currentSpriteX = 0;
		currentSpriteY = 0;
	}
	
	/**
	 * 
	 * Sets the sprite sheet of this dynamic texture.
	 * 
	 * @param spritesheet - the sprite sheet to be set
	 * @return this dynamic texture instance
	 */
	public DynamicTexture setSpriteSheet(SpriteSheet spritesheet) {
		this.spritesheet = spritesheet;
		
		return this;
	}
	
	/**
	 * 
	 * Swaps the current texture with the one from the specified sprite sheet coordinates.
	 * 
	 * @param x - the x coordinate of the sprite to use
	 * @param y - the y coordinate of the sprite to use
	 */
	public void swap(int x, int y) {
		swapX(x);
		swapY(y);
	}
	
	/**
	 * 
	 * Swaps the current texture with the one from the specified sprite sheet index.
	 * 
	 * @param i - the index of the sprite to use
	 */
	public void swap(int i) {
		swapX(i%spritesheet.getSpritesX());
		swapY(Math.floorDiv(i, spritesheet.getSpritesX()));
	}
	
	/**
	 * 
	 * Swaps the current x offset in the sprite sheet.
	 * 
	 * @param x - the new x coordinate in the sprite sheet
	 */
	public void swapX(int x) {
		currentSpriteX = x;
	}
	
	/**
	 * 
	 * Swaps the current y offset in the sprite sheet.
	 * 
	 * @param y - the new y coordinate in the sprite sheet
	 */
	public void swapY(int y) {
		currentSpriteY = y;
	}
	
	/**
	 * 
	 * Increments the x offset in the sprite sheet.
	 * 
	 * @return false if the offset could not increase
	 */
	public boolean incrementX() {
		currentSpriteX++;
		if(currentSpriteX >= spritesheet.getSpritesX()) {
			currentSpriteX--;
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * Increments the y offset in the sprite sheet.
	 * 
	 * @return false if the offset could not increase
	 */
	public boolean incrementY() {
		currentSpriteY++;
		if(currentSpriteY >= spritesheet.getSpritesY()) {
			currentSpriteY--;
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * Binds the current sprite to the texture target index 0 for render usage.
	 * 
	 */
	public Texture bind() {
		spritesheet.bind(currentSpriteX, currentSpriteY, 0);
		return this;
	}
	
	/**
	 * 
	 * Binds the current sprite to the specified texture target index.
	 * 
	 */
	public Texture bind(int target) {
		spritesheet.bind(currentSpriteX, currentSpriteY, target);
		return this;
	}
	
	/**
	 * 
	 * Returns the current texture.
	 * 
	 * @return the current texture
	 */
	public Texture getTexture() {
		return spritesheet.getTexture(currentSpriteX, currentSpriteY);
	}
	
	/**
	 * 
	 * Returns the sprite sheet.
	 * 
	 * @return the sprite sheet
	 */
	SpriteSheet getSpriteSheet() {
		return spritesheet;
	}
	
	/**
	 * 
	 * Returns the current sprite's x offset.
	 * 
	 * @return the current sprite's x offset
	 */
	public int getSpriteX() {
		return currentSpriteX;
	}
	
	/**
	 * 
	 * Returns the current sprite's y offset.
	 * 
	 * @return the current sprite's y offset
	 */
	public int getSpriteY() {
		return currentSpriteY;
	}
	
	/**
	 * 
	 * Clones this dynamic texture. Textures will not regenerate.
	 * 
	 * @return the cloned dynamic texture
	 */
	public DynamicTexture clone() {
		return new DynamicTexture(spritesheet);
	}
}