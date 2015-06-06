package dangine.entity.movement;

import java.util.List;

import dangine.entity.Obstruction;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class HeroCollision {

    final float IMPACT_SPARK_THRESHOLD = DangineSavedSettings.INSTANCE.getMaxVelocity();

    public Vector2f checkCollisions(HeroMovement movement, Vector2f currentPosition, Vector2f potentialPosition) {
        List<Obstruction> obstructions = Utility.getActiveScene().getObstructions();
        for (Obstruction obs : obstructions) {
            if (obs.isCollidingWithPoint(potentialPosition.x, currentPosition.y)) {
                if (movement.getVelocity().x < 0) {
                    potentialPosition.x = obs.rightX();
                } else if (movement.getVelocity().x > 0) {
                    potentialPosition.x = obs.leftX();
                }
                checkImpactSparks(movement.getVelocity().x, currentPosition.x, currentPosition.y);
                movement.getVelocity().x = 0;
            }
            if (obs.isCollidingWithPoint(currentPosition.x, potentialPosition.y)) {
                if (movement.getVelocity().y < 0) {
                    potentialPosition.y = obs.bottomY();
                } else if (movement.getVelocity().y > 0) {
                    potentialPosition.y = obs.topY();
                }
                checkImpactSparks(movement.getVelocity().y, currentPosition.x, currentPosition.y);
                movement.getVelocity().y = 0;
            }
        }
        return potentialPosition;
    }

    private void checkImpactSparks(float velocity, float x, float y) {
        if (Math.abs(velocity) >= IMPACT_SPARK_THRESHOLD) {
            createVisualEffect(x, y);
        }
    }

    private void createVisualEffect(float x, float y) {
        DanginePictureParticle particle = ParticleEffectFactory.create(8, 8, ParticleEffectFactory.energyColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

}
