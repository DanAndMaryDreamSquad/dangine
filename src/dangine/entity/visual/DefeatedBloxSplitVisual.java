package dangine.entity.visual;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.FlattenSceneGraphNode;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class DefeatedBloxSplitVisual implements IsUpdateable, HasDrawable {

    final float MAX_TIMER = 5000f;
    final float MOVE_SPEED = 0.020f;
    final float ROTATION_SPEED = 0.50f;
    final SceneGraphNode base = new SceneGraphNode();
    final List<SceneGraphNode> parts = new ArrayList<SceneGraphNode>();
    final List<Vector2f> velocities = new ArrayList<Vector2f>();
    float timer = 0;
    float angle = 0f;

    public DefeatedBloxSplitVisual(float x, float y, float angle, int playerId) {
        Debugger.info();
        if (Utility.getActiveScene().getHero(playerId) != null) {
            SceneGraphNode toFlat = (SceneGraphNode) Utility.getActiveScene().getHero(playerId).getDrawable();
            SceneGraphNode flat = FlattenSceneGraphNode.flatten(toFlat);
            for (int i = 0; i < flat.getChildNodes().size(); i++) {
                parts.add(flat.getChildNodes().get(i));
                float velAngle = (360 / flat.getChildNodes().size()) * i;
                velocities.add(new Vector2f(velAngle).scale(MOVE_SPEED));
            }
            base.addChild(flat);
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    @Override
    public void update() {
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
}
