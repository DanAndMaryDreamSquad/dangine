package dangine.bots;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import dangine.collision.GreatSwordCounterCollider;
import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.movement.HeroFacing;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.DefeatedBloxKnockVisual;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Method;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;

public class DangineBot implements IsUpdateable, HasDrawable {

    final float SPEED = 0.3f;

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

    public DangineBot() {
        onHit = new CombatEvent(-1, position, HITBOX_SIZE, getOnHitBy(), this);
        hitbox = new CombatEventHitbox(onHit);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
        Color color = Utility.getMatchParameters().getPlayerColor(-1);
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
        movement.moveHero(this.position, input, activeWeapon);
        draw.getBase().setPosition(position);
        animator.update();
        facing.updateFacing(this, input);

        onHit.setPosition(position);
        hitbox.setPosition(position);
        hitbox.setPosition(position.x - HITBOX_SIZE, position.y - HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(onHit);

        if (input.isButtonTwo()) {
            this.destroy();
        }
    }

    public void destroy() {
        if (destroyed) {
            return;
        }
        destroyed = true;
        if (activeWeapon != null) {
            activeWeapon.destroy();
        }

        Vector2f absolutePosition = new Vector2f();
        absolutePosition = ScreenUtility.getWorldPosition(draw.getBody(), absolutePosition);

        Color color = Utility.getMatchParameters().getPlayerColor(-1);
        DefeatedBloxKnockVisual split = new DefeatedBloxKnockVisual(absolutePosition.x, absolutePosition.y, -30, color);
        Utility.getActiveScene().getCameraNode().addChild(split.getDrawable());
        Utility.getActiveScene().addUpdateable(split);

        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getCameraNode().removeChild(this.getDrawable());
        Utility.getActiveScene().getCameraNode().removeChild(hitbox.getDrawable());
        Utility.getActiveScene().getMatchOrchestrator().addEvent(new BotDefeatEvent(-1));

        Debugger.info("destroying bot ");
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
                if (arg.getCreator() instanceof GreatSwordCounterCollider || arg.getCreator() instanceof Hero
                        || arg.getCreator() instanceof DangineBot) {
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
}