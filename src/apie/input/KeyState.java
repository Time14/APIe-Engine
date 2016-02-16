package apie.input;
/**
 * An enumerator for all the possible key states a virtual key can have.
 * 
 * <p>
 * UP - The key is up, nothing is happening to the key
 * </p>
 * <p>
 * DOWN - The key is held down
 * </p>
 * <p>
 * RELEASED - The key was released this frame
 * </p>
 * <p>
 * PRESSED - The key was pressed this frame
 * 
 * @author Eddie-Boi
 *
 */
public enum KeyState {
	UP,
	DOWN,
	RELEASED,
	PRESSED;
	
	public enum MetaState {
		NONE,
		DELAYED;
	}
}
