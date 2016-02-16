package apie.gfx;

import apie.gfx.shader.OrthographicShaderProgram;
import apie.gfx.shader.ShaderProgram;
import apie.math.Vector2f;
import apie.math.Vector3f;

public class VertexTex extends Vertex {
	
	/**
	 * 
	 * Creates a vertex with two components:
	 * one Vecotr3f representing the position and
	 * one Vector2f representing the texture coordinate.
	 * 
	 * 
	 * @param pos - the position for this vertex
	 * @param texCoords - the texture coordinate for this vertex
	 */
	public VertexTex(Vector3f pos, Vector2f texCoords) {
		super(pos, texCoords);
	}
	
	public ShaderProgram getShaderProgram() {
		return OrthographicShaderProgram.INSTANCE;
	}
}