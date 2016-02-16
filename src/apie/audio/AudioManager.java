package apie.audio;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

import apie.audio.AudioLibrary;
import apie.util.Util;

public final class AudioManager {

	public static final int MAX_SOURCES = 256;
	public static final int MAX_LOOP_SOURCES = 10;
	public static final int MAX_TEMP_SOURCES = MAX_SOURCES - MAX_LOOP_SOURCES;

	private static AudioHandler audioHandler;
	private static Thread thread;

	/**
	 * Initializes the audio manager
	 */
	public static final synchronized void start() {

		audioHandler = new AudioHandler();
		thread = new Thread(audioHandler, "Audio Handler");

		thread.start();
	}

	public static final synchronized Thread getThread() {
		return thread;
	}

	/**
	 * Fades the volume to the target during the time supplied, for all sources. 
	 * @param target The desired volume after the fade.
	 * @param duration The time the fade should take.
	 * @param source Which sources you want this to affect. 
	 */
	public static final synchronized void fadeLoopGain(float target, 
			float duration, int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_FADE_GAIN, new float[] { s, target,
							duration }));
	}
	
	/**
	 * Fades the pitch to the target during the time supplied, for all sources. 
	 * @param target The desired pitch after the fade.
	 * @param duration The time the fade should take.
	 * @param source Which sources you want this to affect. 
	 */
	public static final synchronized void fadeLoopPitch(float target,
			float duration, int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_FADE_PITCH, new float[] { s, target,
							duration }));
	}
	
	/**
	 * Sets the volume of the supplied sources.
	 * @param target The volume you want.
	 * @param source Which sources you want to affect.
	 */
	public static final synchronized void setLoopGain(float target, 
			int... source) {
		fadeLoopGain(target, 0, source);
	}

	/**
	 * Sets the pitch of the supplied sources.
	 * @param target The pitch you want.
	 * @param source Which sources you want to affect.
	 */
	public static final synchronized void setLoopPitch(float target,
			int... source) {
		fadeLoopPitch(target, 0, source);
	}

	// Instant Stop
	// public static final synchronized void stop(String... audio) {
	// for(String s : audio)
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_STOP));
	// }

	// Instant Loop Stop
	/**
	 * Stop the audio playing from the supplied sources.
	 * @param source Which sources you want to affect.
	 */
	public static final synchronized void stopLoop(int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_STOP, new float[] { s }));
	}

	// Interpolated Stop
	// public static final synchronized void stop(float duration, String...
	// audio) {
	// for(String s : audio) {
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_STOP_FADE, new float[]{duration}));
	// }
	// }

	// Interpolated Loop Stop
	/**
	 * Fade out the audio and then stop the audio.
	 * @param duration How long the fade should be.
	 * @param source Which sources you want to affect.
	 */
	public static final synchronized void stopLoop(float duration,
			int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_STOP_FADE, new float[] { s, duration }));
	}

	// Instant Pause
	// public static final synchronized void pause(String... audio) {
	// for(String s : audio)
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_PAUSE));
	// }

	// Instant Loop Pause
	/**
	 * Pause the audio from the supplied source.
	 * @param source The source you want to pause.
	 */
	public static final synchronized void pauseLoop(int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_PAUSE, new float[] { s }));
	}

	// Interpolated Pause
	// public static final synchronized void pause(float duration, String...
	// audio) {
	// for(String s : audio) {
	// audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s),
	// false, AudioEvent.EVENT_PAUSE_FADE, new float[]{duration}));
	// }
	// }

	// Interpolated Loop Pause
	/**
	 * Fade out the loop and then pause it, on all the supplied sources.
	 * @param duration The time it takes to fade out.
	 * @param source Which sources you want to affect.
	 */
	public static final synchronized void pauseLoop(float duration,
			int... source) {
		for (int s : source)
			audioHandler.queue(new AudioEvent(null, true,
					AudioEvent.EVENT_PAUSE_FADE, new float[] { s, duration }));
	}

	// Interpolated Play
	/**
	 * Play all the supplied sounds with the set volume and pitch. 
	 * @param gain The volume you want to play the sound with. 
	 * @param pitch The pitch you want to play the sound with. 
	 * @param duration The time it should take to fade to the desired volume.
	 * @param audio All the names of the sounds you want to play in the library.
	 */
	public static final synchronized void play(float gain, float pitch,
			float duration, String... audio) {
		for (String s : audio) {
			audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s), false,
					AudioEvent.EVENT_PLAY_FADE, new float[] { gain, pitch, duration }));
		}
	}

	// Interpolated Loop Play
	/**
	 * Plays audio from the supplied source, fades it in and loops it.
	 * @param source The source you want to loop the sound.
	 * @param gain The volume you want to fade to.
	 * @param pitch The pitch you want the sound to have.
	 * @param duration How long the fade should be.
	 * @param audio The name of the sound you want to play from the library.
	 */
	public static final synchronized void playLoop(int source, float gain,
			float pitch, float duration, String audio) {
		audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(audio), true,
				AudioEvent.EVENT_PLAY_FADE, new float[] { source, gain, pitch,
						duration }));
	}

	// Instant Play
	/**
	 * Play all the supplied sounds with the supplied pitch and volume.
	 * @param gain The volume of the sounds you want to play.
	 * @param pitch The pitch of the sounds you want to play.
	 * @param audio - The names of the sounds you want to play from the library.
	 */
	public static final synchronized void play(float gain, float pitch,
			String... audio) {
		for (String s : audio) {
			audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(s), false,
					AudioEvent.EVENT_PLAY, new float[] { gain, pitch }));
		}
	}

	// Instant Loop Play
	/**
	 * Play and loop the supplied sound.
	 * @param source The source you want to play the sound.
	 * @param gain The volume you want to play the sound with.
	 * @param pitch The pitch you want to play the sound with.
	 * @param audio THe name of the sound you want to play from the library. 
	 */
	public static final synchronized void playLoop(int source, float gain,
			float pitch, String audio) {
		audioHandler.queue(new AudioEvent(AudioLibrary.getAudio(audio), true,
				AudioEvent.EVENT_PLAY, new float[] { source, gain, pitch }));
	}

	/**
	 * "Play time's over motherfucker."
	 * Destroys and cleans up the audio manager so you can't play sounds anymore. 
	 */
	public static final synchronized void destroy() {
		destroy(0);
	}
	
	/**
	 * Destroys and cleans up the audio manager so you can't play sounds anymore. 
	 * Passes on the supplied error to the AudioHandler.
	 * @param error The error code for the error that called this function. 
	 */
	public static final synchronized void destroy(int error) {
		audioHandler.setError(error);
		audioHandler.running = false;
	}

}