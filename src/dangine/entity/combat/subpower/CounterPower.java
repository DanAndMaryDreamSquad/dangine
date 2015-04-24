package dangine.entity.combat.subpower;

import org.newdawn.slick.geom.Vector2f;

import dangine.bots.BotGreatsword;
import dangine.bots.DangineBot;
import dangine.entity.combat.GreatSword;
import dangine.entity.visual.ExplosionVisual;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.DangineParticle;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Utility;

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
            Vector2f position = Utility.getActiveScene().getHero(greatSword.getPlayerId()).getPosition();
            createCounterReadyEffect(position.x, position.y);
            createdReadyEffect = true;
        }
        if (input.isButtonTwo() && timer > MAX_TIME) {
            Vector2f position = Utility.getActiveScene().getHero(greatSword.getPlayerId()).getPosition();
            counter(input, greatSword, position);
            timer = 0;
            createdReadyEffect = false;
        }
    }

    public void update(DangineSampleInput input, BotGreatsword greatSword) {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > MAX_TIME && !createdReadyEffect) {
            DangineBot bot = Utility.getActiveScene().getBot(greatSword.getBotId());
            Vector2f position = bot.getPosition();
            createCounterReadyEffect(position.x, position.y);
            createdReadyEffect = true;
        }
        if (input.isButtonThree() && timer > MAX_TIME) {
            DangineBot bot = Utility.getActiveScene().getBot(greatSword.getBotId());
            Vector2f position = bot.getPosition();
            counter(input, greatSword, position);
            timer = 0;
            createdReadyEffect = false;
        }
    }

    private void counter(DangineSampleInput input, GreatSword greatSword, Vector2f position) {
        greatSword.counterCharge();
        createVisualEffect(position.x, position.y, 0, 360);
    }

    private void counter(DangineSampleInput input, BotGreatsword greatSword, Vector2f position) {
        greatSword.counterCharge();
        createVisualEffect(position.x, position.y, 0, 360);
    }

    private void createCounterReadyEffect(float x, float y) {
        DangineParticle particle = ParticleEffectFactory.create(4, 32, ParticleEffectFactory.yellowColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 200f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

    private void createVisualEffect(float x, float y, float minAngle, float maxAngle) {
        DangineParticle particle = ParticleEffectFactory.create(16, 16, ParticleEffectFactory.yellowColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, minAngle, maxAngle, 0.01f, 0.1f, 1500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

}
