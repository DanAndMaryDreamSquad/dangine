package dangine.collision;

import dangine.bots.DangineBot;
import dangine.debugger.Debugger;
import dangine.entity.Bouncer;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.Vortex;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

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
    boolean clashed = false;

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
        clashed = false;
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
                if (arg.getCreator() instanceof Hero || arg.getCreator() instanceof DangineBot
                        || arg.getCreator() instanceof Vortex || arg.getCreator() instanceof Bouncer) {
                    return;
                }
                HeroMovement movement = null;
                boolean applyKnockback = true;
                if (wielderId < 0) {
                    movement = Utility.getActiveScene().getBot(wielderId).getMovement();
                    if (Utility.getMatchParameters().getBotType().ignoresKnockback()) {
                        applyKnockback = false;
                    }
                } else {
                    movement = Utility.getActiveScene().getHero(wielderId).getMovement();
                }
                if (applyKnockback) {
                    CollisionUtility.applyKnockback(movement, arg, absolutePosition);
                }
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
        DanginePictureParticle particle = ParticleEffectFactory.create(4, 4, ParticleEffectFactory.fireColors);
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
