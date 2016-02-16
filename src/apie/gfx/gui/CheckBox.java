package apie.gfx.gui;

import apie.gfx.QuadRenderer;
import apie.gfx.texture.DynamicTexture;
import apie.physics.Body;

public class CheckBox extends Selectable {
	
	/**
	 * 
	 * Constructs a new CheckBox with the specified properties.
	 * 
	 * @param x - the x coordinate of this CheckBox
	 * @param y - the y coordinate of this CheckBox
	 * @param width - the width of this CheckBox
	 * @param height - the height of this CheckBox
	 * @param texture - the texture to use
	 */
	public CheckBox(float x, float y, float width, float height, DynamicTexture texture) {
		setRenderer(new QuadRenderer(x, y, width, height, texture));
		body = new Body(transform, width, height);
	}
	
	@Override
	public void onSelect(boolean selected) {
		if(renderer.getTexture() instanceof DynamicTexture)
			((DynamicTexture)getRenderer().getTexture()).swap(selected ? 1 : 0);
	}
	
	@Override
	protected void onClick(float x, float y) {
		select(!isSelected());
	}
	
	@Override
	public void onKeyTrigger(int key, int mods, int action) {}
	
	@Override
	public void onMouseIn() {}
	
	@Override
	public void onMouseOut() {}
	
	@Override
	public void onUpdate(float dt) {}
	
	@Override
	public void onDraw() {
		renderer.draw();
	}
}