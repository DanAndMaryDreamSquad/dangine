package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.utility.Oscillator;

public class BloxAnimator implements IsUpdateable {

    enum State {
        FLOATING, WALKING, IDLE;
    }

    final BloxSceneGraph blox;
    final BloxHeadAnimator head;
    final BloxLegAnimator legs;
    final BloxHandAnimator hands;
    final Oscillator floatOscillator = new Oscillator(-2, 2, 3000);
    State state;
    int facingDirection = 1;

    public BloxAnimator(BloxSceneGraph blox) {
        this.blox = blox;
        head = new BloxHeadAnimator(blox.getHead());
        legs = new BloxLegAnimator(blox);
        hands = new BloxHandAnimator(blox);
        floating();
    }

    @Override
    public void update() {
        head.update();
        legs.update();
        hands.update();
        switch (state) {
        case IDLE:
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

    public void floating() {
        state = State.FLOATING;
        legs.floating();
        head.floating();
        hands.floating();
    }

    public void idle() {
        state = State.IDLE;
        legs.idle();
        head.idle();
        hands.idle();
    }

    public void updateFacing(int direction) {
        facingDirection = direction;
        if (direction == 1) {
            blox.getBase().setHorzitontalFlip(1);
        }
        if (direction == -1) {
            blox.getBase().setHorzitontalFlip(-1);
        }
    }

    public void flipFacingDirection() {
        updateFacing(facingDirection * -1);
    }

    public int getFacingDirection() {
        return facingDirection;
    }

}
