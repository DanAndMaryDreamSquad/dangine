package dangine.entity.visual;

import org.lwjgl.util.Color;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class DefeatedBloxKnockVisual implements IsUpdateable, HasDrawable {

    final float MAX_TIMER = 1000f;
    final float MOVE_SPEED = 0.40f;
    final float ROTATION_SPEED = 0.50f;
    final BloxSceneGraph blox = new BloxSceneGraph();
    final Vector2f velocity;
    float timer = 0;
    float angle = 0f;

    public DefeatedBloxKnockVisual(float x, float y, float angle, Color color) {
        blox.getBase().setPosition(x, y);

        velocity = new Vector2f(angle);
        velocity.scale(MOVE_SPEED);
        blox.getBase().setCenterOfRotation(10, 10);
        Debugger.info(x + " " + y);
        BloxColorer.color(blox, color);
    }

    @Override
    public IsDrawable getDrawable() {
        return blox.getDrawable();
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        Vector2f position = blox.getBase().getPosition();
        position.x += velocity.x * Utility.getGameTime().getDeltaTimeF();
        position.y += velocity.y * Utility.getGameTime().getDeltaTimeF();

        angle += ROTATION_SPEED * Utility.getGameTime().getDeltaTimeF();
        blox.getBase().setAngle(angle);
        if (timer > MAX_TIMER) {
            Utility.getActiveScene().getCameraNode().removeChild(this.getDrawable());
            Utility.getActiveScene().removeUpdateable(this);
        }
    }

}
