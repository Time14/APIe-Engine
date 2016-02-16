package apie.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Loader {
	
	/**
	 * 
	 * Loads text from the specified file path.
	 * 
	 * @param path - the path of the file to load
	 * @return the loaded text
	 */
	public static final String loadSource(String path) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(path));
			StringBuilder source = new StringBuilder();
			
			while(scanner.hasNextLine()) {
				source.append(scanner.nextLine());
				if(scanner.hasNextLine())
					source.append("\n");
			}
			
			scanner.close();
			
			return source.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * Loads a buffered image from the specified path.
	 * 
	 * @param path - the path of the image file to load
	 * @return the loaded buffered image
	 * @throws IOException if the image could not be loaded
	 */
	public static final BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}
}