package dangine.entity.combat;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.IsUpdateable;
import dangine.entity.visual.SparkTrail;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class GreatSwordAnimator implements IsUpdateable {

    enum State {
        IDLE, CHARGE, SWINGING;
    }

    State state = State.IDLE;

    final float SWING_SPEED = 0.56f;
    final float SWING_TIME = 390.0f / SWING_SPEED;
    Vector2f absolutePosition = new Vector2f(0, 0);
    final GreatSwordSceneGraph greatsword;
    float angle = 0;

    public GreatSwordAnimator(GreatSwordSceneGraph greatsword) {
        this.greatsword = greatsword;
        idle();
    }

    private void createSpark() {
        absolutePosition = ScreenUtility.getWorldPosition(greatsword.getSword(), absolutePosition);
        SparkTrail spark = new SparkTrail(absolutePosition.x, absolutePosition.y);
        Utility.getActiveScene().getCameraNode().addChild(spark.getDrawable());
        Utility.getActiveScene().addUpdateable(spark);
    }

    @Override
    public void update() {
        switch (state) {
        case SWINGING:
            float increment = Utility.getGameTime().getDeltaTimeF() * 0.56f;

            angle -= increment;
            greatsword.getSword().setAngle(angle);
            if (angle < -330) {
                idle();
            }
            break;
        }
    }

    public void idle() {
        state = State.IDLE;
        angle = 60.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(-12 * scale, -28 * scale);
        greatsword.getSword().setCenterOfRotation(7 * scale, 30 * scale);
        greatsword.getSword().setAngle(angle);

        greatsword.getLeftArm().setPosition(4, 26);
        greatsword.getLeftArm().setZValue(-1.0f);
        greatsword.getRightArm().setPosition(6, 24);
        greatsword.getRightArm().setZValue(-1.0f);
    }

    public void charge() {
        state = State.CHARGE;
        angle = 120.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(-12 * scale, -40 * scale);
        greatsword.getSword().setAngle(angle);

    }

    public void swinging() {
        state = State.SWINGING;
        angle = 60.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(-8 * scale, -36 * scale);
        greatsword.getSword().setCenterOfRotation(12 * scale, 36 * scale);
        greatsword.getSword().setAngle(angle);

        createSpark();
    }
}
