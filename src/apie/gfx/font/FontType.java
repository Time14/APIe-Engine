package apie.gfx.font;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import apie.gfx.texture.Texture;

public class FontType {
	
	private HashMap<Integer, FontChar> characters;
	
	private Texture texture;
	
	/**
	 * 
	 * Constructs a new font type from the specified FNT file.
	 * 
	 * @param path - the path of the FNT file to load
	 */
	public FontType(String path) {
		String[] paths = path.split("/");
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 1; i < paths.length; i++)
			sb.append(paths[i - 1]).append("/");
		
		loadFont(sb.toString(), paths[paths.length - 1]);
	}
	
	/**
	 * 
	 * Loads the specified FNT file.
	 * 
	 * @param folderPath - the folder path of the FNT file
	 * @param fileName - the file name of the FNT file
	 */
	private void loadFont(String folderPath, String fileName) {
		
		characters = new HashMap<>();
		
		try (Scanner s = new Scanner(new File(folderPath + fileName))) {
			while(s.hasNextLine()) {
				String[] line = s.nextLine().split(" +");
				
				switch(line[0]) {
				case "page":
					texture = new Texture(folderPath + getStringProperty(line[2]));
					break;
				case "char":
					characters.put(getIntProperty(line[1]), new FontChar(getIntProperty(line[2]),
							getIntProperty(line[3]), getIntProperty(line[4]), getIntProperty(line[5]),
							getIntProperty(line[6]), getIntProperty(line[7]), getIntProperty(line[8])));
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Fetches a string from a text property.
	 * 
	 * @param property - the property to fetch from
	 * @return the fetched string
	 */
	private String getStringProperty(String property) {
		return property.split("\\=")[1].replace("\"", "");
	}
	
	/**
	 * 
	 * Fetches an integer from a text property.
	 * 
	 * @param property - the property to fetch from
	 * @return the fetched integer
	 */
	private int getIntProperty(String property) {
		return Integer.parseInt(property.split("\\=")[1]);
	}
	
	/**
	 * 
	 * Returns the font char associated with the ASCII code of the provided char.
	 * 
	 * @param c - the char to fetch a font chat from
	 * @return the fetched font char
	 */
	public final FontChar getChar(char c) {
		return characters.get((int)c);
	}
	
	/**
	 * 
	 * Returns the texture of this font type.
	 * 
	 * @return the texture of this font type
	 */
	public final Texture getTexture() {
		return texture;
	}
	
	public static final FontType FNT_ARIAL = new FontType("res/font/arial.fnt");
	public static final FontType FNT_CHILLER = new FontType("res/font/chiller.fnt");
	public static final FontType FNT_COMIC_SANS = new FontType("res/font/comic_sans.fnt");
	public static final FontType FNT_COURIER_NEW = new FontType("res/font/courier_new.fnt");
	public static final FontType FNT_MAGNETO = new FontType("res/font/magneto.fnt");
	public static final FontType FNT_TIMES_NEW_ROMAN = new FontType("res/font/times_new_roman.fnt");
	public static final FontType FNT_VERDANA = new FontType("res/font/verdana.fnt");
}