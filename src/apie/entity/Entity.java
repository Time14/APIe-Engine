package apie.entity;

import apie.gfx.Renderer;
import apie.math.Transform;
import apie.math.Vector2f;
import apie.physics.Body;
import apie.physics.PhysicsEngine;

public class Entity {
	
	protected Renderer renderer;
	
	protected Body body;
	
	public Transform transform = new Transform();
	
	/**
	 * 
	 * Constructs an entity without body and renderer.
	 * 
	 */
	public Entity() {}
	
	/**
	 * 
	 * Constructs an entity without a renderer.
	 * 
	 * @param body - the physical body of this entity
	 */
	public Entity(Body body) {
		setBody(body);
	}
	
	/**
	 * 
	 * Constructs an entity without a physical body.
	 * 
	 * @param renderer - the renderer of this entity
	 */
	public Entity(Renderer renderer) {
		setRenderer(renderer);
	}
	
	/**
	 * 
	 * Constructs an entity with both physical body and renderer.
	 * 
	 * @param renderer - the renderer of this entity
	 * @param body - the body of this entity
	 */
	public Entity(Renderer renderer, Body body) {
		setRenderer(renderer);
		setBody(body);
	}
	
	/**
	 * 
	 * Updates the entity and its body. The body will not be updated if none has been specified.
	 * 
	 * @param dt - the delta time since previous frame
	 */
	public void update(float dt) {
		if(body != null)
			body.update(dt);
	}
	
	/**
	 * 
	 * Causes this entity to draw using the specified renderer. If there is no renderer specified nothing will happen.
	 * 
	 */
	public void draw() {
		if(renderer != null)
			renderer.draw();
	}
	
	/**
	 * 
	 * Sets the renderer of this entity.
	 * 
	 * @param renderer - the renderer to be set
	 * @return this entity instance
	 */
	public Entity setRenderer(Renderer renderer) {
		this.renderer = renderer;
		transform = renderer.getTransform();
		
		return this;
	}
	
	/**
	 * 
	 * Returns the renderer of this entity.
	 * 
	 * @return the renderer of this entity
	 */
	public Renderer getRenderer() {
		return renderer;
	}
	
	/**
	 * 
	 * Sets the physical body of this entity.
	 * 
	 * @param body - the physical body to be set
	 * @return
	 */
	public Entity setBody(Body body) {
		this.body = body;
		return this;
	}
	
	public Entity removeBody(PhysicsEngine pe, Body body) {
		pe.removeBody(body);
		return this;
	}
	
	/**
	 * 
	 * Returns the physical body of this entity.
	 * 
	 * @return the physical body of this entity
	 */
	public Body getBody() {
		return body;
	}
	
	/**
	 * 
	 * Checks whether or not a point is contained within this entity's body bounds.
	 * 
	 * @param x - the x coordinate of the point
	 * @param y - the y coordinate of the point
	 * @return true if the point is contained
	 */
	public boolean contains(float x, float y) {
		return body.contains(x, y);
	}
	
	/**
	 * 
	 * Checks whether or not a point is contained within this entity's body bounds.
	 * 
	 * @param p - the point
	 * @return true if the point is contained
	 */
	public boolean contains(Vector2f p) {
		return body.contains(p);
	}
	
	/**
	 * 
	 * Sets the z-axis rotation of this entity's transform.
	 * 
	 * @param rotation - the rotation to set
	 * @return this entity's transform instance
	 */
	public Entity setRotation(float rotation) {
		transform.setRotation(rotation);
		return this;
	}
	
	/**
	 * 
	 * Rotates this entity's transform around the z-axis.
	 * 
	 * @param rotation - the rotation amount
	 * @return this renderer instance
	 */
	public Entity rotate(float rotation) {
		transform.rotate(rotation);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this entity's transform uniformly.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Entity setScale(float scale) {
		setScaleX(scale);
		return setScaleY(scale);
	}
	
	/**
	 * 
	 * Sets the scale of this entity's transform on the x-axis.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Entity setScaleX(float scale) {
		transform.setScaleX(scale);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this entity's transform on the y-axis.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Entity setScaleY(float scale) {
		transform.setScaleY(scale);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this entity's transform.
	 * 
	 * @param x - the x-axis scale
	 * @param y - the y-axis scale
	 * @return this renderer instance
	 */
	public Entity setScale(float x, float y) {
		return setScale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Sets the scale of this entity's transform to the specified vector.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Entity setScale(Vector2f scale) {
		transform.setScale(scale);
		return this;
	}
	
	/**
	 * 
	 * Returns the x component of this renderer's scale.
	 * 
	 * @return the x component of this renderer's scale
	 */
	public float getScaleX() {
		return transform.getScaleX();
	}
	
