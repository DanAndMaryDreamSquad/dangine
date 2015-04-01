package dangine.entity.combat;

import dangine.collision.GreatSwordCollider;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class GreatSword implements IsUpdateable, HasDrawable {

    enum State {
        IDLE, HEAVY_CHARGE, HEAVY_SWING, LIGHT_CHARGE, LIGHT_SWING;
    }

    State state = State.IDLE;
    final int playerId;
    float timer = 0;
    static final float HEAVY_CHARGE_TIME = 500.0f;
    static final float LIGHT_CHARGE_TIME = 250.0f;
    final GreatSwordSceneGraph greatsword = new GreatSwordSceneGraph();
    final GreatSwordAnimator animator = new GreatSwordAnimator(greatsword);
    final GreatSwordCollider hitbox;

    public GreatSword() {
        playerId = 0;
        hitbox = new GreatSwordCollider(playerId);
    }

    public GreatSword(int playerId) {
        this.playerId = playerId;
        hitbox = new GreatSwordCollider(playerId);
    }

    @Override
    public void update() {
        switch (state) {
        case IDLE:
            break;
        case LIGHT_CHARGE:
        case HEAVY_CHARGE:
            timer += Utility.getGameTime().getDeltaTimeF();
            break;
        case LIGHT_SWING:
        case HEAVY_SWING:
            timer += Utility.getGameTime().getDeltaTimeF();
            hitbox.update();
            break;
        }

        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
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
        greatsword.removeHitbox(hitbox);
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
        greatsword.addHitbox(hitbox);
        hitbox.update();
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
