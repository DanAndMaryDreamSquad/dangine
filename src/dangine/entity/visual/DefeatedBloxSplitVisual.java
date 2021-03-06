package dangine.entity.visual;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.FlattenSceneGraphNode;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class DefeatedBloxSplitVisual implements IsUpdateable, HasDrawable {

    final float MAX_TIMER = 5000f;
    final float MOVE_SPEED = 0.020f;
    final float ROTATION_SPEED = 0.50f;
    final SceneGraphNode base = new SceneGraphNode();
    final List<SceneGraphNode> parts = new ArrayList<SceneGraphNode>();
    final List<Vector2f> velocities = new ArrayList<Vector2f>();
    final Vector2f position;
    float timer = 0;
    boolean delay = false;
    float delayTimer = 0;

    public DefeatedBloxSplitVisual(float x, float y, SceneGraphNode node) {
        position = new Vector2f(x, y);
        if (node != null) {
            SceneGraphNode toFlat = node;
            SceneGraphNode flat = FlattenSceneGraphNode.flatten(toFlat);
            for (int i = 0; i < flat.getChildNodes().size(); i++) {
                parts.add(flat.getChildNodes().get(i));
                float velAngle = (360 / flat.getChildNodes().size()) * i;
                Vector2f vel = new Vector2f(velAngle);
                vel.scale(MOVE_SPEED);
                velocities.add(vel);
            }
            base.addChild(flat);
        }
    }

    public DefeatedBloxSplitVisual withDelay() {
        delay = true;
        return this;
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    @Override
    public void update() {
        if (delay) {
            delayTimer += Utility.getGameTime().getDeltaTimeF();
            if (delayTimer > BloxFreezeVisual.MAX_TIME) {
                delay = false;
                Utility.getActiveScene().getCameraNode().addChild(this.getDrawable());
            }
            return;
        }
        timer += Utility.getGameTime().getDeltaTimeF();
        for (int i = 0; i < parts.size(); i++) {
            SceneGraphNode part = parts.get(i);
            Vector2f velocity = velocities.get(i);
            Vector2f position = part.getPosition();
            position.x += velocity.x * Utility.getGameTime().getDeltaTimeF();
            position.y += velocity.y * Utility.getGameTime().getDeltaTimeF();
        }

        if (timer > MAX_TIMER) {
            Utility.getActiveScene().getCameraNode().removeChild(this.getDrawable());
            Utility.getActiveScene().removeUpdateable(this);
        }
    }

    public Vector2f getPosition() {
        return position;
    }
}
