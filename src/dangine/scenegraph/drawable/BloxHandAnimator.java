package dangine.scenegraph.drawable;

import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Oscillator;

public class BloxHandAnimator implements IsUpdateable {

    enum State {
        FLOATING, WALKING, IDLE;
    }

    final SceneGraphNode leftHand;
    final SceneGraphNode rightHand;
    final Oscillator handOscillator = new Oscillator(-2, 2, 4000);
    State state = State.FLOATING;

    public BloxHandAnimator(BloxSceneGraph blox) {
        this.leftHand = blox.leftArm;
        this.rightHand = blox.rightArm;
    }

    @Override
    public void update() {
        switch (state) {
        case IDLE:
            float value = handOscillator.update();
            this.leftHand.setYPosition(value);
            this.rightHand.setYPosition(value);
            break;
        default:
            break;
        }
    }

    public void idle() {
        this.state = State.IDLE;
    }

    public void floating() {
        this.state = State.FLOATING;
    }

    public void walk() {
        this.state = State.WALKING;
    }

}
