package dangine.entity;

import org.newdawn.slick.geom.Vector2f;

import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Utility;

public class Hero implements IsUpdateable, HasDrawable {

    final float SPEED = 0.3f;

    int playerId = 0;
    Vector2f position = new Vector2f(200, 200);
    BloxSceneGraph draw = new BloxSceneGraph();
    BloxAnimator animator = new BloxAnimator(draw);

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
        if (input.isUp()) {
            position.y -= Utility.getGameTime().getDeltaTimeF() * SPEED;
        }
        if (input.isDown()) {
            position.y += Utility.getGameTime().getDeltaTimeF() * SPEED;
        }
        if (input.isLeft()) {
            position.x -= Utility.getGameTime().getDeltaTimeF() * SPEED;
        }
        if (input.isRight()) {
            position.x += Utility.getGameTime().getDeltaTimeF() * SPEED;
        }
        draw.getBase().setPosition(position);
        animator.update();
    }

    @Override
    public IsDrawable getDrawable() {
        return draw;
    }

}
