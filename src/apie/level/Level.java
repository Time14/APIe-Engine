package apie.level;

import java.beans.MethodDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import apie.debug.Debug;
import apie.entity.EntityManager;
import apie.physics.PhysicsEngine;
import apie.util.Loader;

public class Level {
	
	protected String name;
	
	protected EntityManager assetManager;
	
	private ScriptEnvironment scriptEnvironment;
	
	protected PhysicsEngine pe;
	
	public Level(String path) {
		loadLevel(path);
	}
	
	public void loadLevel(String path) {
		
		assetManager = new EntityManager();
		
		scriptEnvironment = new ScriptEnvironment(this);
		
		pe = new PhysicsEngine();
//		
//		String source = Loader.loadSource(path);
//		System.out.println(source);
//		try {
//			String cmd = "";
//			for(int i = 0; i < source.length(); i++) {
//				char c = source.charAt(i);
//				switch(c) {
//				
//				
//				
//				default:
//					cmd += c;
//					break;
//				
//				
//				case '>':
//					
//					String name = cmd.toString();
//					
//					ArrayList<String> args = new ArrayList<>();
//					i++;
//					cmd = "";
//					while(source.charAt(++i) != '\n') {
//						if(source.charAt(i) == ' ') {
//							args.add(cmd.toString());
//							cmd = "";
//						} else {
//							cmd += source.charAt(i);
//						}
//					}
//					
//					args.add(cmd);
//					cmd = "";
//					
//					execCmd(name, args);
//					break;
//					
//				case '\n':
//					cmd = "";
//					break;
//				}
//			}
//		} catch (StringIndexOutOfBoundsException e) {}
		
		int row = -1;
		try (Scanner s = new Scanner(new File(path))) {
			
			while(s.hasNextLine()) {
				row++;
				
				String line = s.nextLine().replace("\t", "");
				
				if(line.replace(" ", "").length() == 0 || line.startsWith("#"))
					continue;
				
				String[] data = line.split("> +");
				String name = data[0];
				Object[] params = data[1].split(" +");
				
				Class<?>[] types = new Class<?>[params.length];
				
				for(int i = 0; i < types.length; i++) {
					types[i] = String.class;
				}
				
				try {
					Method m = scriptEnvironment.getClass().getMethod(name, types);
					m.invoke(scriptEnvironment, params);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private int execCmd(String name, ArrayList<String> args) {
		Debug.log(name, args.toString());
		return 0;
	}
	
	public Asset getAsset(String uniqueID) {
		return (Asset)assetManager.getEntity(uniqueID);
	}
	
	public void update(float delta) {
		pe.update(delta);
		assetManager.update(delta);
	}
	
	public void draw() {
		assetManager.draw();
	}
	
	protected ScriptEnvironment getScriptEnvironment() {
		return scriptEnvironment;
	}
	
	public String getName() {
		return name;
	}
}