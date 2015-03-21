package dangine.entity.visual;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineParticle;
import dangine.utility.Utility;

public class ExplosionVisual implements IsUpdateable, HasDrawable {

    final float duration;
    final float speed;
    float timer = 0;
    SceneGraphNode node = new SceneGraphNode();
    final DangineParticle shape;
    List<Vector2f> velocities = new ArrayList<Vector2f>();

    public ExplosionVisual(float x, float y, DangineParticle particles, float minAngle, float maxAngle, float speed,
            float duration) {
        this.shape = particles;
        this.speed = speed;
        this.duration = duration;
        node.setPosition(x, y);
        node.addChild(shape);
        for (int i = 0; i < shape.getParticles().size(); i++) {
            float range = maxAngle - minAngle;
            float angleOffset = (range / shape.getParticles().size()) * i;
            float angle = angleOffset + minAngle;
            velocities.add(new Vector2f(angle).scale(speed));
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        for (int i = 0; i < shape.getParticles().size(); i++) {
            Vector2f velocity = velocities.get(i);
            Vector2f offset = shape.getParticles().get(i).getOffset();
            float alpha = 1.0f - (timer / duration);
            shape.getParticles().get(i).getColor().a = alpha;
            offset.x += velocity.x * Utility.getGameTime().getDeltaTimeF();
            offset.y += velocity.y * Utility.getGameTime().getDeltaTimeF();
        }
        if (timer >= duration) {
            Utility.getActiveScene().removeUpdateable(this);
            node.removeChild(shape);
            Utility.getActiveScene().getCameraNode().removeChild(node);
        }
    }
}