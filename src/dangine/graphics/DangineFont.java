package dangine.graphics;

import org.lwjgl.util.Point;

public class DangineFont {

    public final static char[] ALPHABET = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', //
            '!', '?', ':', '.', //
            '-', '_', '<', '>', ',', '%', //
            ' ', ' ', ' ', ' ', ' ', ' ', //
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
    public final static String FONT_NAME = "starfont2";
    public final static int CHARACTER_WIDTH_IN_PIXELS = 10;
    public final static int CHARACTER_HEIGHT_IN_PIXELS = 10;
    public final static int CHARACTERS_PER_ROW_IN_FONT_FILE = 6;

    public static CharacterCoordinates getCoordinatesOfCharacter(char c) {
        Point rowAndColumn = getRowAndColumnOfCharacter(c);
        Point topLeft = new Point(rowAndColumn.getX() * CHARACTER_WIDTH_IN_PIXELS, rowAndColumn.getY()
                * CHARACTER_HEIGHT_IN_PIXELS);
        CharacterCoordinates coordinates = new CharacterCoordinates(topLeft);
        return coordinates;

    }

    public static Point getRowAndColumnOfCharacter(char c) {
        int indexInAlphabet = getIndexOfCharacter(c);
        int rowOfCharacterInFontFile = indexInAlphabet / CHARACTERS_PER_ROW_IN_FONT_FILE;
        int columnOfCharacterInFontFile = indexInAlphabet % CHARACTERS_PER_ROW_IN_FONT_FILE;
        return new Point(columnOfCharacterInFontFile, rowOfCharacterInFontFile);
    }

    public static int getIndexOfCharacter(char c) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == c) {
                return i;
            }
        }
        return 0;
    }

    public static int getLengthInPixels(String phrase) {
        return Math.round(phrase.length() * DangineStringPicture.STRING_SCALE * DangineFont.CHARACTER_WIDTH_IN_PIXELS
                * DangineOpenGL.getWindowWorldAspectX());
    }

}
