package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;

public class BloxAnimator implements IsUpdateable {

    final BloxHeadAnimator head;
    final BloxLegAnimator legs;

    public BloxAnimator(BloxSceneGraph blox) {
        head = new BloxHeadAnimator(blox.getHead());
        legs = new BloxLegAnimator(blox);
    }

    @Override
    public void update() {
        head.update();
        legs.update();
    }

    public void walk() {
        legs.walk();
    }

    public void idle() {
        legs.idle();
    }

}
