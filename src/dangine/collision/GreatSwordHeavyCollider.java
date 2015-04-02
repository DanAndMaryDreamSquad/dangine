package dangine.collision;

import org.newdawn.slick.geom.Vector2f;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.visual.ExplosionVisual;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineParticle;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class GreatSwordHeavyCollider implements IsUpdateable, HasDrawable {

    int wielderId = 0;
    final int SIZE = 50;
    final float HEAVY_HITBOX_SIZE = 35;
    final Vector2f HEAVY_DRAW_POSITION = new Vector2f(-25, 0);
    float MAX_X = Utility.getResolution().x;
    float MAX_Y = Utility.getResolution().y;
    SceneGraphNode node = new SceneGraphNode();
    Vector2f absolutePosition = new Vector2f(0, 0);
    final CombatEvent heavySwing;
    final CombatEventHitbox heavyHitBox;

    public GreatSwordHeavyCollider(int wielderId) {
        this.wielderId = wielderId;
        heavySwing = new CombatEvent(wielderId, absolutePosition, HEAVY_HITBOX_SIZE, getOnHitHeavy(), this);
        heavyHitBox = new CombatEventHitbox(heavySwing);
        node.setPosition(HEAVY_DRAW_POSITION);
    }

    public void activate() {
        Utility.getActiveScene().getCameraNode().addChild(heavyHitBox.getDrawable());
    }

    public void deactivate() {
        Utility.getActiveScene().getCameraNode().removeChild(heavyHitBox.getDrawable());
    }

    @Override
    public void update() {
        absolutePosition = ScreenUtility.getWorldPosition(node, absolutePosition);
        heavySwing.setPosition(absolutePosition);
        heavyHitBox.setPosition(absolutePosition.x - HEAVY_HITBOX_SIZE, absolutePosition.y - HEAVY_HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(heavySwing);

    }

    private Method<CombatEvent> getOnHitHeavy() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Hero) {
                    return;
                }
                Hero hero = Utility.getActiveScene().getHero(wielderId);
                if (hero != null) {
                    Vector2f angleOfAttack = new Vector2f(absolutePosition.x, absolutePosition.y);
                    angleOfAttack = angleOfAttack.sub(arg.getPosition()).normalise();
                    hero.getMovement().push(angleOfAttack.x, angleOfAttack.y, 0.25f);
                    createVisualEffect();
                }
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
        DangineParticle particle = ParticleEffectFactory.createFire(4, 4);
        ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

}
