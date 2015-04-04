package dangine.collision;

import org.newdawn.slick.geom.Vector2f;

import dangine.bots.DangineBot;
import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.ExplosionVisual;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineParticle;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class GreatSwordLightCollider implements IsUpdateable, HasDrawable {

    int wielderId = 0;
    final int SIZE = 50;
    final float LIGHT_HITBOX_SIZE = 25;
    final Vector2f LIGHT_DRAW_POSITION = new Vector2f(-38, 0);
    float MAX_X = Utility.getResolution().x;
    float MAX_Y = Utility.getResolution().y;
    SceneGraphNode node = new SceneGraphNode();
    Vector2f absolutePosition = new Vector2f(0, 0);
    final CombatEvent lightSwing;
    final CombatEventHitbox lightHitBox;
    boolean clashed = false;

    public GreatSwordLightCollider(int wielderId) {
        this.wielderId = wielderId;
        lightSwing = new CombatEvent(wielderId, absolutePosition, LIGHT_HITBOX_SIZE, getOnHitLight(), this);
        lightHitBox = new CombatEventHitbox(lightSwing);
        node.setPosition(LIGHT_DRAW_POSITION);
    }

    public void activate() {
        Utility.getActiveScene().getCameraNode().addChild(lightHitBox.getDrawable());
    }

    public void deactivate() {
        Utility.getActiveScene().getCameraNode().removeChild(lightHitBox.getDrawable());
        clashed = false;
    }

    @Override
    public void update() {
        absolutePosition = ScreenUtility.getWorldPosition(node, absolutePosition);
        lightSwing.setPosition(absolutePosition);
        lightHitBox.setPosition(absolutePosition.x - LIGHT_HITBOX_SIZE, absolutePosition.y - LIGHT_HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(lightSwing);

    }

    public Method<CombatEvent> getOnHitLight() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Hero) {
                    return;
                }
                HeroMovement movement = null;
                if (wielderId < 0) {
                    movement = Utility.getActiveScene().getUpdateable(DangineBot.class).getMovement();
                } else {
                    movement = Utility.getActiveScene().getHero(wielderId).getMovement();
                }
                CollisionUtility.applyKnockback(movement, arg, absolutePosition);
                createVisualEffect();
                clashed();
            }
        };
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    private void createVisualEffect() {
        float x = absolutePosition.x;
        float y = absolutePosition.y;
        Debugger.info("" + absolutePosition);
        DangineParticle particle = ParticleEffectFactory.create(4, 4, ParticleEffectFactory.fireColors);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

    public boolean isClashed() {
        return clashed;
    }

    public void clashed() {
        clashed = true;
    }

}