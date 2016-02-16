package apie.physics;

import java.util.HashMap;

import apie.debug.Debug;
import apie.math.Vector2f;

public class Collider {

	private static HashMap<String, Collider> colliders = new HashMap<String, Collider>();
	
	Vector2f[] points;
	Vector2f[] normals;
	
	public Collider(String name, Vector2f ... points) {
		this.points = points;
		this.normals = new Vector2f[points.length];
		for (int i = 0; i < points.length; i++) {
			Vector2f n = (Vector2f) points[i].sub(points[(i+1)%points.length]);
			n = (Vector2f) n.scale(1/n.getMagnitude());
			normals[i] = n;
		}
		
		colliders.put(name, this);
	}
	
	public static final Collider get(String name) {
		if (name.equals("box") && !hasCollider("box")) {
			new Collider("box",
					new Vector2f(-0.5f, -0.5f),
					new Vector2f(-0.5f,  0.5f),
					new Vector2f( 0.5f,  0.5f),
					new Vector2f( 0.5f, -0.5f)
				);
			Debug.log("Box created!");
		}
		return colliders.get(name);
	}
	
	public static final boolean hasCollider(String name) {
		return colliders.containsKey(name);
	}
	
	public Vector2f[] getNormals() {
		return normals;
	}
	
	public Vector2f[] getPoints() {
		return points;
	}
	
	public Vector2f project(Vector2f n, float w, float h) {
		float max = 0;
		float min = 0;
		
		for(int i = 0; i < points.length; i++) {
			Vector2f p = new Vector2f(points[i].getX() * w, points[i].getY() * h);
			
			float d = p.dot(n);	
			if (max < d) {
				max = d;
			}
			if (d < min) {
				min = d;
			}
		}
		return new Vector2f(min, max);
	}
	
	
}
