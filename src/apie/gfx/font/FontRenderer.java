package apie.gfx.font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import apie.gfx.Mesh;
import apie.gfx.Renderer;
import apie.gfx.VertexTex;
import apie.gfx.shader.OrthographicShaderProgram;
import apie.math.Vector2f;
import apie.math.Vector3f;
import apie.math.Vector4f;

public class FontRenderer extends Renderer {
	
	private String text;
	
	private FontType font;
	
	private float size;
	
	private Vector4f color;
	
	/**
	 * 
	 * Constructs a new font renderer with the specified position and text.
	 * 
	 * @param x - the x coordinate to render the text at
	 * @param y - the y coordinate to render the text at
	 * @param text - the text to render
	 */
	public FontRenderer(float x, float y, String text) {
		this(x, y, text, FontType.FNT_ARIAL);
	}
	
	/**
	 * 
	 * Constructs a new font renderer of the specified text, font type at the provided position.
	 * 
	 * @param x - the x coordinate to render the text at
	 * @param y - the y coordinate to render the text at
	 * @param text - the text to render
	 * @param font - the font type to use
	 */
	public FontRenderer(float x, float y, String text, FontType font) {
		this(x, y, text, font, 1);
	}
	
	/**
	 * 
	 * Constructs a new font renderer of the specified text, font type and size at the provided position.
	 * 
	 * @param x
	 * @param y
	 * @param text
	 * @param font
	 * @param size
	 */
	public FontRenderer(float x, float y, String text, FontType font, float size) {
		this.text = text;
		this.font = font;
		this.size = size;
		color = new Vector4f(1, 1, 1, 1);
		
		transform.setPosition(x, y);
		setSize(size);
		
		program = FontShaderProgram.INSTANCE;
		
		texture = font.getTexture().setParameters(GL11.GL_LINEAR, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_TEXTURE_MIN_FILTER);
		
		if(text.length() < 1)
			mesh = new Mesh().createEmpty();
		else
			createFont(false);
	}
	
	/**
	 * 
	 * Generates the font mesh from the current information.
	 * 
	 * @param created - true if the mesh has already been created
	 */
	private void createFont(boolean created) {
		VertexTex[] vertices = new VertexTex[text.length() * 4];
		
		int[] indices = new int[text.length() * 6];
		
		float cursor = 0;
		for(int i = 0; i < text.length(); i++) {
			FontChar c = font.getChar(text.charAt(i));
			if(c == null)
				c = font.getChar('?');
			
			
			float x = cursor + c.X_OFFSET;
			float y = -c.Y_OFFSET;
			
			vertices[0 + i * 4] = new VertexTex(
				new Vector3f(x, y, 0),
				new Vector2f(c.T_X / texture.getWidth(), c.T_Y / texture.getHeight())
			);
			
			vertices[1 + i * 4] = new VertexTex(
				new Vector3f(x + c.T_WIDTH, y, 0),
				new Vector2f((c.T_X + c.T_WIDTH) / texture.getWidth(), c.T_Y / texture.getHeight())
			);
			
			vertices[2 + i * 4] = new VertexTex(
				new Vector3f(x + c.T_WIDTH, y - c.T_HEIGHT, 0),
				new Vector2f((c.T_X + c.T_WIDTH) / texture.getWidth(), (c.T_Y + c.T_HEIGHT) / texture.getHeight())
			);
			
			vertices[3 + i * 4] = new VertexTex(
				new Vector3f(x, y - c.T_HEIGHT, 0),
				new Vector2f(c.T_X / texture.getWidth(), (c.T_Y + c.T_HEIGHT) / texture.getHeight())
			);
			
			cursor += c.X_ADVANCE;
			
			indices[0 + i * 6] = 3 + i * 4;
			indices[1 + i * 6] = 0 + i * 4;
			indices[2 + i * 6] = 1 + i * 4;
			indices[3 + i * 6] = 3 + i * 4;
			indices[4 + i * 6] = 1 + i * 4;
			indices[5 + i * 6] = 2 + i * 4;
		}
		
		if(created)
			mesh.reallocateData(GL15.GL_STATIC_DRAW, vertices, indices);
		else
			mesh = new Mesh(vertices, indices);
	}
	
