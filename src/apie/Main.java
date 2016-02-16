package apie;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import apie.debug.Debug;
import apie.entity.Entity;
import apie.entity.EntityManager;
import apie.gamestate.GameState;
import apie.gamestate.GameStateManager;
import apie.gfx.Mesh;
import apie.gfx.QuadRenderer;
import apie.gfx.Renderer;
import apie.gfx.VertexTex;
import apie.gfx.font.FontRenderer;
import apie.gfx.font.FontType;
import apie.gfx.gui.Button;
import apie.gfx.gui.CheckBox;
import apie.gfx.gui.GUI;
import apie.gfx.gui.InputBox;
import apie.gfx.shader.OrthographicShaderProgram;
import apie.gfx.texture.Animation;
import apie.gfx.texture.DynamicTexture;
import apie.gfx.texture.SpriteSheet;
import apie.gfx.texture.Texture;
import apie.input.InputManager;
import apie.input.KeyState;
import apie.level.Level;
import apie.math.Transform;
import apie.math.Vector2f;
import apie.math.Vector3f;
import apie.physics.Body;
import apie.physics.Collider;
import apie.physics.PhysicsEngine;
import apie.util.Time;

public class Main {
	
	public static final void main(String[] args) {
		
		Game game = new Game();
		
		GameStateManager.registerState(new GameState("Main") {
			
			Level level;
			PhysicsEngine pe = new PhysicsEngine();
			EntityManager em = new EntityManager();
			
			
			
			@Override
			public void init() {
				//Projection
				OrthographicShaderProgram.initProjection(0, 1280, 0, 720);
				OrthographicShaderProgram.INSTANCE.sendMatrix("m_projection", OrthographicShaderProgram.getProjection());
				
//				level = new Level("res/level/test.level");
				
				
				Entity e = new Entity();
				e.setRenderer(new QuadRenderer(50, 200, 100, 100, Texture.DEFAULT_TEXTURE));
				//e.transform = new Transform(new Vector2f(100.0f, 200.0f));
				e.setBody(new Body(e.transform,new Collider("tri", 
						new Vector2f( 0.5f,  0.5f),
						new Vector2f(-0.5f, -0.5f),
						new Vector2f( 0.5f, -0.5f)
						), 100, 100).setAbsolute(true));
				em.addEntity("a", e);
				pe.addBody(e.getBody());
				
				Entity a = new Entity();
				a.setRenderer(new QuadRenderer(100, 500, 100, 100, Texture.DEFAULT_TEXTURE));
				//a.transform = new Transform(new Vector2f(100.0f, 500.0f));
				a.setBody(new Body(a.transform, 100, 100).setAbsolute(false));
				em.addEntity("Ted", a);
				pe.addBody(a.getBody());
			
				pe.setGravity(0, -50);
				em.draw();
			}
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				
			}
			
			@Override
			public void onKeyboard(long window, int key, int scancode, int action, int mods) {
				
			}
			
			@Override
			public void update(float dt) {
				GLFW.glfwSetWindowTitle(game.getWindow(), Integer.toString(Time.getFPS()));
				em.update(dt);
				pe.update(dt);
//				level.update(dt);
			}
			
			@Override
			public void draw() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
//				level.draw();
				em.draw();
				pe._debugDraw();
			}

			@Override
			public void exit() {
				
			}
		});
		
		 game.run("TimeWars", 1280, 720);
	}
}