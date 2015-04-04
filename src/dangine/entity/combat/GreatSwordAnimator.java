package dangine.entity.combat;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.IsUpdateable;
import dangine.utility.Utility;

public class GreatSwordAnimator implements IsUpdateable {

    enum State {
        IDLE, HEAVY_CHARGE, HEAVY_SWINGING, STAB_CHARGE, STAB_SWINGING, ONE_HAND_CHARGE, ONE_HAND_SWINGING, COUNTER_CHARGE, COUNTERING;
    }

    State state = State.IDLE;

    final float HEAVY_SWING_SPEED = 0.56f;
    public final float HEAVY_SWING_TIME = 390.0f / HEAVY_SWING_SPEED;
    public final float LIGHT_SWING_TIME = 400.0f;
    Vector2f absolutePosition = new Vector2f(0, 0);
    Vector2f stabDirection = new Vector2f(260.0f - 90.0f).normalise();
    final GreatSwordSceneGraph greatsword;
    float angle = 0;
    float timer = 0;

    public GreatSwordAnimator(GreatSwordSceneGraph greatsword) {
        this.greatsword = greatsword;
        idle();
    }

    @Override
    public void update() {
        float boost = 0.0f;
        float shift = 0.0f;
        Vector2f position = null;
        switch (state) {
        case STAB_CHARGE:
        case ONE_HAND_CHARGE:
        case HEAVY_CHARGE:
        case COUNTER_CHARGE:
        case COUNTERING:
        case IDLE:
            break;
        case STAB_SWINGING:
            timer += Utility.getGameTime().getDeltaTimeF();
            shift = Utility.getGameTime().getDeltaTimeF() * 0.06f;
            boost = (2.0f - (timer / 250f));
            shift = shift * boost;
            float scale = greatsword.getSword().getScale().x;
            position = greatsword.getSword().getPosition();
            position.x += shift * stabDirection.x * scale;
            position.y += shift * stabDirection.y * scale;
            break;
        case ONE_HAND_SWINGING:
            break;
        case HEAVY_SWINGING:
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

    public void heavyCharge() {
        state = State.HEAVY_CHARGE;
        angle = 120.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(-12 * scale, -40 * scale);
        greatsword.getSword().setAngle(angle);

    }

    public void heavySwinging() {
        state = State.HEAVY_SWINGING;
        angle = 60.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(-8 * scale, -36 * scale);
        greatsword.getSword().setCenterOfRotation(12 * scale, 36 * scale);
        greatsword.getSword().setAngle(angle);
    }

    public void stabCharge() {
        state = State.STAB_CHARGE;
        angle = 260.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(12 * scale, -32 * scale);
        greatsword.getSword().setAngle(angle);
    }

    public void stabSwinging() {
        state = State.STAB_SWINGING;
        angle = 260.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(12 * scale, -32 * scale);
        greatsword.getSword().setAngle(angle);
        timer = 0;
    }

    public void counterCharge() {
        state = State.COUNTER_CHARGE;
        angle = 220.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition(12 * scale, -32 * scale);
        greatsword.getSword().setAngle(angle);
    }

    public void countering() {
        state = State.COUNTERING;
        angle = 120.0f;
        float scale = greatsword.getSword().getScale().x;
        greatsword.getSword().setPosition((-12 - 8) * scale, (-32 - 4) * scale);
        greatsword.getSword().setAngle(angle);
        timer = 0;
    }

    public void oneHandCharge() {
        // TODO potentially needs keyframes to work
        // state = State.ONE_HAND_CHARGE;
        // angle = 20.0f;
        // float scale = greatsword.getSword().getScale().x;
        // greatsword.getSword().setPosition(6 * scale, -26 * scale);
        // greatsword.getSword().setAngle(angle);
        //
        // greatsword.getSword().removeChild(greatsword.getLeftArm());
        // greatsword.getBase().addChild(greatsword.getLeftArm());
        // greatsword.getLeftArm().setPosition(-8, 0);
    }

    public void oneHandSwinging() {
        // TODO potentially needs keyframes to work
    }
}
