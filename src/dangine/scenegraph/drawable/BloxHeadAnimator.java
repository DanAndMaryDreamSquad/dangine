package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.utility.Oscillator;

public class BloxHeadAnimator implements IsUpdateable {

    final float HEAD_RATE = 10000f;
    final Oscillator oscillator = new Oscillator(-30, 30, HEAD_RATE);
    float angle;

    enum State {
        IDLE, WALKING, FLOATING;
    }

    State state = State.IDLE;

    final BloxHeadSceneGraph head;

    public BloxHeadAnimator(BloxHeadSceneGraph head) {
        this.head = head;
    }

    @Override
    public void update() {
        switch (state) {
        case IDLE:
            float angle = oscillator.update();
            head.getHead().setAngle(angle);
            break;
        case WALKING:
            break;
        default:
            break;
        }
    }

    public void idle() {
        state = State.IDLE;
        head.getRightEye().setAngle(90);
        head.getLeftEye().setAngle(90);
    }

    public void walk() {
        state = State.WALKING;
        head.getRightEye().setAngle(45);
        head.getLeftEye().setAngle(-45);
    }

    public void floating() {
        state = State.FLOATING;
        head.getRightEye().setAngle(0);
        head.getLeftEye().setAngle(0);
    }

}
