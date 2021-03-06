package dangine.collision;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.bots.DangineBot;
import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class GreatSwordCollider implements HasDrawable {

    GreatSwordColliderData colliderData;
    boolean isActive = false;

    public GreatSwordCollider(int wielderId, ColliderType colliderType) {
        switch (colliderType) {
        case LIGHT:
        case HEAVY:
            this.colliderData = new GreatSwordColliderData(wielderId, colliderType, getOnHit());
            break;
        case COUNTER:
            this.colliderData = new GreatSwordColliderData(wielderId, colliderType, getOnHitCounter());
            break;
        }
    }

    public void activate() {
        Utility.getActiveScene().getCameraNode().addChild(colliderData.getEventHitBox().getDrawable());
        isActive = true;
    }

    public void deactivate() {
        Utility.getActiveScene().getCameraNode().removeChild(colliderData.getEventHitBox().getDrawable());
        colliderData.setClashed(false);
        isActive = false;
    }

    public void updateSwing() {
        Vector2f absolutePosition = ScreenUtility.getWorldPosition(colliderData.getNode(),
                colliderData.getAbsolutePosition());
        colliderData.getSwingEvent().setPosition(absolutePosition);
        colliderData.getEventHitBox().setPosition(absolutePosition.x - colliderData.getColliderType().getSize(),
                absolutePosition.y - colliderData.getColliderType().getSize());
        Utility.getActiveScene().getCombatResolver().addEvent(colliderData.getSwingEvent());

    }

    public Method<CombatEvent> getOnHit() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                HeroMovement movement = null;
                boolean applyKnockback = true;
                Vector2f unitPosition = null;
                if (colliderData.getWielderId() < 0) {
                    DangineBot bot = Utility.getActiveScene().getBot(colliderData.getWielderId());
                    movement = bot.getMovement();
                    unitPosition = bot.getPosition();
                    if (Utility.getMatchParameters().getBotType().ignoresKnockback()) {
                        applyKnockback = false;
                    }
                } else {
                    Hero hero = Utility.getActiveScene().getHero(colliderData.getWielderId());
                    movement = hero.getMovement();
                    unitPosition = hero.getPosition();
                }
                if (applyKnockback) {
                    CollisionUtility.applyKnockback(movement, arg, unitPosition);
                }
                SoundPlayer.play(SoundEffect.CLASH_LIGHT);
                createVisualEffect();
                clashed();
            }
        };
    }

    public Method<CombatEvent> getOnHitCounter() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                SoundPlayer.play(SoundEffect.COUNTER_CLASH);
                createVisualEffect();
                clashed();
            }
        };
    }

    @Override
    public IsDrawable getDrawable() {
        return colliderData.getDrawable();
    }

    private void createVisualEffect() {
        float x = colliderData.getAbsolutePosition().x;
        float y = colliderData.getAbsolutePosition().y;
        Debugger.info("" + colliderData.getAbsolutePosition());
        DanginePictureParticle particle = ParticleEffectFactory.create(8, 4, ParticleEffectFactory.fireColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

    public boolean isClashed() {
        return colliderData.isClashed();
    }

    public void clashed() {
        colliderData.setClashed(true);
    }

    public GreatSwordColliderData getColliderData() {
        return colliderData;
    }

    public boolean isActive() {
        return isActive;
    }

}
