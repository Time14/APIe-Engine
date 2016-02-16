package apie.gfx;

import apie.gfx.shader.ShaderProgram;
import apie.gfx.texture.Texture;
import apie.math.Transform;
import apie.math.Vector2f;

public class Renderer {
	
	protected Texture texture = Texture.DEFAULT_TEXTURE;
	
	protected Mesh mesh;
	
	protected ShaderProgram program;
	
	protected Transform transform = new Transform();
	
	/**
	 * 
	 * Constructs an empty renderer. This renderer will not be able to process at least until a mesh has been specified.
	 * 
	 */
	public Renderer() {}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh.
	 * 
	 * @param mesh - the mesh of this renderer
	 */
	public Renderer(Mesh mesh) {
		setMesh(mesh);
	}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh and transform.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @param transform - the transform of this renderer
	 */
	public Renderer(Mesh mesh, Transform transform) {
		setMesh(mesh);
		setTransform(transform);
	}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh and texture.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @param texture - the texture of this renderer
	 */
	public Renderer(Mesh mesh, Texture texture) {
		setMesh(mesh);
		setTexture(texture);
	}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh, transform and texture.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @param transform - the transform of this renderer
	 * @param texture - the texture of this renderer
	 */
	public Renderer(Mesh mesh, Transform transform, Texture texture) {
		setMesh(mesh);
		setTransform(transform);
		setTexture(texture);
	}
	
	/**
	 * 
	 *  Draws with the specified mesh, texture and transform.
	 * 
	 */
	public void draw() {
		program.bind();
		program.sendMatrix("m_transform", transform.getMatrix());
		texture.bind();
		mesh.draw();
	}
	
	/**
	 * 
	 * Sets the mesh of this renderer.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @return this renderer instance
	 */
	public Renderer setMesh(Mesh mesh) {
		this.mesh = mesh;
		program = mesh.getShaderProgram();
		return this;
	}
	
	/**
	 * 
	 * Sets the transform of this renderer.
	 * 
	 * @param transform - the transform of this renderer
	 * @return this renderer instance
	 */
	public Renderer setTransform(Transform transform) {
		this.transform = transform;
		return this;
	}
	
	/**
	 * 
	 * Returns the position vector from this renderer's transform.
	 * 
	 * @return the position vector from this renderer's transform
	 */
	public Vector2f getPosition() {
		return transform.pos;
	}
	
	/**
	 * 
	 * Returns the rotation from this renderer's transform.
	 * 
	 * @return the rotation from this renderer's transform
	 */
	public float getRotation() {
		return transform.rotation;
	}
	
	/**
	 * 
	 * Returns the scale vector from this renderer's transform.
	 * 
	 * @return the scale vector from this renderer's transform
	 */
	public Vector2f getScale() {
		return transform.scale;
	}
	
	/**
	 * 
	 * Sets the texture of this renderer.
	 * 
	 * @param texture - the texture of this renderer
	 * @return this renderer instance
	 */
	public Renderer setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	
	/**
	 * 
	 * Returns the mesh of this renderer.
	 * 
	 * @return the mesh of this renderer
	 */
	public Mesh getMesh() {
		return mesh;
	}
	
	/**
	 * 
	 * Returns the transform of this mesh.
	 * 
	 * @return the transform of this mesh
	 */
	public Transform getTransform() {
		return transform;
	}
	
	/**
	 * 
	 * Returns the texture of this renderer.
	 * 
	 * @return the texture of this renderer
	 */
	public Texture getTexture() {
		return texture;
	}
	
	/**
	 * 
	 * Sets the z-axis rotation of this renderer's transform.
	 * 
	 * @param rotation - the rotation to set
	 * @return this renderer's transform instance
	 */
	public Renderer setRotation(float rotation) {
		transform.setRotation(rotation);
		return this;
	}
	
	/**
	 * 
	 * Rotates this renderer's transform around the z-axis.
	 * 
	 * @param rotation - the rotation amount
	 * @return this renderer instance
	 */
	public Renderer rotate(float rotation) {
		transform.rotate(rotation);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this renderer's transform uniformly.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Renderer setScale(float scale) {
		setScaleX(scale);
		return setScaleY(scale);
	}
	