	public void reallocateText(String text) {
		this.text = text;
		if(text.length() < 1) {
			mesh.destroy();
			mesh = new Mesh().createEmpty();
		} else {
			createFont(true);
		}
	}
	
	@Override
	public void draw() {
		prepareShader();
		super.draw();
	}
	
	/**
	 * 
	 * Sets the size of this font.
	 * 
	 * @param size - the size of the font
	 * @return this font renderer instance
	 */
	public FontRenderer setSize(float size) {
		this.size = size;
		transform.setScale(size);
		
		return this;
	}
	
	/**
	 * 
	 * Sets the RGB color of this font renderer.
	 * 
	 * @param color - the color to use for this font renderer
	 * @return this font renderer instance
	 */
	public FontRenderer setColor(Vector3f color) {
		this.color = new Vector4f(color.getX(), color.getY(), color.getZ(), 1);
		return this;
	}
	
	/**
	 * 
	 * Sets the RGB color of this font renderer.
	 * 
	 * @param r - the r component of the color
	 * @param g - the g component of the color
	 * @param b - the b component of the color
	 * @return this font renderer instance
	 */
	public FontRenderer setColor(float r, float g, float b) {
		return setColor(new Vector4f(r, g, b, 1));
	}
	
	/**
	 * 
	 * Sets the RGBA color of this font renderer.
	 * 
	 * @param color - the color to use for this font renderer
	 * @return this font renderer instance
	 */
	public FontRenderer setColor(Vector4f color) {
		this.color = color;
		return this;
	}
	
	/**
	 * 
	 * Sets the RGBA color of this font renderer.
	 * 
	 * @param r - the r component of the color
	 * @param g - the g component of the color
	 * @param b - the b component of the color
	 * @param a - the a component of the color
	 * @return this font renderer instance
	 */
	public FontRenderer setColor(float r, float g, float b, float a) {
		return setColor(new Vector4f(r, g, b, a));
	}
	
	/**
	 * 
	 * Returns the current RGBA color of this font renderer.
	 * 
	 * @return the color of this font renderer
	 */
	public Vector4f getColor() {
		return color;
	}
	
	/**
	 * 
	 * Sets the uniform width and edge of the text to draw.
	 * 
	 * @return
	 */
	private FontRenderer prepareShader() {
		FontShaderProgram.INSTANCE.sendFloat("width", .46f * (1 + size / 100f));
		FontShaderProgram.INSTANCE.sendFloat("edge", .2f * (1f / (size * 2)));
		FontShaderProgram.INSTANCE.sendVec4("fontColor", color);
		
		return this;
	}
	
	/**
	 * 
	 * Returns the full width of the text mesh.
	 * 
	 * @return the full width of the text mesh
	 */
	public float getWidth() {
		
		float width= 0;
		for(int i = 0; i < text.length(); i++) {
			width += font.getChar(text.charAt(i)).X_ADVANCE * size;
		}
		
		return width;
	}
	
	/**
	 * 
	 * Returns the maximum height of the text mesh.
	 * 
	 * @return the maximum height of the text mesh
	 */
	public float getHeight() {
		
		float height = 0;
		for(int i = 0; i < text.length(); i++) {
			FontChar c = font.getChar(text.charAt(i));
			float h = (c.T_HEIGHT + c.Y_OFFSET) * size;
			
			if(h > height)
				height = h;
		}
		
		return height;
	}
	
	/**
	 * 
	 * Returns the average height of each font character.
	 * 
	 * @return the average height of each font character
	 */
	public float getAverageHeight() {
		return (texture.getHeight() / 16) * size;
	}
	
	/**
	 * 
	 * Returns the size of this font renderer.
	 * 
	 * @return the current size of this font renderer
	 */
	public float getSize() {
		return size;
	}
	
	/**
	 * 
	 * Returns the font type used by this font renderer.
	 * 
	 * @return the font type used by this font renderer
	 */
	public FontType getFontType() {
		return font;
	}
	
	/**
	 * 
	 * Returns the text string represented by this font renderer.
	 * 
	 * @return the text string represented by this font renderer
	 */
	public String getText() {
		return text;
	}
}