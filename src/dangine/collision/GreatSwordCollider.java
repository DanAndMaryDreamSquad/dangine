package dangine.collision;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineShape;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class GreatSwordCollider implements IsUpdateable, HasDrawable {

    int wielderId = 0;
    final int SIZE = 50;
    final float HITBOX_SIZE = 35;
    final Vector2f DRAW_POSITION = new Vector2f(-25, 0);
    SceneGraphNode node = new SceneGraphNode();
    SceneGraphNode center = new SceneGraphNode();
    Vector2f absolutePosition = new Vector2f(0, 0);
    final CombatEvent swing;
    final CombatEventHitbox hitBox;

    public GreatSwordCollider(int wielderId) {
        this.wielderId = wielderId;
        swing = new CombatEvent(wielderId, absolutePosition, HITBOX_SIZE, getOnHit(), this);
        hitBox = new CombatEventHitbox(swing);
        node.setPosition(DRAW_POSITION);
        center.addChild(new DangineShape());
    }

    public void activate() {
        Utility.getActiveScene().getCameraNode().addChild(center);
        Utility.getActiveScene().getCameraNode().addChild(hitBox.getDrawable());
    }

    public void deactivate() {
        Utility.getActiveScene().getCameraNode().removeChild(center);
        Utility.getActiveScene().getCameraNode().removeChild(hitBox.getDrawable());

    }

    @Override
    public void update() {
        absolutePosition = ScreenUtility.getWorldPosition(node, absolutePosition);

        swing.setPosition(absolutePosition);
        hitBox.setPosition(absolutePosition.x - HITBOX_SIZE, absolutePosition.y - HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(swing);

        center.setPosition(absolutePosition);
    }

    public Method<CombatEvent> getOnHit() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Hero) {
                    return;
                }
                Hero hero = Utility.getActiveScene().getHero(wielderId);
                if (hero != null) {
                    Vector2f angleOfAttack = absolutePosition.sub(arg.getPosition()).normalise();
                    hero.getMovement().push(angleOfAttack.x, angleOfAttack.y);
                }
            }
        };
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
