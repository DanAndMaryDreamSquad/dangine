package dangine.collision;

import dangine.entity.combat.CombatEvent;
import dangine.entity.movement.HeroMovement;
import dangine.utility.Vector2f;

public class CollisionUtility {

    public static boolean isCircleCollidingPoint(Vector2f p1, float r1, Vector2f p2) {
        float rSquared = r1 * r1;
        float distSquared = p1.distanceSquared(p2);
        return distSquared <= rSquared;
    }

    public static boolean isCircleCollidingCircle(Vector2f p1, float r1, Vector2f p2, float r2) {
        float r1Squared = (r1 + r2) * (r1 + r2);
        float distSquared = p1.distanceSquared(p2);
        return distSquared <= r1Squared;
    }

    public static void applyKnockback(HeroMovement movement, CombatEvent arg, Vector2f absolutePosition) {
        Vector2f angleOfAttack = new Vector2f(absolutePosition.x, absolutePosition.y);
        angleOfAttack = angleOfAttack.sub(arg.getPosition()).normalise();
        if (arg.getCreator() instanceof GreatSwordHeavyCollider) {
            movement.push(angleOfAttack.x, angleOfAttack.y, 5.0f);
        }
        if (arg.getCreator() instanceof GreatSwordLightCollider) {
            movement.push(angleOfAttack.x, angleOfAttack.y, 3.0f);
        }
        if (arg.getCreator() instanceof GreatSwordCounterCollider) {
            movement.dash(angleOfAttack.x * 2.0f, angleOfAttack.y * 2.0f);
        }
    }

}
