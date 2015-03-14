package dangine.entity;

import org.newdawn.slick.geom.Vector2f;

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
    }

    @Override
    public IsDrawable getDrawable() {
        return draw;
    }

}
