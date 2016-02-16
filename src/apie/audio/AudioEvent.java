package apie.audio;

public class AudioEvent {
	public static final int EVENT_PLAY = 0;
	public static final int EVENT_PAUSE = 1;
	public static final int EVENT_STOP = 2;
	public static final int EVENT_PLAY_FADE = 3;
	public static final int EVENT_PAUSE_FADE = 4;
	public static final int EVENT_STOP_FADE = 5;
	public static final int EVENT_FADE_GAIN = 6;
	public static final int EVENT_FADE_PITCH = 7;
	
	public final Audio AUDIO;
	public final int EVENT;
	public final float[] PARAMS;
	public final boolean LOOP;
	
	public AudioEvent(Audio audio, boolean loop, int event, float... params) {
		AUDIO = audio;
		EVENT = event;
		PARAMS = params;
		LOOP = loop;
	}
}