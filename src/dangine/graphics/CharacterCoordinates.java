package dangine.graphics;

import org.lwjgl.util.Point;

public class CharacterCoordinates {
    final Point topLeft;
    final Point bottomRight;

    public CharacterCoordinates(Point topLeft) {
        this.topLeft = topLeft;
        this.bottomRight = new Point(topLeft.getX() + DangineFont.CHARACTER_WIDTH_IN_PIXELS, topLeft.getY()
                + DangineFont.CHARACTER_HEIGHT_IN_PIXELS);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }
}