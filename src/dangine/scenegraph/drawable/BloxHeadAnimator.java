package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.utility.Oscillator;

public class BloxHeadAnimator implements IsUpdateable {

    final float HEAD_RATE = 10000f;
    final Oscillator oscillator = new Oscillator(-30, 30, HEAD_RATE);

    enum State {
        IDLE;
    }

    final BloxHeadSceneGraph head;

    public BloxHeadAnimator(BloxHeadSceneGraph head) {
        this.head = head;
    }

    @Override
    public void update() {
        float angle = oscillator.update();
        head.getHead().setAngle(angle);
    }

}
