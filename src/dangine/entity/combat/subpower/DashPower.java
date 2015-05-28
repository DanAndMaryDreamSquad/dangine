package dangine.entity.combat.subpower;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class DashPower {

    float timer = 0;
    final float MAX_TIME = 3000f;
    boolean createdReadyEffect = false;

    public boolean canDash() {
        return timer > MAX_TIME;
    }

    public void update(DangineSampleInput input, HeroMovement movement, Vector2f position) {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > MAX_TIME && !createdReadyEffect) {
            SoundPlayer.play(SoundEffect.DASH_READY);
            createDashReadyEffect(position.x, position.y);
            createdReadyEffect = true;
        }
        if (input.isButtonTwo() && timer > MAX_TIME) {
            SoundPlayer.play(SoundEffect.DASH_START);
            dash(input, movement, position);
            timer = 0;
            createdReadyEffect = false;
        }
    }

    private void dash(DangineSampleInput input, HeroMovement movement, Vector2f position) {
        int x = 0;
        int y = 0;
        if (input.isUp()) {
            y--;
        }
        if (input.isDown()) {
            y++;
        }
        if (input.isRight()) {
            x++;
        }
        if (input.isLeft()) {
            x--;
        }
        Vector2f vel = new Vector2f(x, y).normalise();
        movement.dash(vel.x, vel.y);
        float minAngle = (float) vel.getTheta() - 15 + 180;
        float maxAngle = (float) vel.getTheta() + 15 + 180;
        if (x == 0 && y == 0) {
            minAngle = 0;
            maxAngle = 360;
        }
        createVisualEffect(position.x, position.y, minAngle, maxAngle);
    }

    private void createDashReadyEffect(float x, float y) {
        DanginePictureParticle particle = ParticleEffectFactory.create(4, 32, ParticleEffectFactory.greenColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 200f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

    private void createVisualEffect(float x, float y, float minAngle, float maxAngle) {
        DanginePictureParticle particle = ParticleEffectFactory.create(16, 16, ParticleEffectFactory.greenColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, minAngle, maxAngle, 0.01f, 0.1f, 1500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

}
