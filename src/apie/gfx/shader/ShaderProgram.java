package apie.gfx.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

import apie.gfx.Vertex;
import apie.gfx.shader.StructDefiner.GLSLType;
import apie.math.Matrix4f;
import apie.math.Vector2f;
import apie.math.Vector3f;
import apie.math.Vector4f;
import apie.util.Loader;
import apie.util.Util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
	
	protected static int currentProgram;
	
	private int id;
	
	protected HashMap<String, Integer> ul;
	
	/**
	 * 
	 * Generates a new shader program with one vertex shader and one fragment shader. 
	 * 
	 * @param vshp - the path for the vertex shader to load
	 * @param fshp - the path for the fragment shader to load
	 */
	public ShaderProgram(String vshp, String fshp) {
		
		int vsh = createShader(vshp, GL_VERTEX_SHADER);
		int fsh = createShader(fshp, GL_FRAGMENT_SHADER);
		
		id = glCreateProgram();
		
		glAttachShader(id, vsh);
		glAttachShader(id, fsh);
		
		glLinkProgram(id);
		
		if(glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println("Error linking shader program \"" + id + "\"");
			System.err.println("-------------------------------- START --------------------------------");
			System.err.println(glGetProgramInfoLog(id, glGetProgrami(id, GL_INFO_LOG_LENGTH)));
			System.err.println("--------------------------------  END  --------------------------------");
		}
		
		glDetachShader(id, vsh);
		glDetachShader(id, fsh);
		glDeleteShader(vsh);
		glDeleteShader(fsh);
		
		ul = new HashMap<>();
		registerUniformLocations();
	}
	
	/**
	 * 
	 * Creates a new shader from the provided path with the specified type.
	 * 
	 * @param path - the path of the file to load the shader from
	 * @param type - specifies the type of shader to be created. Must be one of GL_COMPUTE_SHADER, GL_VERTEX_SHADER, GL_TESS_CONTROL_SHADER, GL_TESS_EVALUATION_SHADER, GL_GEOMETRY_SHADER, or GL_FRAGMENT_SHADER
	 * @return the id of the created shader
	 */
	private int createShader(String path, int type) {
		
		int shader = glCreateShader(type);
		glShaderSource(shader, Loader.loadSource(path));
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Error in " + (type == GL_VERTEX_SHADER ? "vertex" : "fragment") + " shader at \"" + path + "\"");
			System.err.println("-------------------------------- START --------------------------------");
			System.err.println(glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH)));
			System.err.println("--------------------------------  END  --------------------------------");
		}
	
		return shader;
	}
	
	/**
	 * 
	 * Binds this shader program for graphical use.
	 * 
	 */
	public void bind() {
		if(id == currentProgram)
			return;
		glUseProgram(id);
		currentProgram = id;
	}
	
	/**
	 * 
	 * Returns the id of this shader program.
	 * 
	 * @return the id of this shader program
	 */
	public int getID() {
		return id;
	}
	
	
	/**
	 * 
	 * Generates vertex buffer objects correspondingly to the specified vertices' format. Bind a vertex array buffer before calling this method.
	 * 
	 * @param vertices - the vertices to generate vertex buffer objects from
	 * @param usage - specifies the expected usage pattern of the data store. The symbolic constant must be GL_STREAM_DRAW, GL_STREAM_READ, GL_STREAM_COPY, GL_STATIC_DRAW, GL_STATIC_READ, GL_STATIC_COPY, GL_DYNAMIC_DRAW, GL_DYNAMIC_READ, or GL_DYNAMIC_COPY
	 * @return the IDs of the generated vertex buffer objects
	 */
	public IntBuffer initAttributes(Vertex[] vertices, int usage) {
		IntBuffer vbos = Util.createIntBuffer(vertices[0].getLength());
		
		for(int i = 0; i < vertices[0].getLength(); i++) {
			FloatBuffer buffer = Util.createFloatBuffer(vertices.length * vertices[0].getComponent(i).getDimension());
			
			for(Vertex v : vertices) {
				buffer.put(v.getComponent(i).getData());
			}
			
			buffer.flip();
			
			int vbo = GL15.glGenBuffers();
			
			vbos.put(vbo);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, usage);
			
			GL20.glEnableVertexAttribArray(i);
			GL20.glVertexAttribPointer(i, vertices[0].getComponent(i).getDimension(), GL11.GL_FLOAT, false, 0, 0);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		
		vbos.flip();
		
		return vbos;
	}
	
	/**
	 * 
	 * Registers all uniform locations for this shader program.
	 * 
	 */
	protected abstract void registerUniformLocations();
	
	/**
	 * 
	 * Returns the output format of this shader program.
	 * 
	 * @return the output format of this shader program, usually GL_RGBA
	 */
	public abstract int getOutputFormat();
	
	/**
	 * 
	 * Registers a uniform location with the specified name.
	 * 
	 * @param uniform - the name of the uniform to register
	 */
	protected void registerUniformLocation(String uniform) {
		ul.put(uniform, getUniformLocation(uniform));
	}
	
	/**
	 * 
	 * Registers uniform array locations with the specified array name.
	 * 
	 * @param uniform - the name of the uniform array to register
	 * @param length - the length of the uniform array to register
	 */
	protected void registerUniformArrayLocations(String uniform, int length) {
		for(int i = 0; i < length; i++)
			registerUniformLocation(uniform + "[" + i + "]");
	}
	
	/**
	 * 
	 * Registers the uniform locations of the specified struct with the specified components.
	 * 
	 * @param uniform - the name of the struct to register
	 * @param comps - the struct component names to register
	 */
	protected void registerUniformStructLocation(String uniform, String[] comps) {
		for(String comp : comps)
			registerUniformLocation(uniform + "." + comp);
	}
	
	/**
	 * 
	 * Registers the uniform struct array locations of the specified struct with the specified components.
	 * 
	 * @param uniform - the name of the struct array to register
	 * @param comps - the struct component names to register
	 * @param length - the length of the uniform struct array to register
	 */
	protected void registerUniformStructArrayLocations(String uniform, String[] comps, int length) {
		for(int i = 0; i < length; i++)
			registerUniformStructLocation(uniform + "[" + i + "]", comps);
	}
	
	/**
	 * 
	 * Returns the location of the specified uniform.
	 * 
	 * @param uniform - the location of the specified uniform.
	 * @return
	 */
	private int getUniformLocation(String uniform) {
		return glGetUniformLocation(id, uniform);
	}
	
	/**
	 * 
	 * Sends the specified matrix to the shader program.
	 * 
	 * @param target - the name of the uniform (must be registered)
	 * @param matrix - the matrix to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendMatrix(String target, Matrix4f matrix) {
		bind();
		glUniformMatrix4fv(ul.get(target), false, Util.toFloatBuffer(matrix));
		return this;
	}
	
	/**
	 * 
	 * Sends the specified matrices to a uniform array in the shader program.
	 * 
	 * @param target - the name of the uniform array (must be registered)
	 * @param matrices - the matrices to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendMatrixArray(String target, Matrix4f[] matrices) {
		for(int i = 0; i < matrices.length; i++)
			sendMatrix(target + "[" + i + "]", matrices[i]);
		return this;
	}
	
	/**
	 * 
	 * Sends an integer to the specified uniform target.
	 * 
	 * @param target - the name of the uniform
	 * @param i - the integer to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendInt(String target, int i) {
		bind();
		glUniform1i(ul.get(target), i);
		return this;
	}
	
	/**
	 * 
	 * Sends an integer array to the specified uniform target.
	 * 
	 * @param target - the name of the uniform array
	 * @param data - the integers to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendIntArray(String target, int[] data) {
		for(int i = 0; i < data.length; i++)
			sendInt(target + "[" + i + "]", data[i]);
		return this;
	}
	
	/**
	 * 
	 * Sends a float to the specified uniform target.
	 * 
	 * @param target - the name of the uniform
	 * @param f - the float to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendFloat(String target, float f) {
		bind();
		glUniform1f(ul.get(target), f);
		return this;
	}
	
	/**
	 * 
	 * Sends a float array to the specified uniform target.
	 * 
	 * @param target - the name of the uniform array
	 * @param data - the floats to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendFloatArray(String target, float[] data) {
		for(int i = 0; i < data.length; i++)
			sendFloat(target + "[" + i + "]", data[i]);
		return this;
	}
	
	/**
	 * 
	 * Sends a Vector2f to the specified uniform target.
	 * 
	 * @param target - the name of the uniform
	 * @param vec2 - the vector to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendVec2(String target, Vector2f vec2) {
		bind();
		glUniform2f(ul.get(target), vec2.getX(), vec2.getY());
		return this;
	}
	
	/**
	 * 
	 * Sends a Vector2f array to the specified uniform target.
	 * 
	 * @param target - the name of the uniform array
	 * @param data - the vectors to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendVec2Array(String target, Vector2f[] data) {
		for(int i = 0; i < data.length; i++)
			sendVec2(target + "[" + i + "]", data[i]);
		return this;
	}
	
	/**
	 * 
	 * Sends a Vector3f to the specified uniform target.
	 * 
	 * @param target - the name of the uniform
	 * @param vec3 - the vector to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendVec3(String target, Vector3f vec3) {
		bind();
		glUniform3f(ul.get(target), vec3.getX(), vec3.getY(), vec3.getZ());
		return this;
	}
	
	/**
	 * 
	 * Sends a Vector3f array to the specified uniform target.
	 * 
	 * @param target - the name of the uniform array
	 * @param data - the vectors to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendVec3Array(String target, Vector3f[] data) {
		for(int i = 0; i < data.length; i++)
			sendVec3(target + "[" + i + "]", data[i]);
		return this;
	}
	
	/**
	 * 
	 * Sends a Vector4f to the specified uniform target.
	 * 
	 * @param target - the name of the uniform
	 * @param vec4 - the vector to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendVec4(String target, Vector4f vec4) {
		bind();
		glUniform4f(ul.get(target), vec4.getX(), vec4.getY(), vec4.getZ(), vec4.getW());
		return this;
	}
	
	/**
	 * 
	 * Sends a Vector4f array to the specified uniform target.
	 * 
	 * @param target - the name of the uniform array
	 * @param data - the vectors to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendVec4Array(String target, Vector4f[] data) {
		for(int i = 0; i < data.length; i++)
			sendVec4(target + "[" + i + "]", data[i]);
		return this;
	}
	
	/**
	 * 
	 * Sends a struct to the specified uniform target.
	 * The StructDefiner containS all information and values required for this struct.
	 * 
	 * @param target - the name of the uniform
	 * @param struct - the struct to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendStruct(String target, StructDefiner struct) {
		bind();
		
		float[] data = struct.getData();
		
		int offset = 0;
		for(int i = 0; i < struct.getStructure().length; i++) {
			GLSLType type = struct.getStructure()[i];
			String location = target + "." + struct.getComponentNames()[i];
			switch(type) {
			case FLOAT:
			case INT:
			case DOUBLE:
				glUniform1f(ul.get(location), data[offset]);
				break;
			case VEC2:
				glUniform2f(ul.get(location), data[offset], data[offset + 1]);
				break;
			case VEC3:
				glUniform3f(ul.get(location), data[offset], data[offset + 1], data[offset + 2]);
				break;
			case VEC4:
				glUniform4f(ul.get(location), data[offset], data[offset + 1], data[offset + 2], data[offset + 3]);
				break;
			case MAT4:
				glUniformMatrix4fv(ul.get(location), false, Util.toFloatBuffer(Arrays.copyOfRange(data, offset, offset + GLSLType.MAT4.LENGTH - 1)));
				break;
			}
			offset += type.LENGTH;
		}
		
		return this;
	}
	
	/**
	 * Sends a struct array to the specified uniform target.
	 * The StructDefiners contain all information and values required for this struct array.
	 * 
	 * @param target - the name of the uniform array
	 * @param structs - the structs to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendStructArray(String target, StructDefiner[] structs) {
		for(int i = 0; i < structs.length; i++)
			sendStruct(target + "[" + i + "]", structs[i]);
		return this;
	}
	
	/**
	 * 
	 * Sends a boolean to the specified uniform target.
	 * 
	 * @param target - the name of the uniform
	 * @param bool - the boolean to send
	 * @return this shader program instance
	 */
	public ShaderProgram sendBoolean(String target, boolean bool) {
		bind();
		glUniform1i(ul.get(target), bool ? GL11.GL_TRUE : GL11.GL_FALSE);
		return this;
	}
	
	/**
	 * 
	 * Sends a boolean array to the specified uniform target.
	 * 
	 * @param target - the name of the uniform array
	 * @param data - the booleans to sends
	 * @return this shader program instance
	 */
	public ShaderProgram sendBooleanArray(String target, boolean[] data) {
		for(int i = 0; i < data.length; i++)
			sendBoolean(target + "[" + i + "]", data[i]);
		return this;
	}
	
	/**
	 * 
	 * Destroys this shader program releasing any occupied system resources.
	 * 
	 */
	public void destroy() {
		glDeleteProgram(id);
		System.out.println("Destroyed program with ID: " + id);
	}
	
	/**
	 * 
	 * Returns the id of the currently bound shader program.
	 * 
	 * @return the id of the currently bound shader program.
	 */
	public static final int currentlyBoundID() {
		return currentProgram;
	}
}