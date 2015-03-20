package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Oscillator;

public class BloxLegAnimator implements IsUpdateable {

    enum State {
        IDLING, WALKING, FLOATING;
    }

    final float WALK_RATE = 1000f;
    final float FLOAT_RATE = 5000f;
    final Oscillator walkOscillator = new Oscillator(0, 20, WALK_RATE);
    final Oscillator floatOscillator = new Oscillator(-10, -30, FLOAT_RATE);

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
            // float value = walkOscillator.update();
            // leftLeg.setXPosition(value - 5);
            // rightLeg.setXPosition(15 - value);
            float value = floatOscillator.update();
            leftLeg.setAngle(value - 12);
            rightLeg.setAngle(floatOscillator.calcOffset(FLOAT_RATE / 2) - 12);
            return;
        case FLOATING:
            float angle = floatOscillator.update();
            leftLeg.setAngle(angle);
            rightLeg.setAngle(floatOscillator.calcOffset(FLOAT_RATE / 2));
            return;
        }
    }

    public void idle() {
        state = State.IDLING;
    }

    public void walk() {
        state = State.WALKING;
        leftLeg.setAngle(0);
        rightLeg.setAngle(0);
    }

    public void floating() {
        state = State.FLOATING;
        leftLeg.setXPosition(2);
        rightLeg.setXPosition(8);
    }

}
