package dangine.entity.movement;

import dangine.entity.combat.IsGreatsword;
import dangine.input.DangineSampleInput;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class HeroMovement implements Movement {

    // public static final float MAX_VELOCITY = 0.25f;
    // public static final float ACCELERATION = 0.00055f;
    // public static final float DASH_VELOCITY = 0.50f;
    // public static final float DASH_TIME = 150f;
    // public static final float DRAG = ACCELERATION * 2;
    public final float MAX_VELOCITY = DangineSavedSettings.INSTANCE.getMaxVelocity();
    public final float ACCELERATION = DangineSavedSettings.INSTANCE.getAcceleration();
    public final float DASH_VELOCITY = DangineSavedSettings.INSTANCE.getDashVelocity();
    public final float DASH_TIME = DangineSavedSettings.INSTANCE.getDashDuration();
    public final float DRAG = ACCELERATION * DangineSavedSettings.INSTANCE.getDragAccelerationMultiplier();
    boolean isDashing = false;
    float dashTimer = 0;
    Vector2f velocity = new Vector2f(0, 0);
    Vector2f tempPosition = new Vector2f(0, 0);
    HeroCollision collision = new HeroCollision();

    public Vector2f moveHero(Vector2f position, DangineSampleInput input, IsGreatsword activeWeapon) {
        if (isDashing) {
            dashTimer += Utility.getGameTime().getDeltaTimeF();
            if (dashTimer > DASH_TIME) {
                isDashing = false;
            }
        } else {
            MovementMode movementMode = Utility.getMatchParameters().getMovementMode();
            if (movementMode.canMove(activeWeapon)) {
                updateVelocity(input);
            }
            enforceMaximumVelocity();
        }
        Vector2f finalPosition = updatePosition(position);
        finalPosition = collision.checkCollisions(this, position, finalPosition);
        position.x = finalPosition.x;
        position.y = finalPosition.y;
        return position;
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
        float overShootY = 0;
        float overShootX = 0;
        if (velocity.y > MAX_VELOCITY) {
            overShootY = velocity.y - MAX_VELOCITY;
            velocity.y -= DRAG * Utility.getGameTime().getDeltaTimeF();
            if (overShootY < DRAG * Utility.getGameTime().getDeltaTimeF() * 1) {
                velocity.y = Math.min(velocity.y, MAX_VELOCITY);
            }
        } else if (velocity.y < -MAX_VELOCITY) {
            overShootY = velocity.y + MAX_VELOCITY;
            velocity.y += DRAG * Utility.getGameTime().getDeltaTimeF();
            if (overShootY > -DRAG * Utility.getGameTime().getDeltaTimeF() * 1) {
                velocity.y = Math.max(velocity.y, -MAX_VELOCITY);
            }
        }
        if (velocity.x > MAX_VELOCITY) {
            overShootX = velocity.x - MAX_VELOCITY;
            velocity.x -= DRAG * Utility.getGameTime().getDeltaTimeF();
            if (overShootX < DRAG * Utility.getGameTime().getDeltaTimeF() * 1) {
                velocity.x = Math.min(velocity.x, MAX_VELOCITY);
            }
        } else if (velocity.x < -MAX_VELOCITY) {
            overShootX = velocity.x + MAX_VELOCITY;
            velocity.x += DRAG * Utility.getGameTime().getDeltaTimeF();
            if (overShootX > -DRAG * Utility.getGameTime().getDeltaTimeF() * 1) {
                velocity.x = Math.max(velocity.x, -MAX_VELOCITY);
            }
        }

        // velocity.y = Math.min(velocity.y, MAX_VELOCITY);
        // velocity.y = Math.max(velocity.y, -MAX_VELOCITY);
        // velocity.x = Math.min(velocity.x, MAX_VELOCITY);
        // velocity.x = Math.max(velocity.x, -MAX_VELOCITY);
    }

    public Vector2f updatePosition(Vector2f position) {
        tempPosition.x = position.x + (velocity.x * Utility.getGameTime().getDeltaTimeF());
        tempPosition.y = position.y + (velocity.y * Utility.getGameTime().getDeltaTimeF());
        return tempPosition;
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

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public void dash(float x, float y) {
        velocity.x = x * DASH_VELOCITY;
        velocity.y = y * DASH_VELOCITY;
        isDashing = true;
        dashTimer = 0;
    }

    public void knock(float x, float y) {
        velocity.x = x * DASH_VELOCITY;
        velocity.y = y * DASH_VELOCITY;
        isDashing = true;
        dashTimer = 0;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

}
