package dangine.entity.combat;

import dangine.collision.ColliderType;
import dangine.collision.GreatSwordCollider;
import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.subpower.CounterPower;
import dangine.entity.movement.AttackMode;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class GreatSword implements IsUpdateable, HasDrawable, IsGreatsword {

    public enum State {
        IDLE, HEAVY_CHARGE, HEAVY_SWING, LIGHT_CHARGE, LIGHT_SWING, HOLD_CHARGING, COUNTER_CHARGE, COUNTERING, RECOILING, RECOIL_RECOVERY, RECOVERING;
    }

    State state = State.IDLE;
    final int playerId;
    float timer = 0;
    static final float HEAVY_CHARGE_TIME = 500.0f;
    static final float HEAVY_COLLIDER_TIME = 300.0f;
    static final float LIGHT_CHARGE_TIME = 150.0f;
    // static final float LIGHT_CHARGE_TIME = 250.0f;
    static final float COUNTER_CHARGE_TIME = 250.0f;
    static final float HOLD_DECISION_TIME = 150.0f;
    static final float HEAVY_SWING_TIME = 700f;
    static final float LIGHT_SWING_TIME = 400.0f;
    static final float RECOIL_TIME = 1000.0f;
    static final float RECOIL_RECOVERY_TIME = 100.0f;
    static final float RECOVERY_TIME = 200.0f;
    final GreatSwordSceneGraph greatsword = new GreatSwordSceneGraph();
    final GreatSwordAnimator animator = new GreatSwordAnimator(greatsword);
    final GreatSwordCollider heavyHitbox;
    final GreatSwordCollider lightHitbox;
    final GreatSwordCollider counterHitbox;
    GreatswordInputProvider inputProvider;
    CounterPower counterPower = null;
    boolean willHeavySwing = false;
    boolean heavySwingTick = false;

    public GreatSword(int playerId, GreatswordInputProvider inputProvider) {
        this.playerId = playerId;
        this.inputProvider = inputProvider;
        heavyHitbox = new GreatSwordCollider(playerId, ColliderType.HEAVY);
        lightHitbox = new GreatSwordCollider(playerId, ColliderType.LIGHT);
        counterHitbox = new GreatSwordCollider(playerId, ColliderType.COUNTER);
    }

    @Override
    public void update() {
        if (heavyHitbox.isClashed() || lightHitbox.isClashed() || counterHitbox.isClashed()) {
            recoil();
        }
        switch (state) {
        case IDLE:
            break;
        case LIGHT_CHARGE:
        case HEAVY_CHARGE:
        case HOLD_CHARGING:
        case COUNTER_CHARGE:
        case RECOILING:
        case RECOIL_RECOVERY:
        case RECOVERING:
            timer += Utility.getGameTime().getDeltaTimeF();
            break;
        case LIGHT_SWING:
            timer += Utility.getGameTime().getDeltaTimeF();
            lightHitbox.updateSwing();
            break;
        case HEAVY_SWING:
            if (heavySwingTick) {
                greatsword.addHitbox(heavyHitbox.getDrawable());
                heavyHitbox.activate();
                heavyHitbox.updateSwing();
                heavySwingTick = false;
            }
            timer += Utility.getGameTime().getDeltaTimeF();
            if (timer > HEAVY_COLLIDER_TIME) {
                greatsword.removeHitbox(heavyHitbox.getDrawable());
                heavyHitbox.deactivate();
            } else {
                heavyHitbox.updateSwing();
            }
            break;
        case COUNTERING:
            timer += Utility.getGameTime().getDeltaTimeF();
            counterHitbox.updateSwing();
            break;
        }

        DangineSampleInput input = inputProvider.getInput(this);
        // DangineSampleInput input =
        // Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        AttackMode attackMode = Utility.getMatchParameters().getAttackMode();
        if (counterPower != null) {
            counterPower.update(input, this);
        }
        if (attackMode == AttackMode.BUTTONS) {
            if (input.isButtonTwo() && state == State.IDLE) {
                lightCharge();
            }
            if (input.isButtonOne() && state == State.IDLE) {
                heavyCharge();
            }
        } else if (attackMode == AttackMode.HOLD_TO_CHARGE) {
            if (input.isButtonOne() && state == State.IDLE) {
                holdCharge();
                willHeavySwing = true;
            }
            if (!input.isButtonOne() && state == State.HOLD_CHARGING) {
                float timeAlreadySpentCharging = timer;
                if (timer < HOLD_DECISION_TIME) {
                    willHeavySwing = false;
                    timer = timeAlreadySpentCharging;
                } else {
                    if (timer > LIGHT_CHARGE_TIME) {
                        heavyCharge();
                    }
                    timer = timeAlreadySpentCharging;
                    if (!willHeavySwing) {
                        Debugger.info("light charge");
                        lightCharge();
                    }
                }
            }
            if (input.isButtonOne() && state == State.HOLD_CHARGING) {
                if (timer > LIGHT_CHARGE_TIME) {
                    float timeAlreadySpentCharging = timer;
                    heavyCharge();
                    timer = timeAlreadySpentCharging;
                }
                if (timer > HEAVY_CHARGE_TIME) {
                    heavySwinging();
                }
            }
        }
        if (state == State.LIGHT_SWING && timer > LIGHT_SWING_TIME) {
            recovery();
        }
        if (state == State.HEAVY_SWING && timer > HEAVY_SWING_TIME) {
            recovery();
        }
        if (state == State.COUNTERING && timer > CounterPower.COUNTER_DURATION) {
            idle();
        }
        if (state == State.LIGHT_CHARGE && timer > LIGHT_CHARGE_TIME) {
            Debugger.info("light swing " + timer + " " + LIGHT_CHARGE_TIME);
            lightSwinging();
        }
        if (state == State.HEAVY_CHARGE && timer > HEAVY_CHARGE_TIME) {
            heavySwinging();
        }
        if (state == State.COUNTER_CHARGE && timer > COUNTER_CHARGE_TIME) {
            counter();
        }
        if (state == State.RECOILING && timer > RECOIL_TIME) {
            recoilRecovery();
        }
        if (state == State.RECOIL_RECOVERY && timer > RECOIL_RECOVERY_TIME) {
            idle();
        }
        if (state == State.RECOVERING && timer > RECOVERY_TIME) {
            idle();
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
        greatsword.removeHitbox(counterHitbox.getDrawable());
        heavyHitbox.deactivate();
        lightHitbox.deactivate();
        counterHitbox.deactivate();
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
        heavySwingTick = true;
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
        lightHitbox.updateSwing();
    }

    private void holdCharge() {
        state = State.HOLD_CHARGING;
        animator.holdCharging();
        timer = 0;
    }

    public void counterCharge() {
        counterHitbox.deactivate();
        greatsword.removeHitbox(counterHitbox.getDrawable());
        state = State.COUNTER_CHARGE;
        animator.counterCharge();
        timer = 0;
    }

    public void counter() {
        state = State.COUNTERING;
        animator.countering();
        counterHitbox.activate();
        counterHitbox.updateSwing();
        greatsword.addHitbox(counterHitbox.getDrawable());
        timer = 0;
    }

    public void recoil() {
        state = State.RECOILING;
        greatsword.removeHitbox(heavyHitbox.getDrawable());
        greatsword.removeHitbox(lightHitbox.getDrawable());
        greatsword.removeHitbox(counterHitbox.getDrawable());
        heavyHitbox.deactivate();
        lightHitbox.deactivate();
        counterHitbox.deactivate();
        animator.recoiling();
        timer = 0;
    }

    public void recoilRecovery() {
        state = State.RECOIL_RECOVERY;
        animator.recoilRecovering();
        timer = 0;
    }

    public void recovery() {
        greatsword.removeHitbox(heavyHitbox.getDrawable());
        greatsword.removeHitbox(lightHitbox.getDrawable());
        greatsword.removeHitbox(counterHitbox.getDrawable());
        heavyHitbox.deactivate();
        lightHitbox.deactivate();
        counterHitbox.deactivate();
        state = State.RECOVERING;
        animator.recovering();
        timer = 0;
    }

    public void destroy() {
        heavyHitbox.deactivate();
        lightHitbox.deactivate();
        counterHitbox.deactivate();
        greatsword.removeHitbox(heavyHitbox.getDrawable());
        greatsword.removeHitbox(lightHitbox.getDrawable());
        greatsword.removeHitbox(counterHitbox.getDrawable());
        Utility.getActiveScene().removeUpdateable(this);
    }

    @Override
    public boolean isSwinging() {
        return state == State.LIGHT_SWING || state == State.HEAVY_SWING || state == State.COUNTERING;
    }

    @Override
    public boolean isCharging() {
        return state == State.LIGHT_CHARGE || state == State.HEAVY_CHARGE || state == State.HOLD_CHARGING
                || state == State.COUNTER_CHARGE;
    }

    public GreatSwordSceneGraph getGreatsword() {
        return greatsword;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setCounterPower(CounterPower counterPower) {
        this.counterPower = counterPower;
    }

    public State getState() {
        return state;
    }

    public CounterPower getCounterPower() {
        return counterPower;
    }
}
