package dangine.entity;

import org.newdawn.slick.geom.Vector2f;

import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Utility;

public class Hero implements IsUpdateable, IsDrawable {

    final float SPEED = 0.3f;

    int playerId = 0;
    Vector2f position = new Vector2f(0, 0);
    BloxSceneGraph draw = new BloxSceneGraph();

    @Override
    public void draw() {
        draw.draw();
    }

    @Override
    public void update() {
        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
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
        draw.getBody().setPosition(position);
    }

}
