package dangine.animation;

import dangine.utility.Vector2f;

public enum GreatSwordAnimationKeyframes {

    IDLE(new Vector2f(-12, -28), new Vector2f(7, 30), 60.0f), //
    // LIGHT_CHARGE(new Vector2f(12, -32), new Vector2f(7, 30), 260.0f), //
    LIGHT_CHARGE(new Vector2f(12, -32), new Vector2f(7, 30), -100.0f), //
    HEAVY_CHARGE(new Vector2f(-12, -40), new Vector2f(7, 30), 120.0f), //
    LIGHT_SWING_START(new Vector2f(12, -32), new Vector2f(7, 30), 260.0f), //
    LIGHT_SWING_END(new Vector2f(12 - 30, -32 + 5), new Vector2f(7, 30), 260.0f), //
    // HEAVY_SWING_START(new Vector2f(-8, -36), new Vector2f(12, 36), 60.0f), //
    HEAVY_SWING_START(new Vector2f(-8, -36), new Vector2f(12, 36), 30.0f), //
    HEAVY_SWING_END(new Vector2f(-8, -36), new Vector2f(12, 36), -200.0f), //
    // HEAVY_SWING_END(new Vector2f(-8, -36), new Vector2f(12, 36), -330.0f), //
    HOLD_MIDWAY(new Vector2f(10, -30), new Vector2f(7, 30), 0.0f), //
    COUNTER_CHARGE(new Vector2f(12, -32), new Vector2f(12, 36), 220.0f), //
    COUNTER_SWING(new Vector2f(-20, -36), new Vector2f(7, 30), 120.0f), //
    RECOILING(new Vector2f(6, -40), new Vector2f(7, 30), 60.0f);

    Orientation orientation;

    GreatSwordAnimationKeyframes(Vector2f position, Vector2f centerOfRotationX, float angle) {
        orientation = new Orientation(position, centerOfRotationX, angle);
    }

    public Orientation getOrientation() {
        return orientation;
    }

}
