package dangine.entity.combat.subpower;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.combat.GreatSword;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class CounterPower {

    public static final float COUNTER_DURATION = 500f;
    float timer = 0;
    final float MAX_TIME = 5000f;
    boolean createdReadyEffect = false;

    public boolean canCounter() {
        return timer > MAX_TIME;
    }

    public void update(DangineSampleInput input, GreatSword greatSword) {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > MAX_TIME && !createdReadyEffect) {
            SoundPlayer.play(SoundEffect.COUNTER_READY);
            Vector2f position = Utility.getActiveScene().getHero(greatSword.getPlayerId()).getPosition();
            createCounterReadyEffect(position.x, position.y);
            createdReadyEffect = true;
        }
        if (input.isButtonTwo() && timer > MAX_TIME) {
            SoundPlayer.play(SoundEffect.COUNTER_START);
            Vector2f position = Utility.getActiveScene().getHero(greatSword.getPlayerId()).getPosition();
            counter(input, greatSword, position);
            timer = 0;
            createdReadyEffect = false;
        }
    }

    private void counter(DangineSampleInput input, GreatSword greatSword, Vector2f position) {
        greatSword.counterCharge();
        createVisualEffect(position.x, position.y, 0, 360);
    }
    
    private void createCounterReadyEffect(float x, float y) {
        DanginePictureParticle particle = ParticleEffectFactory.create(4, 32, ParticleEffectFactory.yellowColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 200f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

    private void createVisualEffect(float x, float y, float minAngle, float maxAngle) {
        DanginePictureParticle particle = ParticleEffectFactory.create(16, 16, ParticleEffectFactory.yellowColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, minAngle, maxAngle, 0.01f, 0.1f, 1500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

}
