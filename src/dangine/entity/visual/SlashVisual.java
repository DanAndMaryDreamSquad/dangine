package dangine.entity.visual;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class SlashVisual implements IsUpdateable, HasDrawable {
    final float DELAY = 5f;
    final float MAX_TIME = 7f;
    final float SPEED = 75.0f;
    float timer = 0;
    float scaleY = 1.0f;
    float x, y;
    float offsetY = -150.0f;
    boolean active = false;
    SceneGraphNode node = new SceneGraphNode();
    SceneGraphNode child = new SceneGraphNode();
    // DangineBox box = new DangineBox(5, 100, new Color(Color.WHITE));
    DanginePicture box = new DanginePicture("slashline");
    List<Vector2f> velocities = new ArrayList<Vector2f>();

    public SlashVisual(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        node.setPosition(x, y);
        node.addChild(child);
        node.setAngle(angle);
        node.setZValue(-50f);
        child.setPosition(0, offsetY);
        child.setScale(1.0f, 10.0f);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > DELAY && !active) {
            child.addChild(box);
            active = true;
        }
        if (active) {
            scaleY = scaleY + Utility.getGameTime().getDeltaTimeF() * SPEED;
            offsetY = offsetY + Utility.getGameTime().getDeltaTimeF() * SPEED;
            child.setPosition(0, offsetY);
        }
        if (timer >= MAX_TIME) {
            Utility.getActiveScene().removeUpdateable(this);
            node.removeChild(box);
            Utility.getActiveScene().getCameraNode().removeChild(node);
        }

    }
}
