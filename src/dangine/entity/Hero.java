package dangine.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import dangine.debugger.Debugger;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.GreatSword;
import dangine.entity.gameplay.DefeatEvent;
import dangine.entity.movement.HeroMovement;
import dangine.entity.visual.DefeatedBloxKnockVisual;
import dangine.entity.visual.DefeatedBloxSplitVisual;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.MathUtility;
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
    final int HITBOX_SIZE = 20;
    final CombatEvent onHit;
    final CombatEventHitbox hitbox;
    boolean immunity = false;
    boolean destroyed = false;
    GreatSword activeWeapon = null;

    public Hero(int playerId) {
        this.playerId = playerId;
        onHit = new CombatEvent(playerId, position, HITBOX_SIZE, getOnHitBy(), this);
        hitbox = new CombatEventHitbox(onHit);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
        Color color = Utility.getActiveScene().getMatchParameters().getPlayerColor(getPlayerId());
        BloxColorer.color(draw, color);
    }

    @Override
    public void update() {
        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        if (input.isUp() || input.isDown()) {
            animator.idle();
        } else if (input.isLeft() || input.isRight()) {
            animator.walk();
        } else {
            animator.floating();
        }
        movement.moveHero(this.position, input);
        draw.getBase().setPosition(position);
        animator.update();
        int facing = 0;
        if (input.isLeft()) {
            facing++;
        }
        if (input.isRight()) {
            facing--;
        }
        animator.updateFacing(facing);

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

        // DefeatedBloxSplitVisual split = new
        // DefeatedBloxSplitVisual(absolutePosition.x, absolutePosition.y, 0,
        // playerId);

        if (MathUtility.randomBoolean()) {
            DefeatedBloxKnockVisual split = new DefeatedBloxKnockVisual(absolutePosition.x, absolutePosition.y, -30);
            Utility.getActiveScene().getCameraNode().addChild(split.getDrawable());
            Utility.getActiveScene().addUpdateable(split);
        } else {
            DefeatedBloxSplitVisual split = new DefeatedBloxSplitVisual(absolutePosition.x, absolutePosition.y, -30,
                    playerId);
            Utility.getActiveScene().getCameraNode().addChild(split.getDrawable());
            Utility.getActiveScene().addUpdateable(split);

        }

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
                // if (arg.getCreator() instanceof Hero) {
                // return;
                // }
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
}
