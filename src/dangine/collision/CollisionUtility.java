package dangine.collision;

import org.newdawn.slick.geom.Vector2f;

public class CollisionUtility {

    public static boolean isCircleCollidingPoint(Vector2f p1, float r1, Vector2f p2) {
        float rSquared = r1 * r1;
        float distSquared = p1.distanceSquared(p2);
        return distSquared <= rSquared;
    }

    public static boolean isCircleCollidingCircle(Vector2f p1, float r1, Vector2f p2, float r2) {
        float r1Squared = r1 * r1;
        float r2Squared = r2 * r2;
        float distSquared = p1.distanceSquared(p2);
        return distSquared <= r1Squared + r2Squared;
    }

}
