package dangine.scenegraph.drawable;

import java.util.Random;

import dangine.entity.IsUpdateable;
import dangine.utility.Oscillator;
import dangine.utility.Utility;

public class BloxHeadAnimator implements IsUpdateable {

    final float HEAD_RATE = 10000f;
    final Oscillator oscillator = new Oscillator(-30, 30, HEAD_RATE);
    Random random = new Random();
    float blinkStart, blinkEnd, timer, angle, eyeYValue;

    enum State {
        IDLE, WALKING, FLOATING;
    }

    State state = State.IDLE;

    final BloxHeadSceneGraph head;

    public BloxHeadAnimator(BloxHeadSceneGraph head) {
        this.head = head;
        eyeYValue = head.getRightEye().getPosition().y;
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();

        if (timer >= blinkEnd) {
            resetBlink();

            // head.getRightEye().setAngle(0);
            // head.getLeftEye().setAngle(0);

            head.getRightEye().setScale(1, 1);
            head.getLeftEye().setScale(1, 1);
            head.getRightEye().setPosition(head.getRightEye().getPosition().x, eyeYValue);
            head.getLeftEye().setPosition(head.getLeftEye().getPosition().x, eyeYValue);

        }

        if (timer >= blinkStart && timer < blinkEnd) {

            // head.getRightEye().setAngle(90);
            // head.getLeftEye().setAngle(90);

            head.getRightEye().setScale(1, 0.3f);
            head.getLeftEye().setScale(1, 0.3f);
            head.getRightEye().setPosition(head.getRightEye().getPosition().x, eyeYValue * 1.3f);
            head.getLeftEye().setPosition(head.getLeftEye().getPosition().x, eyeYValue * 1.3f);
        }

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
    }

    public void walk() {
        state = State.WALKING;
    }

    public void floating() {
        state = State.FLOATING;
    }

    public void resetBlink() {
        timer = 0;

        blinkStart = (random.nextInt(3) + 3) * 1000;
        blinkEnd = random.nextInt(3) * 200 + blinkStart;
    }

}
