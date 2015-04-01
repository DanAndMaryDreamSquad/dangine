package dangine.bots;

import dangine.collision.GreatSwordCollider;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.GreatSwordAnimator;
import dangine.entity.combat.GreatSwordSceneGraph;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class BotGreatsword implements IsUpdateable, HasDrawable {

    enum State {
        IDLE, CHARGE, SWINGING;
    }

    State state = State.IDLE;
    float timer = 0;
    static final float CHARGE_TIME = 1000.0f;
    final GreatSwordSceneGraph greatsword = new GreatSwordSceneGraph();
    final GreatSwordAnimator animator = new GreatSwordAnimator(greatsword);
    final GreatSwordCollider hitbox;
    DangineBotLogic logic = new DangineBotLogic();

    public BotGreatsword() {
        hitbox = new GreatSwordCollider(-1);
    }

    @Override
    public void update() {
        switch (state) {
        case IDLE:
            break;
        case CHARGE:
            timer += Utility.getGameTime().getDeltaTimeF();
            break;
        case SWINGING:
            timer += Utility.getGameTime().getDeltaTimeF();
            hitbox.update();
            break;
        }

        DangineSampleInput input = logic.getWhatDoWithWeapon(this);
        if (input.isButtonOne() && state == State.IDLE) {
            charge();
        }
        if (input.isButtonTwo()) {
            idle();
        }
        if (state == State.SWINGING && timer > animator.HEAVY_SWING_TIME) {
            idle();
        }
        if (state == State.CHARGE && timer > CHARGE_TIME) {
            swinging();
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
        greatsword.removeHitbox(hitbox);
    }

    public void charge() {
        state = State.CHARGE;
        animator.heavyCharge();
        timer = 0;
    }

    public void swinging() {
        state = State.SWINGING;
        animator.heavySwinging();
        timer = 0;
        greatsword.addHitbox(hitbox);
        hitbox.update();
    }

    public void destroy() {
        greatsword.removeHitbox(hitbox);
        Utility.getActiveScene().removeUpdateable(this);
    }

    public GreatSwordSceneGraph getGreatsword() {
        return greatsword;
    }
}
