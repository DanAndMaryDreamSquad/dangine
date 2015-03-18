package dangine.collision;

import org.newdawn.slick.geom.Vector2f;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatEvent;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineShape;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class GreatSwordCollider implements IsUpdateable, HasDrawable {

    int wielderId = 0;
    final int SIZE = 50;
    final Vector2f DRAW_POSITION = new Vector2f(-55, -20);
    SceneGraphNode node = new SceneGraphNode();
    SceneGraphNode center = new SceneGraphNode();
    Vector2f absolutePosition = new Vector2f(0, 0);

    public GreatSwordCollider(int wielderId) {
        this.wielderId = wielderId;
        // node.addChild(new DangineShape(SIZE, SIZE, Color.yellow));
        node.setPosition(DRAW_POSITION);
        center.addChild(new DangineShape());
        Utility.getActiveScene().getParentNode().addChild(center);
    }

    @Override
    public void update() {
        node.setPosition(DRAW_POSITION.x + (SIZE / 2), DRAW_POSITION.y + (SIZE / 2));
        absolutePosition = ScreenUtility.getScreenPosition(node, absolutePosition);
        node.setPosition(DRAW_POSITION);
        Utility.getActiveScene().getCombatResolver()
                .addEvent(new CombatEvent(wielderId, absolutePosition, SIZE / 4, getOnHit(), this));

        center.setPosition(absolutePosition);
    }

    public Method<CombatEvent> getOnHit() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                Debugger.info();
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
