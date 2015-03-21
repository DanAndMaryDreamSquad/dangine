package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class DangineParticleData {

    int width;
    int height;
    Color color;
    Vector2f offset;

    public DangineParticleData() {

    }

    public DangineParticleData(int width, int height, Color color, Vector2f offset) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.offset = offset;
    }

    public Vector2f getOffset() {
        return offset;
    }

    public void setOffset(Vector2f offset) {
        this.offset = offset;
    }

    public Color getColor() {
        return color;
    }

}
