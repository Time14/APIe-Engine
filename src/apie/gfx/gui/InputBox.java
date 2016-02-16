package apie.gfx.gui;

import org.lwjgl.glfw.GLFW;

import apie.debug.Debug;
import apie.gfx.QuadRenderer;
import apie.gfx.font.FontRenderer;
import apie.gfx.font.FontType;
import apie.gfx.texture.DynamicTexture;
import apie.gfx.texture.Texture;
import apie.input.InputManager;
import apie.physics.Body;

public class InputBox extends Selectable {
	
	public static final int DEFAULT_CAP = 10;
	
	protected String text = new String();
	protected int textCap = DEFAULT_CAP;
	
	private FontRenderer fontRenderer;
	
	/**
	 * 
	 * Constructs a new input box.
	 * 
	 * @param x - the x coordinate of this input box
	 * @param y - the y coordinate of this input box
	 * @param width - the width of this input box
	 * @param height - the height of this input box
	 * @param texture - the texture to use
	 * @param font - the font type to use
	 * @param size - the size of the font
	 */
	public InputBox(float x, float y, float width, float height, Texture texture, FontType font, float size) {
		setRenderer(new QuadRenderer(x, y, width, height, texture));
		body = new Body(x, y, width, height);
		fontRenderer = new FontRenderer(x, y, text, font, size);
		fontRenderer.setPosition(x - width / 2, y + fontRenderer.getAverageHeight());
	}
	
	@Override
	public void onSelect(boolean selected) {
		
		if(selected)
			InputManager.clearCharQueue();
		
		if(renderer.getTexture() instanceof DynamicTexture) {
			((DynamicTexture) renderer.getTexture()).swap(selected ? 1 : 0);
		}
	}
	
	@Override
	public void onUpdate(float tick) {
		if(isSelected()) {
			if(InputManager.hasNextChar()) {
				if(text.length() < textCap || textCap == 0) {
					text += InputManager.nextChar();
					fontRenderer.reallocateText(text);
				} else {
					InputManager.clearCharQueue();
				}
			}
		}
	}
	
	@Override
	public void onClick(float x, float y) {
		select(!isSelected());
	}
	
	@Override
	public void onKeyTrigger(int key, int mods, int action) {
		if(action == GLFW.GLFW_PRESS) {
			switch(key) {
			case GLFW.GLFW_KEY_ENTER:
				if(isSelected())
					select(false);
				break;
			case GLFW.GLFW_KEY_BACKSPACE:
				if(text.length() < 1)
					break;
				text = text.substring(0, text.length() - 1);
				fontRenderer.reallocateText(text);
				break;
			}
		} else if(action == GLFW.GLFW_REPEAT && key == GLFW.GLFW_KEY_BACKSPACE) {
			if(text.length() < 1)
				return;
			text = text.substring(0, text.length() - 1);
			fontRenderer.reallocateText(text);
		}
	}
	
	@Override
	public void onDraw() {
		renderer.draw();
		fontRenderer.draw();
	}

	@Override
	public void onMouseIn() {}

	@Override
	public void onMouseOut() {}
	
	/**
	 * 
	 * Sets the text of this input box.
	 * 
	 * @param text - the text to display
	 * @return this input box instance
	 */
	public InputBox setText(String text) {
		this.text = text;
		fontRenderer.reallocateText(text);
		
		return this;
	}
	
	/**
	 * 
	 * Returns the text currently displayed in this input box.
	 * 
	 * @return the text of this input box
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * 
	 * Sets the text cap of this input box.
	 * <p>
	 * Set the value to 0 for unlimited size.
	 * 
	 * @param textCap - the maximum amount of characters to contain
	 * @return this input box instance
	 */
	public InputBox setTextCap(int textCap) {
		this.textCap = textCap;
		
		return this;
	}
	
	/**
	 * 
	 * Returns the text cap.
	 * 
	 * @return the maximum amount of characters to contain
	 */
	public int getTextCap() {
		return textCap;
	}
	
	/**
	 * 
	 * Returns the font renderer used by this input box.
	 * 
	 * @return the font renderer of this input box
	 */
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}