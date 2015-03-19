package dangine.entity.combat;

import dangine.collision.GreatSwordCollider;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class GreatSword implements IsUpdateable, HasDrawable {

    enum State {
        IDLE, CHARGE, SWINGING;
    }

    State state = State.IDLE;
    final int playerId;
    float timer = 0;
    static final float CHARGE_TIME = 0.0f;
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
        case CHARGE:
            timer += Utility.getGameTime().getDeltaTimeF();
            break;
        case SWINGING:
            timer += Utility.getGameTime().getDeltaTimeF();
            hitbox.update();
            break;
        }

        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        if (input.isButtonOne() && state == State.IDLE) {
            charge();
        }
        if (input.isButtonTwo()) {
            idle();
        }
        if (state == State.SWINGING && timer > animator.SWING_TIME) {
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
        animator.charge();
        timer = 0;
    }

    public void swinging() {
        state = State.SWINGING;
        animator.swinging();
        timer = 0;
        greatsword.addHitbox(hitbox);
        Utility.getActiveScene().addUpdateable(hitbox);
        Utility.getActiveScene().removeUpdateable(hitbox);
    }
}
