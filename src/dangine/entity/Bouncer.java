package dangine.entity;

import dangine.bots.DangineBot;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Method;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class Bouncer implements IsUpdateable, HasDrawable {
    SceneGraphNode node = new SceneGraphNode();
    SceneGraphNode bumperChild = new SceneGraphNode();
    DanginePicture image = new DanginePicture("bumper");
    final float RADIUS = image.getWidth() / 2;
    final float HITBOX_SIZE = 65;
    final float BUMP_TIME = 50f;
    final CombatEvent onHit;
    final CombatEventHitbox hitbox;
    final Vector2f position = new Vector2f();
    final Vector2f centerPosition = new Vector2f();
    final Vector2f direction = new Vector2f();
    float bumpTimer = 0;
    boolean isBumping = false;
    float scale = 2.0f;

    public Bouncer() {
        bumperChild.addChild(image);
        node.addChild(bumperChild);
        node.setCenterOfRotation(scale * image.getWidth() * 0.5f, scale * image.getHeight() * 0.5f);
        node.setScale(scale, scale);

        onHit = new CombatEvent(-11, position, HITBOX_SIZE, getOnHitBy(), this);
        hitbox = new CombatEventHitbox(onHit);

        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
        setCenterPosition(150, 150);

    }

    @Override
    public void update() {
        if (isBumping) {
            bumpTimer += Utility.getGameTime().getDeltaTimeF();
            if (bumpTimer > BUMP_TIME) {
                endBump();
            }
        }
        onHit.setPosition(position.x + (RADIUS * scale), position.y + (RADIUS * scale));
        hitbox.setPosition(position.x - HITBOX_SIZE + (RADIUS * scale), position.y - HITBOX_SIZE + (RADIUS * scale));
        Utility.getActiveScene().getCombatResolver().addEvent(onHit);
    }

    private Method<CombatEvent> getOnHitBy() {
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
                    hero.getMovement().knock(direction.x, direction.y);
                    bump();
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
                    hero.getMovement().knock(direction.x, direction.y);
                    bump();
                }
            }
        };
    }

    public void setCenterPosition(float x, float y) {
        x -= image.getWidth() * scale * 0.5f;
        y -= image.getHeight() * scale * 0.5f;
        this.position.x = x;
        this.position.y = y;
        node.setPosition(this.position);
        centerPosition.x = position.x + (scale * image.getWidth() * 0.5f);
        centerPosition.y = position.y + (scale * image.getWidth() * 0.5f);
    }

    public void bump() {
        isBumping = true;
        bumpTimer = 0;
        float bumpScale = 0.5f;
        bumperChild.setScale(bumpScale, bumpScale);
        bumperChild.setPosition(bumpScale * image.getWidth() * 0.5f, bumpScale * image.getWidth() * 0.5f);
    }

    public void endBump() {
        isBumping = false;
        bumperChild.setScale(1.0f, 1.0f);
        bumperChild.setPosition(0, 0);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }
}
