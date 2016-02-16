package apie.gfx;

import apie.gfx.texture.Texture;
import apie.math.Vector2f;
import apie.math.Vector3f;

public class QuadRenderer extends Renderer {
	
	
	public QuadRenderer(float x, float y, float width, float height, Texture texture) {
		this(x, y, width, height, 0, 0, 1, 1, texture);
	}
	
	public QuadRenderer(float x, float y, float width, float height, float sMin, float tMin, float sMax, float tMax, Texture texture) {
		setMesh(new Mesh(new VertexTex[]{
				new VertexTex(new Vector3f(-width/2, -height/2, 0), new Vector2f(sMin, tMax)),
				new VertexTex(new Vector3f(-width/2, height/2, 0), new Vector2f(sMin, tMin)),
				new VertexTex(new Vector3f(width/2, height/2, 0), new Vector2f(sMax, tMin)),
				new VertexTex(new Vector3f(width/2, -height/2, 0), new Vector2f(sMax, tMax))
		}, 0, 1, 2, 0, 2, 3));
		getTransform().translate(x, y);
		setTexture(texture);
	}
	
}