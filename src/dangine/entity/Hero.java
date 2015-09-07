package dangine.entity;

import java.util.List;

import org.lwjgl.util.Color;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.collision.ColliderType;
import dangine.collision.GreatSwordColliderData;
import dangine.debugger.Debugger;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.CombatResolver;
import dangine.entity.combat.CombatResolver.EventType;
import dangine.entity.combat.GreatSword;
import dangine.entity.combat.GreatSword.State;
import dangine.entity.combat.subpower.DashPower;
import dangine.entity.combat.subpower.ProjectilePower;
import dangine.entity.combat.subpower.ProjectileShot;
import dangine.entity.gameplay.DefeatEvent;
import dangine.entity.gameplay.LifeIndicator;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.entity.movement.HeroFacing;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.DefeatType;
import dangine.entity.visual.FinalDefeatVisual;
import dangine.entity.visual.ScreenFlashVisual;
import dangine.entity.visual.SlashVisual;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class Hero implements IsUpdateable, HasDrawable {

    final float SPEED = 0.3f;

    int playerId = 0;
    final Vector2f position = new Vector2f(200, 200);
    // final BloxSceneGraph draw = new BloxSceneGraph();
    final BloxSceneGraph draw;
    final BloxAnimator animator;
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
        draw = Utility.getActiveScene().getBloxPool().get(playerId);
        animator = new BloxAnimator(draw);
        int colliderId = MatchType.getColliderId(playerId);
        onHit = new CombatEvent(colliderId, position, HITBOX_SIZE, getOnHitBy(), this, EventType.HERO, CombatResolver
                .getTypeToTargets().get(EventType.HERO));
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

    public void destroy(int playerWhoDefeatedThis) {
        destroy(playerWhoDefeatedThis, DefeatType.randomSwordEffect());
    }

    public void destroy(int playerWhoDefeatedThis, DefeatType defeatType) {
        if (destroyed) {
            return;
        }
        destroyed = true;
        Vector2f absolutePosition = new Vector2f();
        absolutePosition = ScreenUtility.getWorldPosition(draw.getBody(), absolutePosition);
        if (Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().playerHasLivesLeft(playerId)) {
            defeatType.applyEffect(absolutePosition.x, absolutePosition.y, playerId);
        } else {
            FinalDefeatVisual finalVisual = new FinalDefeatVisual(position.x, position.y, draw);
            Utility.getActiveScene().addUpdateable(finalVisual);
            Utility.getActiveScene().getCameraNode().addChild(finalVisual.getDrawable());
        }
        Utility.getActiveScene().getMatchOrchestrator().addEvent(new DefeatEvent(playerId, playerWhoDefeatedThis));

    }

    public void destroyForReal() {
        if (activeWeapon != null) {
            activeWeapon.destroy();
        }

        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getCameraNode().removeChild(this.getDrawable());
        Utility.getActiveScene().getCameraNode().removeChild(hitbox.getDrawable());

        List<LifeIndicator> lifeIndicators = Utility.getActiveScene().getUpdateables(LifeIndicator.class);
        for (LifeIndicator lifeIndicator : lifeIndicators) {
            if (lifeIndicator.getOwnerId() == getPlayerId()) {
                lifeIndicator.destroy();
            }
        }

        Debugger.info("destroying hero " + playerId + " " + this);
    }

    public boolean equipWeapon(GreatSword greatsword) {
        BloxColorer.color(greatsword.getGreatsword(), getBlox().getBodyShape().getColor());
        draw.addWeapon((SceneGraphNode) greatsword.getDrawable());
        draw.removeHands();
        activeWeapon = greatsword;
        return true;
    }

    public Method<CombatEvent> getOnHitBy() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (!isImmunity()) {
                    Vector2f angleVector = new Vector2f(getPosition().x - arg.getPosition().x, getPosition().y
                            - arg.getPosition().y);
                    float angle = (float) Math.toDegrees(angleVector.getTheta());
                    if (arg.getCreator() instanceof GreatSwordColliderData) {
                        GreatSwordColliderData collider = (GreatSwordColliderData) arg.getCreator();
                        if (collider.getColliderType() == ColliderType.COUNTER) {
                            return;
                        }
                        SoundPlayer.play(SoundEffect.SWORD_DEFEAT);
                    } else if (arg.getCreator() instanceof ProjectileShot) {
                        SoundPlayer.play(SoundEffect.PROJECTILE_HIT);
                    }
                    if (getActiveWeapon() != null && getActiveWeapon().getState() == State.COUNTERING) {
                        return;
                    }
                    Utility.getGameTime().setModulator(0.015f, 1000);
                    SlashVisual slashVisual = new SlashVisual(position.x, position.y, angle);
                    Utility.getActiveScene().addUpdateable(slashVisual);
                    Utility.getActiveScene().getCameraNode().addChild(slashVisual.getDrawable());

                    ScreenFlashVisual flashVisual = new ScreenFlashVisual();
                    Utility.getActiveScene().addUpdateable(flashVisual);
                    Utility.getActiveScene().getCameraNode().addChild(flashVisual.getDrawable());
                    destroy(arg.getOwnerId());
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
