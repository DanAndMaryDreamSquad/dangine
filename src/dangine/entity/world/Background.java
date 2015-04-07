package dangine.entity.world;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class Background implements HasDrawable, IsUpdateable {

    DangineImage background;
    DangineImage background2;
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode node1 = new SceneGraphNode();
    SceneGraphNode node2 = new SceneGraphNode();
    float scale = 1.0f;
    float panSpeed = 0.0f;

    float x1 = 0;
    float x2 = 0;

    public Background(World world) {
        background = new DangineImage(world.getBgImage());
        background2 = new DangineImage(world.getBgImage());
        panSpeed = world.getPanSpeed();

        scale = (int) (Utility.getResolution().x / background.getWidth());
        scale = (scale * world.getScale());
        base.addChild(node2);
        base.addChild(node1);
        base.setZValue(1.0f);

        node1.addChild(background);
        node1.setZValue(1.0f);
        node1.setScale(scale, scale);

        node2.addChild(background2);
        node2.setZValue(1.0f);
        node2.setScale(scale, scale);

        x2 = -background.getWidth() * scale;
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    @Override
    public void update() {
        x1 += Utility.getGameTime().getDeltaTimeF() * panSpeed;
        if (x1 > background.getWidth() * scale) {
            x1 += -background.getWidth() * scale * 2;
        }
        node1.setPosition(x1, 0);
        x2 += Utility.getGameTime().getDeltaTimeF() * panSpeed;
        if (x2 > background.getWidth() * scale) {
            x2 += -background.getWidth() * scale * 2;
        }
        node2.setPosition(x2, 0);
    }

}
