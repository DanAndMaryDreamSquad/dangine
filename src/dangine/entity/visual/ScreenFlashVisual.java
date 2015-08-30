package dangine.entity.visual;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineBox;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Oscillator;
import dangine.utility.Utility;

public class ScreenFlashVisual implements IsUpdateable, HasDrawable {
    final float SPEED = 0.05f;
    final float MAX_TIME = 2f;
    float timer = 0;
    Oscillator oscillator = new Oscillator(0, 255, 4);
    SceneGraphNode node = new SceneGraphNode();
    Color color = new Color(Color.WHITE);
    DangineBox box = new DangineBox((int) Utility.getResolution().x, (int) Utility.getResolution().y, new Color(
            Color.WHITE));

    public ScreenFlashVisual() {
        node.addChild(box);
        node.setZValue(-100);
        color.setAlpha(0);
        box.setColor(color);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        oscillator.update();
        color.setAlpha(Math.round(oscillator.calcOffset(0)));
        box.setColor(color);
        if (timer >= MAX_TIME) {
            Utility.getActiveScene().removeUpdateable(this);
            node.removeChild(box);
            Utility.getActiveScene().getCameraNode().removeChild(node);
        }

    }
}
