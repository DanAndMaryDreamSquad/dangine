package dangine.entity;

import dangine.bots.DangineBot;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.visual.DefeatType;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Method;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class Vortex implements IsUpdateable, HasDrawable {

    static final float ROTATION_SPEED = 0.18f;
    SceneGraphNode node = new SceneGraphNode();
    DanginePicture image = new DanginePicture("vortex");
    final float RADIUS = image.getWidth() / 2;
    public final float HITBOX_SIZE = 5;
    final float PULL_HITBOX_SIZE = 100;
    final float SCALE = 2.0f;
    float angle = 0;
    final CombatEvent onHit;
    final CombatEventHitbox hitbox;
    final CombatEvent onHitPull;
    final CombatEventHitbox hitboxPull;
    final Vector2f position = new Vector2f();
    final Vector2f centerPosition = new Vector2f();
    final Vector2f direction = new Vector2f();

    public Vortex() {
        node.addChild(image);
        node.setCenterOfRotation(SCALE * image.getWidth() * 0.5f, SCALE * image.getHeight() * 0.5f);
        node.setScale(SCALE, SCALE);

        onHit = new CombatEvent(-10, position, HITBOX_SIZE, getOnHitBy(), this);
        hitbox = new CombatEventHitbox(onHit);

        onHitPull = new CombatEvent(-10, position, PULL_HITBOX_SIZE, getOnHitByPull(), this);
        hitboxPull = new CombatEventHitbox(onHitPull);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
        Utility.getActiveScene().getCameraNode().addChild(hitboxPull.getDrawable());
        setCenterPosition(150, 150);
    }

    @Override
    public void update() {
        angle += Utility.getGameTime().getDeltaTimeF() * ROTATION_SPEED;
        node.setAngle(angle);
        onHit.setPosition(position.x + (RADIUS * SCALE), position.y + (RADIUS * SCALE));
        hitbox.setPosition(position.x - HITBOX_SIZE + (RADIUS * SCALE), position.y - HITBOX_SIZE + (RADIUS * SCALE));
        Utility.getActiveScene().getCombatResolver().addEvent(onHit);

        onHitPull.setPosition(position.x + (RADIUS * SCALE), position.y + (RADIUS * SCALE));
        hitboxPull.setPosition(position.x - PULL_HITBOX_SIZE + (RADIUS * SCALE), position.y - PULL_HITBOX_SIZE
                + (RADIUS * SCALE));
        Utility.getActiveScene().getCombatResolver().addEvent(onHitPull);
    }

    private Method<CombatEvent> getOnHitBy() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Hero) {
                    Hero hero = (Hero) arg.getCreator();
                    if (!hero.isImmunity()) {
                        hero.destroy(DefeatType.SPIN);
                    }
                }
                if (arg.getCreator() instanceof DangineBot) {
                    if (Utility.getMatchParameters().getBotType().ignoresObstacles()) {
                        return;
                    }
                    DangineBot hero = (DangineBot) arg.getCreator();
                    if (!hero.isImmunity()) {
                        hero.destroy(DefeatType.SPIN);
                    }
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
                    if (hero.isImmunity()) {
                        return;
                    }
                    direction.x = hero.getPosition().x;
                    direction.y = hero.getPosition().y;
                    direction.sub(centerPosition).normalise();
                    hero.getMovement().push(-direction.x, -direction.y, 0.02f);
                }

                if (arg.getCreator() instanceof DangineBot) {
                    if (Utility.getMatchParameters().getBotType().ignoresObstacles()) {
                        return;
                    }
                    DangineBot hero = (DangineBot) arg.getCreator();
                    if (hero.isImmunity()) {
                        return;
                    }
                    direction.x = hero.getPosition().x;
                    direction.y = hero.getPosition().y;
                    direction.sub(centerPosition).normalise();
                    hero.getMovement().push(-direction.x, -direction.y, 0.02f);
                }
            }
        };
    }

    public Vector2f getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(float x, float y) {
        x -= image.getWidth() * SCALE * 0.5f;
        y -= image.getHeight() * SCALE * 0.5f;
        this.position.x = x;
        this.position.y = y;
        node.setPosition(this.position);
        centerPosition.x = position.x + (SCALE * image.getWidth() * 0.5f);
        centerPosition.y = position.y + (SCALE * image.getWidth() * 0.5f);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }
}
