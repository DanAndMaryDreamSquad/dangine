package dangine.collision;

import org.newdawn.slick.geom.Vector2f;

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
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class GreatSwordCounterCollider implements IsUpdateable, HasDrawable {

    int wielderId = 0;
    final int SIZE = 50;
    final float HITBOX_SIZE = 50;
    final Vector2f DRAW_POSITION = new Vector2f(-16, 0);
    float MAX_X = Utility.getResolution().x;
    float MAX_Y = Utility.getResolution().y;
    SceneGraphNode node = new SceneGraphNode();
    Vector2f absolutePosition = new Vector2f(0, 0);
    final CombatEvent swing;
    final CombatEventHitbox hitBox;
    boolean clashed = false;

    public GreatSwordCounterCollider(int wielderId) {
        this.wielderId = wielderId;
        swing = new CombatEvent(wielderId, absolutePosition, HITBOX_SIZE, getOnHitCounter(), this);
        hitBox = new CombatEventHitbox(swing);
        node.setPosition(DRAW_POSITION);
    }

    public void activate() {
        Utility.getActiveScene().getCameraNode().addChild(hitBox.getDrawable());
    }

    public void deactivate() {
        Utility.getActiveScene().getCameraNode().removeChild(hitBox.getDrawable());
        clashed = false;
    }

    @Override
    public void update() {
        absolutePosition = ScreenUtility.getWorldPosition(node, absolutePosition);
        swing.setPosition(absolutePosition);
        hitBox.setPosition(absolutePosition.x - HITBOX_SIZE, absolutePosition.y - HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(swing);

    }

    public Method<CombatEvent> getOnHitCounter() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Hero || arg.getCreator() instanceof DangineBot
                        || arg.getCreator() instanceof Vortex || arg.getCreator() instanceof Bouncer) {
                    return;
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
        DanginePictureParticle particle = ParticleEffectFactory.create(4, 4, ParticleEffectFactory.yellowColors);
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
