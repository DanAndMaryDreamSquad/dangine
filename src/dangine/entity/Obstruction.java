package dangine.entity;

import org.newdawn.slick.geom.Vector2f;

import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class Obstruction implements IsUpdateable, HasDrawable {

    SceneGraphNode node = new SceneGraphNode();
    DangineImage image = new DangineImage("wood");

    public Obstruction() {
        node.addChild(image);
        node.setPosition((Utility.getResolution().x - getWidth()) / 2, (Utility.getResolution().y - getHeight()) / 2);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    @Override
    public void update() {

    }

    public boolean isCollidingWithPoint(Vector2f position) {
        return isCollidingWithPoint(position.x, position.y);
    }

    public boolean isCollidingWithPoint(float x, float y) {
        if (x < node.getPosition().x || x > node.getPosition().x + getWidth() || //
                y < node.getPosition().y || y > node.getPosition().y + getHeight()) {
            return false;
        }
        return true;
    }

    public float getWidth() {
        return image.getWidth() * node.getScale().x;
    }

    public float getHeight() {
        return image.getHeight() * node.getScale().y;
    }

    public float topY() {
        return node.getPosition().y - 0.1f;
    }

    public float bottomY() {
        return node.getPosition().y + getHeight() + 0.1f;
    }

    public float leftX() {
        return node.getPosition().x - 0.1f;
    }

    public float rightX() {
        return node.getPosition().x + getWidth() + 0.1f;
    }
}
