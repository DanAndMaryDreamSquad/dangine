package dangine.entity;

import org.newdawn.slick.geom.Vector2f;

import dangine.debugger.Debugger;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Method;
import dangine.utility.Utility;

public class Vortex implements IsUpdateable, HasDrawable {

    static final float ROTATION_SPEED = 0.18f;
    SceneGraphNode node = new SceneGraphNode();
    DangineImage image = new DangineImage("vortex");
    final float RADIUS = image.getWidth() / 2;
    final float HITBOX_SIZE = 20;
    final float PULL_HITBOX_SIZE = 200;
    float angle = 0;
    final CombatEvent onHit;
    final CombatEventHitbox hitbox;
    final CombatEvent onHitPull;
    final CombatEventHitbox hitboxPull;
    final Vector2f position = new Vector2f();

    public Vortex() {
        node.addChild(image);
        node.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);

        onHit = new CombatEvent(-1, position, HITBOX_SIZE, getOnHitBy(), this);
        hitbox = new CombatEventHitbox(onHit);

        onHitPull = new CombatEvent(-1, position, PULL_HITBOX_SIZE, getOnHitByPull(), this);
        hitboxPull = new CombatEventHitbox(onHitPull);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
        Utility.getActiveScene().getCameraNode().addChild(hitboxPull.getDrawable());
        setPosition(150, 150);
    }

    @Override
    public void update() {
        angle += Utility.getGameTime().getDeltaTimeF() * ROTATION_SPEED;
        node.setAngle(angle);
        onHit.setPosition(position.x + RADIUS, position.y + RADIUS);
        hitbox.setPosition(position.x - HITBOX_SIZE + RADIUS, position.y - HITBOX_SIZE + RADIUS);
        Utility.getActiveScene().getCombatResolver().addEvent(onHit);

        onHitPull.setPosition(position.x + RADIUS, position.y + RADIUS);
        hitboxPull.setPosition(position.x - PULL_HITBOX_SIZE + RADIUS, position.y - PULL_HITBOX_SIZE + RADIUS);
        Utility.getActiveScene().getCombatResolver().addEvent(onHitPull);
    }

    private Method<CombatEvent> getOnHitBy() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Hero) {
                    Hero hero = (Hero) arg.getCreator();
                    hero.destroy();
                }
            }
        };
    }

    private Method<CombatEvent> getOnHitByPull() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Hero) {
                    Hero hero = (Hero) arg.getCreator();

                    Vector2f direction = new Vector2f(hero.getPosition()).sub(position).normalise();
                    Debugger.info(direction.toString());
                    hero.getMovement().push(-direction.x, -direction.y);
                }
            }
        };
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        node.setPosition(this.position);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }
}
