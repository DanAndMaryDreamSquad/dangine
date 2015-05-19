package dangine.entity;

import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class Obstruction implements IsUpdateable, HasDrawable {

    SceneGraphNode node = new SceneGraphNode();
    DanginePicture image = new DanginePicture("wood");

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

    public void setPosition(float x, float y) {
        node.setPosition(x, y);
    }

    public void setCenterPosition(float x, float y) {
        node.setPosition(x - (image.getWidth() * 0.5f), y - (image.getHeight() * 0.5f));
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
