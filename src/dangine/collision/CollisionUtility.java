package dangine.collision;

import dangine.debugger.Debugger;
import dangine.entity.combat.CombatEvent;
import dangine.entity.movement.Movement;
import dangine.utility.DangineSavedSettings;
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

    public static void applyKnockback(Movement movement, CombatEvent arg, Vector2f absolutePosition) {
        Vector2f angleOfAttack = new Vector2f(absolutePosition.x, absolutePosition.y);
        angleOfAttack = angleOfAttack.sub(arg.getPosition()).normalise();
        if (arg.getCreator() instanceof GreatSwordColliderData) {
            ColliderType colliderType = ((GreatSwordColliderData) arg.getCreator()).getColliderType();
            Debugger.warn("in col: " + angleOfAttack + " " + colliderType);
            if (colliderType == ColliderType.HEAVY) {
                movement.push(angleOfAttack.x, angleOfAttack.y, DangineSavedSettings.INSTANCE.getHeavyKnockPower());
            }
            if (colliderType == ColliderType.LIGHT) {
                movement.push(angleOfAttack.x, angleOfAttack.y, DangineSavedSettings.INSTANCE.getLightKnockPower());
            }
            if (colliderType == ColliderType.COUNTER) {
                movement.dash(angleOfAttack.x * DangineSavedSettings.INSTANCE.getCounterKnockPower(), angleOfAttack.y
                        * DangineSavedSettings.INSTANCE.getCounterKnockPower());
            }
        }
    }

}
