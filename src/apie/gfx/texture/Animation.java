package apie.gfx.texture;

public class Animation  {
	
	private DynamicTexture dynTex;
	
	private int mark;
	private int[] indices;
	
	private float speed = 1f;
	
	private float deltaStack;
	
	/**
	 * 
	 * Creates an empty Animation. The indices and dynamic texture must be set before usage.
	 * 
	 */
	public Animation() {}
	
	/**
	 * 
	 * Creates a new Animation with the provided dynamic texture and indices.
	 * 
	 * @param dynTex - the dynamic texture of this animation
	 * @param indices - the indices of this animation
	 */
	public Animation(DynamicTexture dynTex, int... indices) {
		setDynamicTexture(dynTex);
		setIndices(indices);
	}
	
	/**
	 * 
	 * Updates this animation accordingly to the current speed.
	 * 
	 * @param dt - the time passed since the previous update
	 */
	public void update(float dt) {
		deltaStack += dt;
		if(deltaStack > 1f / speed) {
			deltaStack = 0;
			dynTex.swap(indices[mark]);
			if(mark < indices.length - 1)
				if(indices[mark + 1] == -1)
					return;
			mark++;
			mark %= indices.length;
		}
	}
	
	/**
	 * 
	 * Returns the current texture of this animations dynamic texture.
	 * 
	 * @return the current texture of this animations dynamic texture
	 */
	public Texture getTexture() {
		return dynTex.getTexture();
	}
	
	/**
	 * 
	 * Sets the speed of this animation.
	 * 
	 * @param speed - the speed to be set
	 * @return this animation instance
	 */
	public Animation setSpeed(float speed) {
		this.speed = speed;
		return this;
	}
	
	/**
	 * 
	 * Returns the speed of this animation.
	 * 
	 * @return the speed of this animation
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * 
	 * Sets the dynamic texture of this animation.
	 * 
	 * @param dynTex - the dynamic texture to be set
	 * @return this animation instance
	 */
	public Animation setDynamicTexture(DynamicTexture dynTex) {
		this.dynTex = dynTex;
		return this;
	}
	
	/**
	 * 
	 * Sets the indices of this animation.
	 * 
	 * @param indices - the indices to be set
	 * @return this animation instance
	 */
	public Animation setIndices(int... indices) {
		this.indices = indices;
		return this;
	}
	
	/**
	 * 
	 * Returns the dynamic texture of this animation.
	 * 
	 * @return the dynamic texture of this animation
	 */
	public DynamicTexture getDynamicTexture() {
		return dynTex;
	}
	
	/**
	 * 
	 * Returns the indices of this animation.
	 * 
	 * @return the indices of this animation
	 */
	public int[] getIndices() {
		return indices;
	}
	
	/**
	 * 
	 * Returns the animation marks current position within this animation.
	 * 
	 * @return the current mark offset
	 */
	public int getMark() {
		return mark;
	}
	
	/**
	 * 
	 * Sets the current mark offset within this animation.
	 * 
	 * @param mark - the mark to jump to
	 * @return this animation instance
	 */
	public Animation setMark(int mark) {
		this.mark = mark;
		dynTex.swap(indices[mark]);
		
		return resetDeltaStack();
	}
	
	/**
	 * 
	 * Resets the current delta stack of this animation.
	 * 
	 * @return this animation instance
	 */
	public Animation resetDeltaStack() {
		deltaStack = 0;
		
		return this;
	}
	
	/**
	 * 
	 * Resets this animation to its original state.
	 * 
	 * @return this animation instance
	 */
	public Animation reset() {
		deltaStack = 0;
		mark = 0;
		dynTex.swap(indices[mark]);
		
		return this;
	}
}