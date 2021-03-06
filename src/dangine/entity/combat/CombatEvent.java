package dangine.entity.combat;

import java.util.List;

import dangine.collision.CollisionUtility;
import dangine.entity.combat.CombatResolver.EventType;
import dangine.utility.Method;
import dangine.utility.Vector2f;

public class CombatEvent {

    final int ownerId;
    Method<CombatEvent> onHitBy = null;
    Vector2f position = new Vector2f(0, 0);
    float radius;
    Object creator;
    EventType type;
    List<EventType> targets;

    public CombatEvent(int ownerId, Vector2f position, float radius, Method<CombatEvent> onHitBy, Object creator,
            EventType type, List<EventType> targets) {
        this.ownerId = ownerId;
        this.position.x = position.x;
        this.position.y = position.y;
        this.radius = radius;
        this.onHitBy = onHitBy;
        this.creator = creator;
        this.type = type;
        this.targets = targets;
    }

    public void process(List<CombatEvent> events) {

        for (CombatEvent event : events) {
            if (event == this || event.getOwnerId() == ownerId) {
                continue;
            }
            boolean colliding = CollisionUtility.isCircleCollidingCircle(position, radius, event.getPosition(),
                    event.getRadius());
            if (colliding) {
                onHitBy(event);
            }
        }
    }

    public void setPosition(Vector2f position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void onHitBy(CombatEvent event) {
        if (onHitBy != null) {
            onHitBy.call(event);
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

    public Object getCreator() {
        return creator;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public List<EventType> getTargets() {
        return targets;
    }

    public void setTargets(List<EventType> targets) {
        this.targets = targets;
    }

}
