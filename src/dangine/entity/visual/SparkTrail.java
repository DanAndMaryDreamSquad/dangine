package dangine.entity.visual;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineParticle;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Utility;

public class SparkTrail implements IsUpdateable, HasDrawable {

    final float MAX_TIME = 1000f;
    final float SPEED = 0.16f;
    float timer = 0;
    SceneGraphNode node = new SceneGraphNode();
    DangineParticle shape = ParticleEffectFactory.createFire(8, 20);
    List<Vector2f> velocities = new ArrayList<Vector2f>();

    public SparkTrail(float x, float y) {
        node.setPosition(x, y);
        node.addChild(shape);
        for (int i = 0; i < shape.getParticles().size(); i++) {
            float angle = (360 / shape.getParticles().size()) * i;
            velocities.add(new Vector2f(angle).scale(SPEED));
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
            offset.x += velocity.x * Utility.getGameTime().getDeltaTimeF();
            offset.y += velocity.y * Utility.getGameTime().getDeltaTimeF();
        }
        if (timer >= MAX_TIME) {
            Utility.getActiveScene().removeUpdateable(this);
            node.removeChild(shape);
            Utility.getActiveScene().getParentNode().removeChild(node);
        }
    }
}