package dangine.entity.gameplay.soccer;

import dangine.collision.ColliderType;
import dangine.collision.CollisionUtility;
import dangine.collision.GreatSwordColliderData;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.CombatResolver;
import dangine.entity.combat.CombatResolver.EventType;
import dangine.entity.movement.SoccerBallMovement;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Method;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class SoccerBall implements IsUpdateable, HasDrawable {

    SceneGraphNode base = new SceneGraphNode();
    DanginePicture image;
    static final float SCALE = 3.0f;
    final float ROTATION_SPEED = 0.094f;
    final float LAST_HITTER_PERIOD = 200f;
    float angle = 0.0f;
    final int HITBOX_SIZE = 25;
    final CombatEvent onHit;
    final CombatEventHitbox hitbox;
    final SoccerBallMovement movement = new SoccerBallMovement();
    final Vector2f position = new Vector2f(Utility.getResolution()).scale(0.5f);
    float lastHitterTimer = 0;
    int lastHitterId = -99;

    public SoccerBall() {
        image = new DanginePicture("soccerball");
        base.addChild(image);
        base.setScale(SCALE, SCALE);
        base.setCenterOfRotation(image.getWidth() * SCALE * 0.5f, image.getHeight() * SCALE * 0.5f);
        base.setPosition(position);
        onHit = new CombatEvent(100, position, HITBOX_SIZE, getOnHitBy(), this, EventType.BALL, CombatResolver
                .getTypeToTargets().get(EventType.BALL));
        hitbox = new CombatEventHitbox(onHit);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
    }

    @Override
    public void update() {
        lastHitterTimer += Utility.getGameTime().getDeltaTimeF();
        if (lastHitterTimer > LAST_HITTER_PERIOD) {
            lastHitterId = -99;
        }

        movement.moveBall(this.position);
        angle += Utility.getGameTime().getDeltaTimeF() * ROTATION_SPEED;
        base.setAngle(angle);

        float offset = image.getHeight() * SCALE * 0.5f;
        base.setPosition(position);
        onHit.setPosition(position.x + offset, position.y + offset);
        hitbox.setPosition(position.x - HITBOX_SIZE + offset, position.y - HITBOX_SIZE + offset);
        Utility.getActiveScene().getCombatResolver().addEvent(onHit);
    }

    public Method<CombatEvent> getOnHitBy() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getOwnerId() == lastHitterId) {
                    return;
                }
                Vector2f position;
                ColliderType colliderType = ColliderType.LIGHT;
                if (arg.getCreator() instanceof GreatSwordColliderData) {
                    Hero hero = Utility.getActiveScene().getHero(arg.getOwnerId());
                    colliderType = ((GreatSwordColliderData) arg.getCreator()).getColliderType();
                    position = hero.getPosition();
                } else {
                    position = arg.getPosition();
                }
                CollisionUtility.applyKnockback(movement, position, onHit.getPosition(), colliderType);
                lastHitterId = arg.getOwnerId();
                lastHitterTimer = 0;
            }
        };
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public Vector2f getPosition() {
        return position;
    }

    public SoccerBallMovement getMovement() {
        return movement;
    }

}
