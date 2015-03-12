package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Oscillator;

public class BloxLegAnimator implements IsUpdateable {

    enum State {
        IDLING, WALKING;
    }

    final float WALK_RATE = 1000f;
    final Oscillator oscillator = new Oscillator(0, 20, WALK_RATE);

    final SceneGraphNode leftLeg;
    final SceneGraphNode rightLeg;

    State state = State.IDLING;

    public BloxLegAnimator(BloxSceneGraph blox) {
        this.leftLeg = blox.leftLeg;
        this.rightLeg = blox.rightLeg;
    }

    @Override
    public void update() {
        switch (state) {
        case IDLING:
            return;
        case WALKING:
            float value = oscillator.update();
            leftLeg.setXPosition(value - 5);
            rightLeg.setXPosition(15 - value);
        }
    }

    public void idle() {
        state = State.IDLING;
    }

    public void walk() {
        state = State.WALKING;
    }

}
