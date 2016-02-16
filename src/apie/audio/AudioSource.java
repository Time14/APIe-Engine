package apie.audio;

import org.lwjgl.openal.AL10;

import apie.util.Util;

public class AudioSource {
	
	private int id;
	
	private float gain = 1f, pitch = 1f;
	
	private float targetPitch = 1f, targetGain = 1f;
	
	private float deltaPitch, deltaGain;
	
	private boolean loop;
	
	//true will stop on zero gain and false will pause
	private boolean stop;
	
	/**
	 * Initializes the Audio Source and allocates memory for it.
	 * @param loop - Weather it should loop the supplied sound or not. 
	 */
	public AudioSource(boolean loop) {
		id = AL10.alGenSources();
		this.loop = loop;
	}
	
	/**
	 * Play the supplied audio with the preset settings. 
	 * @param audio - The sound you want to play.
	 */
	public void play(Audio audio) {
		
		AL10.alSourcei(id, AL10.AL_BUFFER, audio.getID());
		AL10.alSourcef(id, AL10.AL_PITCH, pitch);
		AL10.alSourcef(id, AL10.AL_GAIN, gain);
		AL10.alSourcef(id, AL10.AL_MAX_GAIN, 1);
		AL10.alSourcef(id, AL10.AL_MIN_GAIN, 0);
		AL10.alSourcefv(id, AL10.AL_POSITION, Util.toFloatBuffer(new float[]{0, 0, 0}));
		AL10.alSourcefv(id, AL10.AL_VELOCITY, Util.toFloatBuffer(new float[]{0, 0, 0}));
		AL10.alSourcei(id, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
		
		AL10.alSourcePlay(id);
	}
	
	/**
	 * Update the source to adjust the gain and pitch.
	 * @param tick- The delta time of this update.
	 */
	public void update(double tick) {
		adjustGain(tick);
		adjustPitch(tick);
	}
	
	/**
	 * Smoothly fade the volume.
	 * @param tick - The delta time of this update.
	 */
	private void adjustGain(double tick) {
		if(gain < targetGain) {
			addGain((float)tick * deltaGain);
			if(gain >= targetGain)
				setGain(targetGain);
		} else if(gain > targetGain) {
			addGain((float)tick * deltaGain);
			if(gain <= targetGain) {
				setGain(targetGain);
				if(gain == 0) {
					if(stop)
						stop();
					else
						pause();
				}
			}
		}
	}
	
	/**
	 * Smoothly fade the pitch.
	 * @param tick - The delta time of this update.
	 */
	private void adjustPitch(double tick) {
		if(pitch < targetPitch) {
			addPitch((float)tick * deltaPitch);
			if(pitch >= targetPitch)
				setPitch(targetPitch);
		} else if(pitch > targetPitch) {
			addPitch((float)tick * deltaPitch);
			if(pitch <= targetPitch)
				setPitch(targetPitch);
		}
	}
	
	/**
	 * Pause this source.
	 */
	public void pause() {
		AL10.alSourcePause(id);
	}
	
	/**
	 * Stop this source.
	 */
	public void stop() {
		AL10.alSourceStop(id);
	}
	
	/**
	 * Start fading the volume of this source to the supplied target.
	 * @param target - The volume you want after the fade.
	 * @param time - The time it should take to fade.
	 */
	public void fadeGain(float target, float time) {
		targetGain = target;
		deltaGain = target - gain;
		
		if(time == 0) {
			setGain(target);
			return;
		}
		
		deltaGain /= time;
	}
	
	/**
	 * Start fading the pitch of this source to the supplied target.
	 * @param target - The pitch you want after the fade.
	 * @param time - The time it should take to fade.
	 */
	public void fadePitch(float target, float time) {
		targetPitch = target;
		deltaPitch = target - pitch;
		
		if(time == 0) {
			setPitch(target);
			return;
		}
		
		deltaPitch /= time;
	}
	
	/**
	 * Add a the supplied value to the current volume.
	 * @param gain - How much you want to increase the volume.
	 */
	public void addGain(float gain) {
		this.gain += gain;
		AL10.alSourcef(id, AL10.AL_GAIN, this.gain);
	}
	
	/**
	 * Add a the supplied value to the current pitch.
	 * @param gain - How much you want to increase the pitch.
	 */
	public void addPitch(float pitch) {
		this.pitch += pitch;
		AL10.alSourcef(id, AL10.AL_PITCH, this.pitch);
	}
	
	/**
	 * Set the volume to the supplied value.
	 * @param gain - The volume you want.
	 */
	public void setGain(float gain) {
		this.gain = gain;
		targetGain = gain;
		AL10.alSourcef(id, AL10.AL_GAIN, gain);
	}
	
	/**
	 * Set the pitch to the supplied value.
	 * @param pitch - The pitch you want.
	 */
	public void setPitch(float pitch) {
		this.pitch = pitch;
		targetPitch = pitch;
		AL10.alSourcef(id, AL10.AL_PITCH, pitch);
	}
	
	/**
	 * If the source should loop the audio.
	 * @param loop - True if you want to loop the audio, false otherwise. 
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	/**
	 * Returns if this source currently is playing audio.
	 * @return - If this source is playing something.
	 */
	public boolean isPlaying() {
		return AL10.alGetSourcei(id, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	/**
	 * If the source should stop the audio when it fades to 0 volume.
	 * @param stop - True if you want to stop the audio when it reaches 0 volume, false if you want to pause it.
	 */
	public void setZeroStop(boolean stop) {
		this.stop = stop;
	}
	
	/**
	 * I'm going to let you guess if this makes your computer explode into a fiery ball of death or just cleans up after this audio source.
	 */
	public void destroy() {
		AL10.alDeleteSources(id);
	}
}