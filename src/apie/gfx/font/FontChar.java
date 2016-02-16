package apie.gfx.font;

public class FontChar {
	
	public final float T_X, T_Y, T_WIDTH, T_HEIGHT, X_OFFSET, Y_OFFSET, X_ADVANCE;
	
	/**
	 * 
	 * Constructs a new font character with its corresponding information.
	 * 
	 * @param tX - the x coordinate of the character in the texture atlas
	 * @param tY - the y coordinate of the character in the texture atlas
	 * @param tWidth - the width of the character in the texture atlas
	 * @param tHeight - the height of the character in the texture atlas
	 * @param xOffset - the x offset from the cursor position in the texture atlas
	 * @param yOffset - the y offset from the cursor position in the texture atlas
	 * @param xAdvance - how far to advance along the x axis when this character has been drawn
	 */
	public FontChar(int tX, int tY, int tWidth, int tHeight,
			int xOffset, int yOffset, int xAdvance) {
		T_X = tX;
		T_Y = tY;
		T_WIDTH = tWidth;
		T_HEIGHT = tHeight;
		X_OFFSET = xOffset;
		Y_OFFSET = yOffset;
		X_ADVANCE = xAdvance;
	}
}