	/**
	 * 
	 * Returns the y component of this renderer's scale.
	 * 
	 * @return the y component of this renderer's scale
	 */
	public float getScaleY() {
		return transform.getScaleY();
	}
	
	/**
	 * 
	 * Adds scale to this entity's transform uniformly.
	 * 
	 * @param scale - the scale to add
	 * @return this renderer instance
	 */
	public Entity addScale(float scale) {
		addScaleX(scale);
		return addScaleY(scale);
	}
	
	/**
	 * 
	 * Adds scale to this entity's transform on the x-axis.
	 * 
	 * @param scale - the scale to add
	 * @return this renderer instance
	 */
	public Entity addScaleX(float scale) {
		transform.addScaleX(scale);
		return this;
	}
	
	/**
	 * 
	 * Adds scale to this entity's transform on the y-axis.
	 * 
	 * @param scale - the scale to add
	 * @return
	 */
	public Entity addScaleY(float scale) {
		transform.addScaleY(scale);
		return this;
	}
	
	/**
	 * 
	 * Adds scale to this entity's transform.
	 * 
	 * @param x - the scale to add on the x-axis
	 * @param y - the scale to add on the y-axis
	 * @return this renderer instance
	 */
	public Entity addScale(float x, float y) {
		return addScale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Adds the specified vector to this entity's transform scale.
	 * 
	 * @param scale - the scale to add
	 * @return this renderer instance
	 */
	public Entity addScale(Vector2f scale) {
		transform.addScale(scale);
		return this;
	}
	
	/**
	 * 
	 * Scales this entity's transform uniformly.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this renderer instance
	 */
	public Entity scale(float scalar) {
		transform.scale(scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this entity's transform on the x-axis.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this renderer instance
	 */
	public Entity scaleX(float scalar) {
		transform.scaleX(scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this entity's transform on the y-axis.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this renderer instance
	 */
	public Entity scaleY(float scalar) {
		transform.scaleY(scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this entity's transform.
	 * 
	 * @param x - the scalar to scale with on the x-axis
	 * @param y - the scalar to scale with on the y-axis
	 * @return this renderer instance
	 */
	public Entity scale(float x, float y) {
		return scale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Scales this entity's transform with the specified vector.
	 * 
	 * @param scale - the vector to scale with
	 * @return this renderer instance
	 */
	public Entity scale(Vector2f scale) {
		scaleX(scale.getX());
		scaleY(scale.getY());
		return this;
	}
	
	/**
	 * 
	 * Sets the x coordinate of this entity's transform.
	 * 
	 * @param x - the x coordinate to set
	 * @return this renderer instance
	 */
	public Entity setX(float x) {
		transform.setX(x);
		return this;
	}
	
	/**
	 * 
	 * Sets the y coordinate of this entity's transform.
	 * 
	 * @param y - the y coordinate to set
	 * @return this renderer instance
	 */
	public Entity setY(float y) {
		transform.setY(y);
		return this;
	}
	
	/**
	 * 
	 * Sets the position of this entity's transform.
	 * 
	 * @param x - the x coordinate to set
	 * @param y - the y coordinate to set
	 * @return this renderer instance
	 */
	public Entity setPosition(float x, float y) {
		return setPosition(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Sets the position of this entity's transform to the specified vector.
	 * 
	 * @param position - the position to set
	 * @return this renderer instance
	 */
	public Entity setPosition(Vector2f position) {
		setX(position.getX());
		setY(position.getY());
		return this;
	}
	
	/**
	 * 
	 * Returns the x coordinate of this entity's transform.
	 * 
	 * @return the x coordinate of this entity's transform
	 */
	public float getX() {
		return transform.getX();
	}
	
	/**
	 * 
	 * Returns the y coordinate of this entity's transform.
	 * 
	 * @return the y coordinate of this entity's transform
	 */
	public float getY() {
		return transform.getY();
	}
	
	/**
	 * 
	 * Translates this entity's transform on the x-axis.
	 * 
	 * @param x - the translation on the x-axis
	 * @return this renderer instance
	 */
	public Entity translateX(float x) {
		transform.translateX(x);
		return this;
	}
	
	/**
	 * 
	 * Translates this entity's transform on the y-axis.
	 * 
	 * @param y - the translation on the y-axis
	 * @return this renderer instance
	 */
	public Entity translateY(float y) {
		transform.translateY(y);
		return this;
	}
	
	/**
	 * 
	 * Translates this entity's transform.
	 * 
	 * @param x - the translation on the x-axis
	 * @param y - the translation on the y-axis
	 * @return this renderer instance
	 */
	public Entity translate(float x, float y) {
		return translate(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Translates this entity's transform with the specified vector.
	 * 
	 * @param offset - the vector to translate with
	 * @return this renderer instance
	 */
	public Entity translate(Vector2f offset) {
		transform.translate(offset);
		return this;
	}
}