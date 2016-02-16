package apie.audio;

import java.util.ArrayList;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

import apie.util.Util;


public class AudioHandler implements Runnable {
	
	public static final int UPS = 60;
	public static final double TICK = 1d/UPS; 
	
	
	private volatile ArrayList<AudioEvent> tempQueue;
	private volatile ArrayList<AudioEvent> loopQueue;
	
	volatile boolean running = true;
	
	private ALContext context;
	private ALDevice device;

	private AudioSource[] loopSources;
	private AudioSource[] tempSources;

	private int error = 0;
	
	
	/**
	 * Starts and keeps running the audio thread.
	 */
	public void run() {
		try {
			init();
			
			while(running)
				loop();
			
			destroy(error);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loop() {
		
		for(AudioSource a : loopSources)
			a.update(TICK);
		
		for(AudioSource a : tempSources)
			a.update(TICK);
		
		handleEvents();
		
		try {
			Thread.sleep((long) (1000 * TICK));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void handleEvents() {
		for(AudioEvent ae : tempQueue) {
			boolean terminate = false;
			for(int i = 0; i < AudioManager.MAX_TEMP_SOURCES; i++) {
				if(!tempSources[i].isPlaying()) {
					switch(ae.EVENT) {
					case AudioEvent.EVENT_PLAY:
						tempSources[i].setGain(ae.PARAMS[0]);
						tempSources[i].setPitch(ae.PARAMS[1]);
						tempSources[i].play(ae.AUDIO);
						break;
					case AudioEvent.EVENT_PLAY_FADE:
						tempSources[i].setGain(0);
						tempSources[i].setPitch(ae.PARAMS[1]);
						tempSources[i].play(ae.AUDIO);
						tempSources[i].fadeGain(ae.PARAMS[0], ae.PARAMS[2]);
						break;
					case AudioEvent.EVENT_PAUSE:
						tempSources[i].pause();
						break;
					case AudioEvent.EVENT_PAUSE_FADE:
						tempSources[i].fadeGain(0, ae.PARAMS[0]);
						break;
					case AudioEvent.EVENT_STOP:
						tempSources[i].stop();
						break;
					case AudioEvent.EVENT_STOP_FADE:
						tempSources[i].fadeGain(0, ae.PARAMS[0]);
						break;
					}
					
					break;
				} else if(i >= AudioManager.MAX_TEMP_SOURCES - 1) {
					terminate = true;
					break;
				}
			}
			
			if(terminate)
				break;
		}
		
		tempQueue.clear();
		
		for(AudioEvent ae : loopQueue) {
			int source = (int)ae.PARAMS[0];
			switch(ae.EVENT) {
			case AudioEvent.EVENT_PLAY:
				loopSources[source].setGain(ae.PARAMS[1]);
				loopSources[source].setPitch(ae.PARAMS[2]);
				loopSources[source].play(ae.AUDIO);
				break;
			case AudioEvent.EVENT_PLAY_FADE:
				loopSources[source].setGain(0);
				loopSources[source].setPitch(ae.PARAMS[2]);
				loopSources[source].play(ae.AUDIO);
				loopSources[source].fadeGain(ae.PARAMS[1], ae.PARAMS[3]);
				break;
			case AudioEvent.EVENT_PAUSE:
				loopSources[source].pause();
				break;
			case AudioEvent.EVENT_PAUSE_FADE:
				loopSources[source].setZeroStop(false);
				loopSources[source].fadeGain(0, ae.PARAMS[1]);
				break;
			case AudioEvent.EVENT_STOP:
				loopSources[source].stop();
				break;
			case AudioEvent.EVENT_STOP_FADE:
				loopSources[source].setZeroStop(true);
				loopSources[source].fadeGain(0, ae.PARAMS[1]);
				break;
			case AudioEvent.EVENT_FADE_GAIN:
				loopSources[source].fadeGain(ae.PARAMS[1], ae.PARAMS[2]);
				break;
			case AudioEvent.EVENT_FADE_PITCH:
				loopSources[source].fadePitch(ae.PARAMS[1], ae.PARAMS[2]);
				break;
			}
		}
		
		loopQueue.clear();
	}

	private void init() throws Exception {
		try {
			context = ALContext.create();
			device = context.getDevice();
			
			tempQueue = new ArrayList<>();
			loopQueue = new ArrayList<>();
			
			loopSources = new AudioSource[AudioManager.MAX_LOOP_SOURCES];
			
			for(int i = 0; i < loopSources.length; i++)
				loopSources[i] = new AudioSource(true);
			
			tempSources = new AudioSource[AudioManager.MAX_TEMP_SOURCES];
			
			for(int i = 0; i < tempSources.length; i++)
				tempSources[i] = new AudioSource(false);
			
			AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
			AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
			AL10.alListenerfv(AL10.AL_ORIENTATION, Util.toFloatBuffer(new float[]{0, 0, -1, 0, 1, 0}));
			
			System.out.println("OpenAL v." + AL10.alGetInteger(AL10.AL_VERSION));
		} catch (Exception e) {
			System.err.println("There is no audio device connected, playing audio is impossible and will not work.");
		}
	}
	
	/**
	 * Queues up the audio event so it will be handled.
	 * @param event - The event you want to call.
	 */
	public synchronized void queue(AudioEvent... event) {
		for(AudioEvent a : event) {
			if(a.LOOP)
				loopQueue.add(a);
			else
				tempQueue.add(a);
		}
	}
	
	/**
	 * Tells the audio manger there was an error somewhere, scary things.
	 * @param error - The Al-ID of the error.
	 */
	public synchronized void setError(int error) {
		this.error = error;
	}
	
	/**
	 * Cleans up the audio handle, and makes sure everything else is cleaned up.
	 * @param error
	 */
	public synchronized void destroy(int error) {
		AudioLibrary.destroy();
		try {
			context.destroy();
	    	device.destroy();
		} catch(Exception e) {}
		
		if(error == 0)
			System.out.println("Successfully destroyed AudioManager");
		else
			System.err.println("Destroyed AudioManager with errors! (" + error + ")");
	}
}