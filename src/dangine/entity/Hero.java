package dangine.entity;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.combat.GreatSword;
import dangine.entity.movement.HeroMovement;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Utility;

public class Hero implements IsUpdateable, HasDrawable {

    final float SPEED = 0.3f;

    int playerId = 0;
    final Vector2f position = new Vector2f(200, 200);
    final BloxSceneGraph draw = new BloxSceneGraph();
    final BloxAnimator animator = new BloxAnimator(draw);
    final HeroMovement movement = new HeroMovement();

    public Hero() {

    }

    public Hero(int playerId) {
        this.playerId = playerId;
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
    }

    public boolean equipWeapon(GreatSword greatsword) {
        draw.getBody().addChild(greatsword.getDrawable());
        draw.removeHands();
        return true;
    }

    @Override
    public IsDrawable getDrawable() {
        return draw.getBase();
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }
}
