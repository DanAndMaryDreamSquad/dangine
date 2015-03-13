package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.utility.Oscillator;

public class BloxAnimator implements IsUpdateable {

    enum State {
        IDLE, WALKING, FLOATING;
    }

    final BloxSceneGraph blox;
    final BloxHeadAnimator head;
    final BloxLegAnimator legs;
    final BloxHandAnimator hands;
    final Oscillator floatOscillator = new Oscillator(-2, 2, 3000);
    State state;

    public BloxAnimator(BloxSceneGraph blox) {
        this.blox = blox;
        head = new BloxHeadAnimator(blox.getHead());
        legs = new BloxLegAnimator(blox);
        hands = new BloxHandAnimator(blox);
        idle();
    }

    @Override
    public void update() {
        head.update();
        legs.update();
        hands.update();
        switch (state) {
        case FLOATING:
            float value = floatOscillator.update();
            blox.getBody().setYPosition(value);
            break;

        default:
            break;
        }
    }

    public void walk() {
        state = State.WALKING;
        legs.walk();
        head.walk();
        hands.walk();
    }

    public void idle() {
        state = State.IDLE;
        legs.idle();
        head.idle();
        hands.idle();
    }

    public void floating() {
        state = State.FLOATING;
        legs.floating();
        head.floating();
        hands.floating();
    }

}
