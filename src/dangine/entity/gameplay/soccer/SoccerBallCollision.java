package dangine.entity.gameplay.soccer;

import java.util.List;

import dangine.entity.Obstruction;
import dangine.entity.movement.SoccerBallMovement;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.graphics.DangineTextures;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class SoccerBallCollision {

    final float IMPACT_SPARK_THRESHOLD = DangineSavedSettings.INSTANCE.getMaxVelocity();

    public Vector2f checkCollisions(SoccerBallMovement movement, Vector2f currentPosition, Vector2f potentialPosition) {
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
        handleVerticleBounds(movement, currentPosition, potentialPosition);
        handleHorizontalBounds(movement, currentPosition, potentialPosition);
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

    private void handleVerticleBounds(SoccerBallMovement movement, Vector2f currentPosition, Vector2f potentialPosition) {
        float offset = DangineTextures.getImageByName("soccerball").getHeight() * SoccerBall.SCALE;
        if (currentPosition.y > Utility.getResolution().y - offset) {
            potentialPosition.y = Utility.getResolution().y - offset;
        } else if (currentPosition.y < 0) {
            potentialPosition.y = 0;
        }
        if (currentPosition.y > Utility.getResolution().y - offset || currentPosition.y < 0) {
            checkImpactSparks(movement.getVelocity().y, currentPosition.x, currentPosition.y - (offset / 2));
            movement.getVelocity().y = -movement.getVelocity().y;
        }
    }

    private void handleHorizontalBounds(SoccerBallMovement movement, Vector2f currentPosition,
            Vector2f potentialPosition) {
        float offset = DangineTextures.getImageByName("soccerball").getWidth() * SoccerBall.SCALE;
        if (currentPosition.x > Utility.getResolution().x - offset) {
            potentialPosition.x = Utility.getResolution().x - offset;
        } else if (currentPosition.x < 0) {
            potentialPosition.x = 0;
        }
        if (currentPosition.x > Utility.getResolution().x - offset || currentPosition.x < 0) {
            checkImpactSparks(movement.getVelocity().x, currentPosition.x - (offset / 2), currentPosition.y);
            movement.getVelocity().x = -movement.getVelocity().x;
        }
    }

}
