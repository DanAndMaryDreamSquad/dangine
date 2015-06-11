package dangine.bots;

import org.lwjgl.util.Color;

import dangine.collision.GreatSwordCounterCollider;
import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.CombatResolver;
import dangine.entity.combat.CombatResolver.EventType;
import dangine.entity.combat.subpower.DashPower;
import dangine.entity.combat.subpower.ProjectilePower;
import dangine.entity.gameplay.MatchStarter.MatchType;
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
import dangine.utility.Vector2f;

public class DangineBot implements IsUpdateable, HasDrawable {

    final float SPEED = 0.3f;
    final int botId;
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
    BotGreatsword activeWeapon = null;
    DangineBotLogic logic = new DangineBotLogic();
    DashPower dashPower = null;
    ProjectilePower projectilePower = null;

    public DangineBot(int botId) {
        this.botId = botId;
        int colliderId = MatchType.getColliderId(botId);
        onHit = new CombatEvent(colliderId, position, HITBOX_SIZE, getOnHitBy(), this, EventType.HERO, CombatResolver
                .getTypeToTargets().get(EventType.HERO));
        hitbox = new CombatEventHitbox(onHit);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
        Color color = Utility.getMatchParameters().getPlayerColor(botId);
        BloxColorer.color(draw, color);
    }

    @Override
    public void update() {
        DangineSampleInput input = logic.getWhatToDo(this);
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
        animator.update();
        facing.updateFacing(this, input);

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
        Vector2f absolutePosition = new Vector2f();
        absolutePosition = ScreenUtility.getWorldPosition(draw.getBody(), absolutePosition);
        defeatType.applyEffect(absolutePosition.x, absolutePosition.y, botId);
        Utility.getActiveScene().getMatchOrchestrator().addEvent(new BotDefeatEvent(botId));
        Debugger.info("created event to destroy bot " + botId);
    }

    public void destroyForReal() {
        if (activeWeapon != null) {
            activeWeapon.destroy();
        }

        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getCameraNode().removeChild(this.getDrawable());
        Utility.getActiveScene().getCameraNode().removeChild(hitbox.getDrawable());

        Debugger.info("destroying bot " + botId + " " + this);
    }

    public boolean equipWeapon(BotGreatsword greatsword) {
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
                if (arg.getCreator() instanceof GreatSwordCounterCollider) {
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

    public BotGreatsword getActiveWeapon() {
        return activeWeapon;
    }

    public BloxAnimator getAnimator() {
        return animator;
    }

    public void setDashPower(DashPower dashPower) {
        this.dashPower = dashPower;
    }

    public void setProjectilePower(ProjectilePower projectilePower) {
        this.projectilePower = projectilePower;
    }

    public DashPower getDashPower() {
        return dashPower;
    }

    public ProjectilePower getProjectilePower() {
        return projectilePower;
    }

    public int getBotId() {
        return botId;
    }
}