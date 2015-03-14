package dangine.entity.combat;

import dangine.entity.IsUpdateable;

public class GreatSwordAnimator implements IsUpdateable {

    enum State {
        IDLE, SWINGING;
    }

    State state = State.IDLE;

    final GreatSwordSceneGraph greatsword;
    float angle = 0;

    public GreatSwordAnimator(GreatSwordSceneGraph greatsword) {
        this.greatsword = greatsword;
        idle();
    }

    @Override
    public void update() {

    }

    public void idle() {
        state = State.IDLE;
        float scale = greatsword.getBase().getScale().x;
        greatsword.getBase().setPosition(-12 * scale, -28 * scale);
        greatsword.getBase().setCenterOfRotation(7 * scale, 30 * scale);
        greatsword.getBase().setAngle(60.0f);

        greatsword.getLeftArm().setPosition(4, 26);
        greatsword.getLeftArm().setZValue(-1.0f);
        greatsword.getRightArm().setPosition(6, 24);
        greatsword.getRightArm().setZValue(-1.0f);
    }

    public void swinging() {

    }
}
