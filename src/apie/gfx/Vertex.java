package apie.gfx;

import java.util.ArrayList;

import apie.gfx.shader.ShaderProgram;
import apie.math.Vector2f;
import apie.math.Vector3f;
import apie.math.VectorXf;

public abstract class Vertex {
	protected VectorXf[] components;
	
	/**
	 * 
	 * Creates a vertex with the specified components.
	 * 
	 * @param components - the components of this vertex
	 */
	public Vertex(VectorXf... components) {
		this.components = components;
	}
	
	/**
	 * 
	 * Returns the specified component of this vertex.
	 * 
	 * @param i - the index of the component to return
	 * @return the specified component of this vertex
	 */
	public VectorXf getComponent(int i) {
		return components[i];
	}
	
	/**
	 * 
	 * Returns the number of components contained in this vertex.
	 * 
	 * @return the number of components contained in this vertex
	 */
	public int getLength() {
		return components.length;
	}
	
	/**
	 * 
	 * Returns the shader program associated with this vertex.
	 * 
	 * @return the shader program associated with this vertex
	 */
	public abstract ShaderProgram getShaderProgram();
}