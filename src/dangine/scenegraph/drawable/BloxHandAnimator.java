package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Oscillator;

public class BloxHandAnimator implements IsUpdateable {

    enum State {
        IDLE, WALKING, FLOATING;
    }

    final SceneGraphNode leftHand;
    final SceneGraphNode rightHand;
    final Oscillator handOscillator = new Oscillator(-2, 2, 4000);
    State state = State.IDLE;

    public BloxHandAnimator(BloxSceneGraph blox) {
        this.leftHand = blox.leftArm;
        this.rightHand = blox.rightArm;
    }

    @Override
    public void update() {
        switch (state) {
        case FLOATING:
            float value = handOscillator.update();
            this.leftHand.setYPosition(value);
            this.rightHand.setYPosition(value);
            break;
        default:
            break;
        }
    }

    public void floating() {
        this.state = State.FLOATING;
    }

    public void idle() {
        this.state = State.IDLE;
    }

    public void walk() {
        this.state = State.WALKING;
    }

}
