package dangine.collision;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.CombatResolver;
import dangine.entity.combat.CombatResolver.EventType;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class GreatSwordCounterCollider implements HasDrawable {

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
        int colliderId = MatchType.getColliderId(wielderId);
        swing = new CombatEvent(colliderId, absolutePosition, HITBOX_SIZE, getOnHitCounter(), this, EventType.SWORD,
                CombatResolver.getTypeToTargets().get(EventType.SWORD));
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

    public void updateSwing() {
        absolutePosition = ScreenUtility.getWorldPosition(node, absolutePosition);
        swing.setPosition(absolutePosition);
        hitBox.setPosition(absolutePosition.x - HITBOX_SIZE, absolutePosition.y - HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(swing);
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
