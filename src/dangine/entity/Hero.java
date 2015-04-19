package dangine.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import dangine.bots.DangineBot;
import dangine.collision.GreatSwordCounterCollider;
import dangine.debugger.Debugger;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.GreatSword;
import dangine.entity.combat.subpower.DashPower;
import dangine.entity.combat.subpower.ProjectilePower;
import dangine.entity.gameplay.DefeatEvent;
import dangine.entity.movement.HeroFacing;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.DefeatType;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class Hero implements IsUpdateable, HasDrawable {

    final float SPEED = 0.3f;

    int playerId = 0;
    final Vector2f position = new Vector2f(200, 200);
    final BloxSceneGraph draw = new BloxSceneGraph();
    final BloxAnimator animator = new BloxAnimator(draw);
    final HeroMovement movement = new HeroMovement();
    final HeroFacing facing = new HeroFacing();
    final int HITBOX_SIZE = 20;
    final CombatEvent onHit;
    final CombatEventHitbox hitbox;
    boolean immunity = false;
    boolean destroyed = false;
    GreatSword activeWeapon = null;
    DashPower dashPower = null;
    ProjectilePower projectilePower = null;

    public Hero(int playerId) {
        this.playerId = playerId;
        onHit = new CombatEvent(playerId, position, HITBOX_SIZE, getOnHitBy(), this);
        hitbox = new CombatEventHitbox(onHit);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
        Color color = Utility.getMatchParameters().getPlayerColor(getPlayerId());
        BloxColorer.color(draw, color);
    }

    @Override
    public void update() {
        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        if (input.isUp() || input.isDown()) {
            animator.floating();
        } else if (input.isLeft() || input.isRight()) {
            animator.walk();
        } else {
            animator.idle();
        }
        if (dashPower != null) {
            dashPower.update(input, getMovement(), getPosition());
        }
        if (projectilePower != null) {
            projectilePower.update(input, getMovement(), getPosition());
        }
        movement.moveHero(this.position, input, activeWeapon);
        draw.getBase().setPosition(position);
        facing.updateFacing(this, input);
        animator.update();

        onHit.setPosition(position);
        hitbox.setPosition(position);
        hitbox.setPosition(position.x - HITBOX_SIZE, position.y - HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(onHit);
    }

    public void destroy() {
        destroy(DefeatType.randomSwordEffect());
    }

    public void destroy(DefeatType defeatType) {
        if (destroyed) {
            return;
        }
        destroyed = true;
        if (activeWeapon != null) {
            activeWeapon.destroy();
        }

        Vector2f absolutePosition = new Vector2f();
        absolutePosition = ScreenUtility.getWorldPosition(draw.getBody(), absolutePosition);
        defeatType.applyEffect(absolutePosition.x, absolutePosition.y, playerId);
        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getCameraNode().removeChild(this.getDrawable());
        Utility.getActiveScene().getCameraNode().removeChild(hitbox.getDrawable());
        Utility.getActiveScene().getMatchOrchestrator().addEvent(new DefeatEvent(playerId));

        Debugger.info("destroying hero " + playerId + " " + this);
    }

    public boolean equipWeapon(GreatSword greatsword) {
        BloxColorer.color(greatsword.getGreatsword(), getBlox().getBodyShape().getColor());
        draw.getBody().addChild(greatsword.getDrawable());
        draw.removeHands();
        activeWeapon = greatsword;
        return true;
    }

    public Method<CombatEvent> getOnHitBy() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof GreatSwordCounterCollider || arg.getCreator() instanceof Hero
                        || arg.getCreator() instanceof DangineBot || arg.getCreator() instanceof Vortex
                        || arg.getCreator() instanceof Bouncer) {
                    return;
                }
                if (!isImmunity()) {
                    destroy();
                }
            }
        };
    }

    @Override
    public IsDrawable getDrawable() {
        return draw.getBase();
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public int getPlayerId() {
        return playerId;
    }

    public HeroMovement getMovement() {
        return movement;
    }

    public Vector2f getPosition() {
        return position;
    }

    public boolean isImmunity() {
        return immunity;
    }

    public void setImmunity(boolean immunity) {
        this.immunity = immunity;
    }

    public BloxSceneGraph getBlox() {
        return draw;
    }

    public void setDashPower(DashPower dashPower) {
        this.dashPower = dashPower;
    }

    public void setProjectilePower(ProjectilePower projectilePower) {
        this.projectilePower = projectilePower;
    }

    public GreatSword getActiveWeapon() {
        return activeWeapon;
    }

    public BloxAnimator getAnimator() {
        return animator;
    }
}
