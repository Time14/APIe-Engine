package apie.gfx.gui;

import apie.gfx.QuadRenderer;
import apie.gfx.font.FontRenderer;
import apie.gfx.font.FontType;
import apie.gfx.texture.DynamicTexture;
import apie.gfx.texture.Texture;
import apie.physics.Body;

public class Button extends GUIElement {
	
	private FontRenderer fontRenderer;
	
	/**
	 * 
	 * Constructs a new button.
	 * 
	 * @param x - the x coordinate of the button
	 * @param y - the y coordinate of the button
	 * @param width - the width of the button
	 * @param height - the height of the button
	 * @param texture - the texture to use
	 */
	public Button(float x, float y, float width, float height, Texture texture) {
		setRenderer(new QuadRenderer(x, y, width, height, texture));
		body = new Body(transform, width, height);
	}
	
	@Override
	public void onClick(float x, float y) {}
	
	@Override
	public void onKeyTrigger(int key, int mods, int action) {}
	
	@Override
	public void onMouseIn() {
		if(getRenderer().getTexture() instanceof DynamicTexture) {
			((DynamicTexture)getRenderer().getTexture()).swap(1);
		}
	}
	
	@Override
	public void onMouseOut() {
		if(getRenderer().getTexture() instanceof DynamicTexture) {
			((DynamicTexture)getRenderer().getTexture()).swap(0);
		}
	}
	
	@Override
	public void onDraw() {
		renderer.draw();
		if(fontRenderer != null)
			fontRenderer.draw();
	}
	
	@Override
	public void onUpdate(float dt) {}
	
	/**
	 * 
	 * Creates a new font renderer for this button.
	 * 
	 * @param text - the text to render
	 * @param font - the font type to use
	 * @param size - the size of the font
	 * @return this button instance
	 */
	public Button setFont(String text, FontType font, float size) {
		return setFont(new FontRenderer(renderer.getX(), renderer.getY(), text, font, size));
	}
	
	/**
	 * 
	 * Sets the font renderer to use for this button.
	 * 
	 * @param fontRenderer - the font renderer to use
	 * @return this button instance
	 */
	public Button setFont(FontRenderer fontRenderer) {
		this.fontRenderer = fontRenderer;
		
		fontRenderer.setPosition(
				renderer.getX() - fontRenderer.getWidth() / 2,
				renderer.getY() + fontRenderer.getAverageHeight()
		);
		
		return this;
	}
	
	/**
	 * 
	 * Returns the font renderer used by this button. Null if there is none.
	 * 
	 * @return the font renderer of this button
	 */
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}