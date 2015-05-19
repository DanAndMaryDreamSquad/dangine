package dangine.entity.combat;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineCirclePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Vector2f;

public class CombatEventHitbox implements HasDrawable {

    final CombatEvent event;
    final SceneGraphNode node = new SceneGraphNode();

    public CombatEventHitbox(CombatEvent event) {
        this.event = event;
        node.addChild(new DangineCirclePicture(event.getRadius()));
        node.setPosition(event.getPosition());
        node.setZValue(-5);
    }

    public void setPosition(Vector2f position) {
        node.setPosition(position);
    }

    public void setPosition(float x, float y) {
        node.setPosition(x, y);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
