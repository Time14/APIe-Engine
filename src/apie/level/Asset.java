package apie.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import apie.entity.Entity;

public class Asset extends Entity {
	
	private Level level;
	
	protected String name;
	
	protected String uniqueID;
	
	private ScriptEnvironment scriptEnvironment;
	
	protected String[] params;
	
	public Asset(Level level, String uniqueID, String path, String params) {
		this.level = level;
		this.uniqueID = uniqueID;
		loadAsset(path, params);
	}
	
	public void loadAsset(String path, String params) {
		scriptEnvironment = new ScriptEnvironment(this);
		
		this.params = params.split("[|]+");
		
		int row = -1;
		try (Scanner s = new Scanner(new File(path))) {
			
			while(s.hasNextLine()) {
				row++;
				
				String line = s.nextLine().replace("\t", "");
				
				if(line.replace(" ", "").length() == 0 || line.startsWith("#"))
					continue;
				
				String[] data = line.split("> +");
				String name = data[0];
				Object[] p = data[1].split(" +");
				
				Class<?>[] types = new Class<?>[p.length];
				
				for(int i = 0; i < types.length; i++) {
					types[i] = String.class;
				}
				
				try {
					Method m = scriptEnvironment.getClass().getMethod(name, types);
					m.invoke(scriptEnvironment, p);
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
	
	public Level getLevel() {
		return level;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}
}