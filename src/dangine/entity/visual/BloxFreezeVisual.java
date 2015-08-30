package dangine.entity.visual;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class BloxFreezeVisual implements IsUpdateable, HasDrawable {

    final float DELAY = 100f;
    static final float MAX_TIME = 500f;
    float timer = 0;
    float x, y;
    boolean active = false;
    SceneGraphNode blox;

    public BloxFreezeVisual(float x, float y, SceneGraphNode blox) {
        this.x = x;
        this.y = y;
        this.blox = blox;
    }

    @Override
    public IsDrawable getDrawable() {
        return blox;
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer >= MAX_TIME) {
            Utility.getActiveScene().removeUpdateable(this);
            Utility.getActiveScene().getCameraNode().removeChild(blox);
        }
    }
}