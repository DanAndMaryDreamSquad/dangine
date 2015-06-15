package dangine.collision;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.CombatResolver;
import dangine.entity.combat.CombatResolver.EventType;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Method;
import dangine.utility.Vector2f;

public class GreatSwordColliderData implements HasDrawable {

    ColliderType colliderType;
    int wielderId = 0;
    int colliderId;
    SceneGraphNode node = new SceneGraphNode();
    Vector2f absolutePosition = new Vector2f(0, 0);
    final CombatEvent swingEvent;
    final CombatEventHitbox eventHitBox;
    boolean clashed = false;

    public GreatSwordColliderData(int wielderId, ColliderType colliderType, Method<CombatEvent> onHit) {
        this.wielderId = wielderId;
        this.colliderType = colliderType;
        this.colliderId = MatchType.getColliderId(wielderId);
        this.swingEvent = new CombatEvent(colliderId, absolutePosition, colliderType.getSize(), onHit, this,
                EventType.SWORD, CombatResolver.getTypeToTargets().get(EventType.SWORD));
        this.eventHitBox = new CombatEventHitbox(swingEvent);
        this.node.setPosition(colliderType.getOffset());
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    public CombatEventHitbox getEventHitBox() {
        return eventHitBox;
    }

    public boolean isClashed() {
        return clashed;
    }

    public void setClashed(boolean clashed) {
        this.clashed = clashed;
    }

    public SceneGraphNode getNode() {
        return node;
    }

    public Vector2f getAbsolutePosition() {
        return absolutePosition;
    }

    public CombatEvent getSwingEvent() {
        return swingEvent;
    }

    public ColliderType getColliderType() {
        return colliderType;
    }

    public int getWielderId() {
        return wielderId;
    }
}
