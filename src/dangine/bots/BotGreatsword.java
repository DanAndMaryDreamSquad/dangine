package dangine.bots;

import dangine.collision.GreatSwordHeavyCollider;
import dangine.collision.GreatSwordLightCollider;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.GreatSwordAnimator;
import dangine.entity.combat.GreatSwordSceneGraph;
import dangine.entity.combat.IsGreatsword;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class BotGreatsword implements IsUpdateable, HasDrawable, IsGreatsword {

    enum State {
        IDLE, HEAVY_CHARGE, HEAVY_SWING, LIGHT_CHARGE, LIGHT_SWING;
    }

    State state = State.IDLE;
    float timer = 0;
    static final float HEAVY_CHARGE_TIME = 500.0f;
    static final float LIGHT_CHARGE_TIME = 250.0f;
    static final float CHARGE_TIME = 1000.0f;
    final GreatSwordSceneGraph greatsword = new GreatSwordSceneGraph();
    final GreatSwordAnimator animator = new GreatSwordAnimator(greatsword);
    final GreatSwordHeavyCollider heavyHitbox;
    final GreatSwordLightCollider lightHitbox;
    DangineBotLogic logic = new DangineBotLogic();

    public BotGreatsword() {
        heavyHitbox = new GreatSwordHeavyCollider(-1);
        lightHitbox = new GreatSwordLightCollider(-1);
    }

    @Override
    public void update() {
        if (heavyHitbox.isClashed() || lightHitbox.isClashed()) {
            idle();
        }
        switch (state) {
        case IDLE:
            break;
        case LIGHT_CHARGE:
        case HEAVY_CHARGE:
            timer += Utility.getGameTime().getDeltaTimeF();
            break;
        case LIGHT_SWING:
            timer += Utility.getGameTime().getDeltaTimeF();
            lightHitbox.update();
            break;
        case HEAVY_SWING:
            timer += Utility.getGameTime().getDeltaTimeF();
            heavyHitbox.update();
            break;
        }

        DangineSampleInput input = logic.getWhatDoWithWeapon(this);
        if (input.isButtonTwo() && state == State.IDLE) {
            lightCharge();
        }
        if (input.isButtonOne() && state == State.IDLE) {
            heavyCharge();
        }
        if (state == State.LIGHT_SWING && timer > animator.LIGHT_SWING_TIME) {
            idle();
        }
        if (state == State.HEAVY_SWING && timer > animator.HEAVY_SWING_TIME) {
            idle();
        }
        if (state == State.LIGHT_CHARGE && timer > LIGHT_CHARGE_TIME) {
            lightSwinging();
        }
        if (state == State.HEAVY_CHARGE && timer > HEAVY_CHARGE_TIME) {
            heavySwinging();
        }
        animator.update();
    }

    @Override
    public IsDrawable getDrawable() {
        return greatsword.getDrawable();
    }

    public void idle() {
        state = State.IDLE;
        animator.idle();
        greatsword.removeHitbox(heavyHitbox.getDrawable());
        greatsword.removeHitbox(lightHitbox.getDrawable());
        heavyHitbox.deactivate();
        lightHitbox.deactivate();
    }

    public void heavyCharge() {
        state = State.HEAVY_CHARGE;
        animator.heavyCharge();
        timer = 0;
    }

    public void heavySwinging() {
        state = State.HEAVY_SWING;
        animator.heavySwinging();
        timer = 0;
        greatsword.addHitbox(heavyHitbox.getDrawable());
        heavyHitbox.activate();
        heavyHitbox.update();
    }

    public void lightCharge() {
        state = State.LIGHT_CHARGE;
        animator.stabCharge();
        timer = 0;
    }

    public void lightSwinging() {
        state = State.LIGHT_SWING;
        animator.stabSwinging();
        timer = 0;
        greatsword.addHitbox(lightHitbox.getDrawable());
        lightHitbox.activate();
        lightHitbox.update();
    }

    public void destroy() {
        heavyHitbox.deactivate();
        lightHitbox.deactivate();
        greatsword.removeHitbox(heavyHitbox.getDrawable());
        greatsword.removeHitbox(lightHitbox.getDrawable());
        Utility.getActiveScene().removeUpdateable(this);
    }

    public GreatSwordSceneGraph getGreatsword() {
        return greatsword;
    }

    public State getState() {
        return state;
    }

    public boolean isSwinging() {
        return state == State.LIGHT_SWING || state == State.HEAVY_SWING;
    }

    public boolean isCharging() {
        return state == State.LIGHT_CHARGE || state == State.HEAVY_CHARGE;
    }

}
