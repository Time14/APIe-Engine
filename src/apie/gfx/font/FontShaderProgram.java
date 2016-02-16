package apie.gfx.font;

import org.lwjgl.opengl.GL11;

import apie.gfx.shader.OrthographicShaderProgram;
import apie.gfx.shader.ShaderProgram;

public class FontShaderProgram extends ShaderProgram {
	
	/**
	 * 
	 * Constructs a new font shader program
	 * 
	 */
	public FontShaderProgram() {
		super("res/shader/font.vsh", "res/shader/font.fsh");
		loadOrthographicProjection();
	}
	
	@Override
	protected void registerUniformLocations() {
		registerUniformLocation("m_projection");
		registerUniformLocation("m_transform");
		registerUniformLocation("t_sampler");
		registerUniformLocation("width");
		registerUniformLocation("edge");
		registerUniformLocation("fontColor");
	}
	
	@Override
	public int getOutputFormat() {
		return GL11.GL_RGBA;
	}
	
	/**
	 * 
	 * Fetches the current projection from the orthographic shader program.
	 * 
	 */
	public void loadOrthographicProjection() {
		sendMatrix("m_projection", OrthographicShaderProgram.getProjection());
	}
	
	public static final FontShaderProgram INSTANCE = new FontShaderProgram();
}