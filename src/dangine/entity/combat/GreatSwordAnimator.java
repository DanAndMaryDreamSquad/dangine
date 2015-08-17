package dangine.entity.combat;

import dangine.animation.GreatSwordAnimationKeyframes;
import dangine.animation.Orientation;
import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.IsUpdateable;
import dangine.utility.MathUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class GreatSwordAnimator implements IsUpdateable {

    enum State {
        IDLE, HEAVY_CHARGE, HEAVY_SWINGING, STAB_CHARGE, STAB_SWINGING, ONE_HAND_CHARGE, ONE_HAND_SWINGING, COUNTER_CHARGE, COUNTERING, HOLD_CHARGE, RECOILING, RECOIL_RECOVERING, RECOVERING;
    }

    State state = State.IDLE;

    Vector2f absolutePosition = new Vector2f(0, 0);
    Vector2f stabDirection = new Vector2f(260.0f - 90.0f);
    final GreatSwordSceneGraph greatsword;
    float angle = 0;
    float timer = 0;
    Orientation recoilStartSnapshot = null;

    public GreatSwordAnimator(GreatSwordSceneGraph greatsword) {
        this.greatsword = greatsword;
        greatsword.getLeftArm().setPosition(4, 26);
        greatsword.getLeftArm().setZValue(-1.0f);
        greatsword.getRightArm().setPosition(6, 24);
        greatsword.getRightArm().setZValue(-1.0f);
        idle();
    }

    @Override
    public void update() {
        switch (state) {
        case RECOVERING:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percentBackToIdle = (timer / GreatSword.RECOVERY_TIME);
            Orientation.applyShortestPath(greatsword.getSword(), recoilStartSnapshot,
                    GreatSwordAnimationKeyframes.IDLE.getOrientation(), greatsword.getSword().getScale().x,
                    percentBackToIdle);
            break;
        case RECOILING:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percentRecoil = (timer / GreatSword.RECOIL_TIME);
            Orientation.apply(greatsword.getSword(), recoilStartSnapshot,
                    GreatSwordAnimationKeyframes.RECOILING.getOrientation(), greatsword.getSword().getScale().x,
                    MathUtility.logFunction(percentRecoil));
            break;
        case RECOIL_RECOVERING:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percentRecovery = (timer / GreatSword.RECOIL_RECOVERY_TIME);
            Orientation.apply(greatsword.getSword(), GreatSwordAnimationKeyframes.RECOILING.getOrientation(),
                    GreatSwordAnimationKeyframes.IDLE.getOrientation(), greatsword.getSword().getScale().x,
                    percentRecovery);
            break;
        case STAB_CHARGE:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percentStab = (timer / GreatSword.LIGHT_CHARGE_TIME);
            Orientation.apply(greatsword.getSword(), GreatSwordAnimationKeyframes.HOLD_MIDWAY.getOrientation(),
                    GreatSwordAnimationKeyframes.LIGHT_CHARGE.getOrientation(), greatsword.getSword().getScale().x,
                    percentStab);
            break;
        case ONE_HAND_CHARGE:
        case HEAVY_CHARGE:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percentHeavy = (timer / GreatSword.HEAVY_CHARGE_TIME);
            Orientation.apply(greatsword.getSword(), GreatSwordAnimationKeyframes.HOLD_MIDWAY.getOrientation(),
                    GreatSwordAnimationKeyframes.HEAVY_CHARGE.getOrientation(), greatsword.getSword().getScale().x,
                    percentHeavy);
            break;
        case COUNTER_CHARGE:
        case COUNTERING:
            break;
        case HOLD_CHARGE:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percentHold = (timer / GreatSword.HOLD_DECISION_TIME);
            Orientation.apply(greatsword.getSword(), GreatSwordAnimationKeyframes.IDLE.getOrientation(),
                    GreatSwordAnimationKeyframes.HOLD_MIDWAY.getOrientation(), greatsword.getSword().getScale().x,
                    percentHold);
            break;
        case IDLE:
            break;
        case STAB_SWINGING:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percent = (timer / GreatSword.LIGHT_SWING_TIME);
            Orientation.apply(greatsword.getSword(), GreatSwordAnimationKeyframes.LIGHT_SWING_START.getOrientation(),
                    GreatSwordAnimationKeyframes.LIGHT_SWING_END.getOrientation(), greatsword.getSword().getScale().x,
                    MathUtility.logFunction(percent));
            break;
        case ONE_HAND_SWINGING:
            break;
        case HEAVY_SWINGING:
            timer += Utility.getGameTime().getDeltaTimeF();
            float percentValue = (timer / GreatSword.HEAVY_SWING_TIME);

            Orientation.apply(greatsword.getSword(), GreatSwordAnimationKeyframes.HEAVY_SWING_START.getOrientation(),
                    GreatSwordAnimationKeyframes.HEAVY_SWING_END.getOrientation(), greatsword.getSword().getScale().x,
                    MathUtility.logFunction(percentValue));
            break;
        }
    }

    public void idle() {
        greatsword.disableSwingBlur();
        greatsword.disableStabBlur();
        timer = 0;
        state = State.IDLE;
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.IDLE.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void heavyCharge() {
        timer = 0;
        SoundPlayer.play(SoundEffect.CHARGE_SWING_HEAVY);
        state = State.HEAVY_CHARGE;
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.HEAVY_CHARGE.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void heavySwinging() {
        greatsword.enableSwingBlur();
        timer = 0;
        SoundPlayer.play(SoundEffect.START_SWING_HEAVY);
        state = State.HEAVY_SWINGING;
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.HEAVY_SWING_START.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void stabCharge() {
        timer = 0;
        SoundPlayer.play(SoundEffect.CHARGE_SWING_LIGHT);
        state = State.STAB_CHARGE;
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.LIGHT_CHARGE.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void stabSwinging() {
        greatsword.enableStabBlur();
        timer = 0;
        SoundPlayer.play(SoundEffect.START_SWING_LIGHT);
        state = State.STAB_SWINGING;
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.LIGHT_SWING_START.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void counterCharge() {
        timer = 0;
        SoundPlayer.play(SoundEffect.COUNTER_START);
        state = State.COUNTER_CHARGE;
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.COUNTER_CHARGE.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void countering() {
        timer = 0;
        SoundPlayer.play(SoundEffect.COUNTER_START);
        state = State.COUNTERING;
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.COUNTER_SWING.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void holdCharging() {
        timer = 0;
        state = State.HOLD_CHARGE;
        SoundPlayer.play(SoundEffect.CHARGE_SWING_LIGHT);
        float scale = greatsword.getSword().getScale().x;
        GreatSwordAnimationKeyframes.HOLD_MIDWAY.getOrientation().apply(greatsword.getSword(), scale);
    }

    public void recoiling() {
        greatsword.disableSwingBlur();
        greatsword.disableStabBlur();
        timer = 0;
        state = State.RECOILING;
        SoundPlayer.play(SoundEffect.CHARGE_SWING_LIGHT);
        float scale = greatsword.getSword().getScale().x;
        Vector2f pos = new Vector2f(greatsword.getSword().getPosition().x / scale,
                greatsword.getSword().getPosition().y / scale);
        Vector2f center = new Vector2f(greatsword.getSword().getCenterOfRotation().x / scale, greatsword.getSword()
                .getCenterOfRotation().y / scale);
        recoilStartSnapshot = new Orientation(pos, center, greatsword.getSword().getAngle());
    }

    public void recoilRecovering() {
        timer = 0;
        state = State.RECOIL_RECOVERING;
        SoundPlayer.play(SoundEffect.CHARGE_SWING_LIGHT);
        float scale = greatsword.getSword().getScale().x;
        Vector2f pos = new Vector2f(greatsword.getSword().getPosition().x / scale,
                greatsword.getSword().getPosition().y / scale);
        Vector2f center = new Vector2f(greatsword.getSword().getCenterOfRotation().x / scale, greatsword.getSword()
                .getCenterOfRotation().y / scale);
        recoilStartSnapshot = new Orientation(pos, center, greatsword.getSword().getAngle());
    }

    public void recovering() {
        greatsword.disableSwingBlur();
        greatsword.disableStabBlur();
        timer = 0;
        state = State.RECOVERING;
        SoundPlayer.play(SoundEffect.CHARGE_SWING_LIGHT);
        float scale = greatsword.getSword().getScale().x;
        Vector2f pos = new Vector2f(greatsword.getSword().getPosition().x / scale,
                greatsword.getSword().getPosition().y / scale);
        Vector2f center = new Vector2f(greatsword.getSword().getCenterOfRotation().x / scale, greatsword.getSword()
                .getCenterOfRotation().y / scale);
        recoilStartSnapshot = new Orientation(pos, center, greatsword.getSword().getAngle());
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

    public State getState() {
        return state;
    }
}
