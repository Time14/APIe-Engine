package apie.physics;

import java.util.HashSet;

import apie.debug.Debug;
import apie.gfx.shader.OrthographicShaderProgram;
import apie.math.Vector2f;


/**
 * 
 * A class to hold everything regarding physics.
 * 
 * @author Eddie-boi
 */

public class PhysicsEngine {
	
	private static float frameRate = 60.0f;
	private static float simulationStep = 1.0f/frameRate;
	
	private float moveConstant = 0.1f;
	
	private HashSet<Body> trash;
	private HashSet<Body> bodies;
	private HashSet<Collision> collisions;
	
	private Vector2f gravity;
	
	private float timer;
	
	private boolean useStep = true;
	
	/**
	 * 
	 * Initialized the physics engine.
	 * 
	 */
	public PhysicsEngine() {
		bodies = new HashSet<Body>();
		trash = new  HashSet<Body>();
		collisions = new HashSet<Collision>();
		gravity = new Vector2f(0.0f, 0.0f);
	}
	
	/**
	 * 
	 * Updates the physics engine and progresses the physics world one step forward.
	 * 
	 * @param delta - the time passed since the previous frame
	 * @return - this PhysicsEngine instance
	 */
	public PhysicsEngine update(float delta) {
		
		//Increase the timer to make sure we run at a smooth frame rate.
		if (useStep) {
			timer += delta;
			delta = simulationStep;
		}
		
		//Check if we're ready to step or running each frame, and if so, enter the loop. 
		while (simulationStep < timer || !useStep) {
			//Decrease the timer so we step through the simulation for all the accumulated time
			if (useStep)
				timer -= simulationStep;
			
			//Clear tags
			for(Body b : bodies) {
				if(b == null) {
					removeBody(b);
					continue;
				}
				b._clearTags();
			}
			
			for(Body b : trash) {
				bodies.remove(b);
			}
			
			trash.clear();
			
			//These are to prevent double collisions
			int i = -1;
			for(Body a : bodies) {
				i++;
				if(a == null)
					continue;
				
				if (!a.isAbsolute())
					a.addVel(gravity.clone().scale(delta));
				
				int j = -1;
				for(Body b : bodies) {
					j++;
					if(b == null) continue;
					if(j <= i) continue;
					if(a.absolute && b.absolute && !(a.isTrigger() || b.isTrigger())) continue;
					
					if(Math.abs(a.getPos().getX() - b.getPos().getX()) > (a.getDim().getX() + b.getDim().getX() / 2)
							&& Math.abs(a.getPos().getY() - b.getPos().getY()) > (a.getDim().getY() + b.getDim().getY() / 2))
						continue;
					
					a._checkCollision(b, this);
				}
			}
			
			for (Collision c : collisions) {
				c._solve();
			}
			collisions.clear();
			
			for(Body a : bodies) {
				if(a == null)
					continue;
				a.update(delta);
			}
			
			//Break if we're running every frame
			if (!useStep)
				break;
		}
		return this;
	}
	
	/**
	 * 
	 * Sets the gravity constant to the supplied vector quantity.
	 * 
	 * @param x - The speed on the x-axis per second
	 * @param y - The speed on the y-axis per second
	 * @return this PhysicsEngine instance
	 */
	public PhysicsEngine setGravity(float x, float y) {
		gravity.setX(x);
		gravity.setY(y);
		return this;
	}
	
	/**
	 * Removes the body from the physics simulation.
	 * @param body The body we want to remove.
	 * @return This physics engine.
	 */
	public PhysicsEngine removeBody(Body body) {
		if (bodies.contains(body) && !trash.contains(body)) {
			trash.add(body);
		}
		return this;
	}
	
	
	
	/**
	 * 
	 * Draws all bodies within this PhysicsEngine. This is method is used for debugging only.
	 * 
	 * @return this PhysicsEngine instance
	 */
	public PhysicsEngine _debugDraw() {
		for(Body b : bodies)
			Debug.drawRect(b);
		return this;
	}
	 /**
	  * 
	  * Adds a body to the physics simulation.
	  * 
	  * @param body - the body to add
	  * @return this PhysicsEngine instance
	  */
	public PhysicsEngine addBody(Body body) {
		bodies.add(body);
		return this;
	}
	 /**
	  * 
	  * It adds a collision between two bodies for the physics engine to handle.
	  * Do not run call this method since it should only be called internally by the PhysicsEngine. 
	  * 
	  * @param col - the collision to add
	  * @return this PhysicsEngine instance
	  */
	public PhysicsEngine addCollision(Collision col) {
		collisions.add(col);
		return this;
	}
	
	/**
	 * 
	 * Sets the frame rate for this physics engine.
	 * 
	 * @param frameRate - the new frame rate
	 */
	public static final void setFrameRate(float frameRate) {
		PhysicsEngine.frameRate = frameRate;
		PhysicsEngine.simulationStep = 1f / frameRate;
	}
	
	/**
	 * 
	 * Sets whether to use step or pure delta time.
	 * 
	 * @param useStep - true if you wish to use step
	 */
	public final void useStep(boolean useStep) {
		this.useStep = useStep;
	}
	
	/**
	 * 
	 * Sets the move constant for all collisions.
	 * 
	 * @param moveConstant - the new move constant
	 */
	public void setMoveConstant(float moveConstant) {
		this.moveConstant = moveConstant;
	}
	
	/**
	 * 
	 * Sets the move constant for all collisions.
	 * 
	 * @param moveConstant - the new move constant
	 */
	public float getMoveConstant() {
		return moveConstant;
	}
}
