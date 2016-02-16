package apie.level;

import apie.debug.Debug;
import apie.gfx.QuadRenderer;
import apie.gfx.texture.Texture;
import apie.physics.Body;

public class ScriptEnvironment {
	
	public static final int TYPE_LEVEL = 0;
	public static final int TYPE_ASSET = 1;
	
	public final int TYPE;
	
	private Level level;
	private Asset asset;
	
	public ScriptEnvironment(Asset asset) {
		TYPE = TYPE_ASSET;
		this.asset = asset;
		this.level = asset.getLevel();
	}
	
	public ScriptEnvironment(Level level) {
		TYPE = TYPE_LEVEL;
		this.level = level;
	}
	
	public void make(String asset, String uniqueID, String x, String y, String params) {
		Asset a = new Asset(level, uniqueID, asset, params);
		a.setPosition(Float.parseFloat(x), Float.parseFloat(y));
		level.assetManager.addEntity(uniqueID, a);
	}
	
	public void log(String text) {
		StringBuilder sb = new StringBuilder();
		for(String s : text.split("[|]+"))
			sb.append(s).append(" ");
		
		if(TYPE == TYPE_LEVEL) {
			System.out.println("[" + level.name + "]: " + sb.subSequence(0, sb.length() - 1));
		} else if(TYPE == TYPE_ASSET) {
			System.out.println("[" + asset.name + "|" + asset.uniqueID + "]: " + sb.subSequence(0, sb.length() - 1));
		}
	}
	
	public void define(String type, String params) {
		
		String[] p = params.split("[|]+");
		
		if(TYPE == TYPE_ASSET)
			for(int i = 0; i < p.length; i++)
				if(p[i].matches("p[0-9]+"))
					p[i] = asset.params[Integer.parseInt(p[i].substring(1))];
		
		switch(type) {
		
		//Unknown definer
		default:
			System.err.println("Undefined: \"" + type + "\"");
			break;
		
		/*
		 * 
		 * Definers for levels and assets
		 * 
		 */
		
		//Define name
		case "name":
			if(TYPE == TYPE_LEVEL)
				level.name = p[0];
			else if(TYPE == TYPE_ASSET)
				asset.name = p[0];
			break;
		
		/*
		 * 
		 * Definers for levels only
		 * 
		 */
		
		//Define gravity
		
		case "gravity":
			if(TYPE == TYPE_LEVEL)
				level.pe.setGravity(Float.parseFloat(p[0]), Float.parseFloat(p[1]));
			else if(TYPE == TYPE_ASSET)
				System.err.println("Cannot define gravity for an asset");
			break;
		
		/*
		 * 
		 * Definers for assets only
		 * 
		 */
		
		//Define renderer
		case "renderer":
			if(TYPE == TYPE_LEVEL)
				System.err.println("Cannot define renderer for a level");
			else if(TYPE == TYPE_ASSET)
				asset.setRenderer(new QuadRenderer(asset.getX(), asset.getY(),
						Float.parseFloat(p[0]), Float.parseFloat(p[1]), Texture.DEFAULT_TEXTURE)
				);
			break;
		
		//Define texture
		case "texture":
			if(TYPE == TYPE_LEVEL)
				System.err.println("Cannot define renderer for a level");
			else if(TYPE == TYPE_ASSET)
				asset.getRenderer().setTexture(new Texture(p[0]));
			break;
			
		//Define static
		case "static":
			if(TYPE == TYPE_LEVEL)
				System.err.println("Cannot define static for a level");
			else if(TYPE == TYPE_ASSET) {
				asset.setBody(new Body(asset.transform, Float.parseFloat(p[0]), Float.parseFloat(p[1])).setAbsolute(true));
				level.pe.addBody(asset.getBody());
			}
			break;
		
		//Define body
		case "body":
			if(TYPE == TYPE_LEVEL)
				System.err.println("Cannot define body for a level");
			else if(TYPE == TYPE_ASSET) {
				asset.setBody(new Body(asset.transform,
						Float.parseFloat(p[0]), Float.parseFloat(p[1]))
						.setTrigger(Boolean.parseBoolean(p[2]))
				);
				level.pe.addBody(asset.getBody());
			}
			break;
		
		//Define mass
		case "mass":
			if(TYPE == TYPE_LEVEL)
				System.err.println("Cannot define mass for a level");
			else if(TYPE == TYPE_ASSET)
				asset.getBody().setMass(Float.parseFloat(p[0]));
			break;
		
		//Define mass
		case "friction":
			if(TYPE == TYPE_LEVEL)
				System.err.println("Cannot define friction for a level");
			else if(TYPE == TYPE_ASSET)
				asset.getBody().setFriction(Float.parseFloat(p[0]));
			break;
		
		//Define bounce
		case "bounce":
			if(TYPE == TYPE_LEVEL)
				System.err.println("Cannot define bounce for a level");
			else if(TYPE == TYPE_ASSET)
				asset.getBody().setEpsilon(Float.parseFloat(p[0]));
			break;
		}
			
	}
}