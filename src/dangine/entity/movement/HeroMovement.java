package dangine.entity.movement;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.combat.IsGreatsword;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class HeroMovement {

    public static final float MAX_VELOCITY = 0.25f;
    public static final float ACCELERATION = 0.00055f;

    Vector2f velocity = new Vector2f(0, 0);

    public Vector2f moveHero(Vector2f position, DangineSampleInput input, IsGreatsword activeWeapon) {
        MovementMode movementMode = Utility.getMatchParameters().getMovementMode();
        if (movementMode.canMove(activeWeapon)) {
            updateVelocity(input);
        }
        enforceMaximumVelocity();
        return updatePosition(position);
    }

    public Vector2f updateVelocity(DangineSampleInput input) {
        if (input.isUp()) {
            velocity.y -= Utility.getGameTime().getDeltaTimeF() * ACCELERATION;
        }
        if (input.isDown()) {
            velocity.y += Utility.getGameTime().getDeltaTimeF() * ACCELERATION;
        }
        if (input.isLeft()) {
            velocity.x -= Utility.getGameTime().getDeltaTimeF() * ACCELERATION;
        }
        if (input.isRight()) {
            velocity.x += Utility.getGameTime().getDeltaTimeF() * ACCELERATION;
        }
        return velocity;
    }

    private void enforceMaximumVelocity() {
        velocity.y = Math.min(velocity.y, MAX_VELOCITY);
        velocity.y = Math.max(velocity.y, -MAX_VELOCITY);
        velocity.x = Math.min(velocity.x, MAX_VELOCITY);
        velocity.x = Math.max(velocity.x, -MAX_VELOCITY);
    }

    public Vector2f updatePosition(Vector2f position) {
        position.x += velocity.x * Utility.getGameTime().getDeltaTimeF();
        position.y += velocity.y * Utility.getGameTime().getDeltaTimeF();
        return position;
    }

    public void push(float x, float y) {
        x = x * MAX_VELOCITY;
        y = y * MAX_VELOCITY;
        velocity.x += x;
        velocity.y += y;
    }

    public void push(float x, float y, float scale) {
        x = x * MAX_VELOCITY * scale;
        y = y * MAX_VELOCITY * scale;
        velocity.x += x;
        velocity.y += y;
    }

}
