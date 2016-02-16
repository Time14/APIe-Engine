package apie.audio;

import java.io.File;
import java.util.HashMap;

import apie.debug.Debug;

public final class AudioLibrary {
	
//	public static final AudioClip DEFAULT_TEXTURE = new AudioClip();
	
	private static final HashMap<String, Audio> audioClips = new HashMap<>();
	
	/**
	 * Adds an audio to the library.
	 * @param name - The name of the audio.
	 * @param audio - The loaded audio object.
	 */
	public static final void registerAudio(String name, Audio audio) {
		audioClips.put(name, audio);
	}
	
	/**
	 * Trys to load all the from the sub folders of "res/audio".
	 * The names are generated from the name of the file, there can be collisions.
	 */
	public static final void tryLoadingFiles() {
	    File[] files = new File("res/audio/").listFiles();
	    showFiles(files);
	}

	private static void showFiles(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            Debug.log("Directory: " + file.getName());
	            showFiles(file.listFiles()); // Calls same method again.
	        } else {
	            Debug.log("File: " + file.getName());
	        }
	    }
	}
	
	
	/**
	 * Returns the audio with the supplied name.
	 * @param name - The name of the audio you want to fetch.
	 * @return - The preloaded audio.
	 */
	public static final Audio getAudio(String name) {
		Audio audio = audioClips.get(name);
		
		if(audio == null) {
			AudioManager.destroy(1);
			throw new IllegalArgumentException("Unregistered sound \"" + name + "\"");
		}
			
		return audio;
	}
	
	/**
	 * Clears out the library to free up allocated resources.
	 */
	public static final void destroy() {
		for(Audio audio : audioClips.values())
			audio.destroy();
	}
}