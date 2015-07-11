package dangine.entity.movement;

import dangine.entity.gameplay.soccer.SoccerBallCollision;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class SoccerBallMovement implements Movement {

    public final float MAX_VELOCITY = DangineSavedSettings.INSTANCE.getMaxVelocity() / 2;
    public final float ACCELERATION = DangineSavedSettings.INSTANCE.getAcceleration();
    public final float DASH_VELOCITY = DangineSavedSettings.INSTANCE.getDashVelocity();
    public final float DASH_TIME = DangineSavedSettings.INSTANCE.getDashDuration();
    public final float DRAG = ACCELERATION * DangineSavedSettings.INSTANCE.getDragAccelerationMultiplier() / 2;
    boolean isDashing = false;
    float dashTimer = 0;
    Vector2f velocity = new Vector2f(0, 0);
    Vector2f tempPosition = new Vector2f(0, 0);
    Vector2f dragVector = new Vector2f(0, 0);
    SoccerBallCollision collision = new SoccerBallCollision();

    public Vector2f moveBall(Vector2f position) {
        if (isDashing) {
            dashTimer += Utility.getGameTime().getDeltaTimeF();
            if (dashTimer > DASH_TIME) {
                isDashing = false;
            }
        } else {
            enforceMaximumVelocity();
        }

        Vector2f finalPosition = updatePosition(position);
        finalPosition = collision.checkCollisions(this, position, finalPosition);
        position.x = finalPosition.x;
        position.y = finalPosition.y;
        return position;
    }

    private void enforceMaximumVelocity() {
        float totalVelSquared = (velocity.x * velocity.x) + (velocity.y * velocity.y);
        float maxVelSquared = MAX_VELOCITY * MAX_VELOCITY;
        if (totalVelSquared < maxVelSquared) {
            return;
        }
        float angle = velocity.getTheta();
        dragVector.x = (float) Math.cos(angle) * DRAG * Utility.getGameTime().getDeltaTimeF();
        dragVector.y = (float) Math.sin(angle) * DRAG * Utility.getGameTime().getDeltaTimeF();
        velocity.x = velocity.x - dragVector.x;
        velocity.y = velocity.y - dragVector.y;
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