	/**
	 * 
	 * Sets the scale of this renderer's transform on the x-axis.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Renderer setScaleX(float scale) {
		transform.setScaleX(scale);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this renderer's transform on the y-axis.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Renderer setScaleY(float scale) {
		transform.setScaleY(scale);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this renderer's transform.
	 * 
	 * @param x - the x-axis scale
	 * @param y - the y-axis scale
	 * @return this renderer instance
	 */
	public Renderer setScale(float x, float y) {
		return setScale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Sets the scale of this renderer's transform to the specified vector.
	 * 
	 * @param scale - the scale to set
	 * @return this renderer instance
	 */
	public Renderer setScale(Vector2f scale) {
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
	 * Adds scale to this renderer's transform uniformly.
	 * 
	 * @param scale - the scale to add
	 * @return this renderer instance
	 */
	public Renderer addScale(float scale) {
		addScaleX(scale);
		return addScaleY(scale);
	}
	
	/**
	 * 
	 * Adds scale to this renderer's transform on the x-axis.
	 * 
	 * @param scale - the scale to add
	 * @return this renderer instance
	 */
	public Renderer addScaleX(float scale) {
		transform.addScaleX(scale);
		return this;
	}
	
	/**
	 * 
	 * Adds scale to this renderer's transform on the y-axis.
	 * 
	 * @param scale - the scale to add
	 * @return
	 */
	public Renderer addScaleY(float scale) {
		transform.addScaleY(scale);
		return this;
	}
	
	/**
	 * 
	 * Adds scale to this renderer's transform.
	 * 
	 * @param x - the scale to add on the x-axis
	 * @param y - the scale to add on the y-axis
	 * @return this renderer instance
	 */
	public Renderer addScale(float x, float y) {
		return addScale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Adds the specified vector to this renderer's transform scale.
	 * 
	 * @param scale - the scale to add
	 * @return this renderer instance
	 */
	public Renderer addScale(Vector2f scale) {
		transform.addScale(scale);
		return this;
	}
	
	/**
	 * 
	 * Scales this renderer's transform uniformly.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this renderer instance
	 */
	public Renderer scale(float scalar) {
		transform.scale(scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this renderer's transform on the x-axis.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this renderer instance
	 */
	public Renderer scaleX(float scalar) {
		transform.scaleX(scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this renderer's transform on the y-axis.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this renderer instance
	 */
	public Renderer scaleY(float scalar) {
		transform.scaleY(scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this renderer's transform.
	 * 
	 * @param x - the scalar to scale with on the x-axis
	 * @param y - the scalar to scale with on the y-axis
	 * @return this renderer instance
	 */
	public Renderer scale(float x, float y) {
		return scale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Scales this renderer's transform with the specified vector.
	 * 
	 * @param scale - the vector to scale with
	 * @return this renderer instance
	 */
	public Renderer scale(Vector2f scale) {
		scaleX(scale.getX());
		scaleY(scale.getY());
		return this;
	}
	
	/**
	 * 
	 * Sets the x coordinate of this renderer's transform.
	 * 
	 * @param x - the x coordinate to set
	 * @return this renderer instance
	 */
	public Renderer setX(float x) {
		transform.setX(x);
		return this;
	}
	
	/**
	 * 
	 * Sets the y coordinate of this renderer's transform.
	 * 
	 * @param y - the y coordinate to set
	 * @return this renderer instance
	 */
	public Renderer setY(float y) {
		transform.setY(y);
		return this;
	}
	
	/**
	 * 
	 * Sets the position of this renderer's transform.
	 * 
	 * @param x - the x coordinate to set
	 * @param y - the y coordinate to set
	 * @return this renderer instance
	 */
	public Renderer setPosition(float x, float y) {
		return setPosition(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Sets the position of this renderer's transform to the specified vector.
	 * 
	 * @param position - the position to set
	 * @return this renderer instance
	 */
	public Renderer setPosition(Vector2f position) {
		setX(position.getX());
		setY(position.getY());
		return this;
	}
	
	/**
	 * 
	 * Returns the x coordinate of this renderer's transform.
	 * 
	 * @return the x coordinate of this renderer's transform
	 */
	public float getX() {
		return transform.getX();
	}
	
	/**
	 * 
	 * Returns the y coordinate of this renderer's transform.
	 * 
	 * @return the y coordinate of this renderer's transform
	 */
	public float getY() {
		return transform.getY();
	}
	
	/**
	 * 
	 * Translates this renderer's transform on the x-axis.
	 * 
	 * @param x - the translation on the x-axis
	 * @return this renderer instance
	 */
	public Renderer translateX(float x) {
		transform.translateX(x);
		return this;
	}
	
	/**
	 * 
	 * Translates this renderer's transform on the y-axis.
	 * 
	 * @param y - the translation on the y-axis
	 * @return this renderer instance
	 */
	public Renderer translateY(float y) {
		transform.translateY(y);
		return this;
	}
	
	/**
	 * 
	 * Translates this renderer's transform.
	 * 
	 * @param x - the translation on the x-axis
	 * @param y - the translation on the y-axis
	 * @return this renderer instance
	 */
	public Renderer translate(float x, float y) {
		return translate(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Translates this renderer's transform with the specified vector.
	 * 
	 * @param offset - the vector to translate with
	 * @return this renderer instance
	 */
	public Renderer translate(Vector2f offset) {
		transform.translate(offset);
		return this;
	}
}