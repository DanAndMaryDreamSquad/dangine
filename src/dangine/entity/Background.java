package dangine.entity;

import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class Background implements HasDrawable, IsUpdateable {

    DangineImage background = new DangineImage("cloudy");
    DangineImage background2 = new DangineImage("cloudy");
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode node1 = new SceneGraphNode();
    SceneGraphNode node2 = new SceneGraphNode();

    float x1 = 0;
    float x2 = -background.getWidth() * 20;

    public Background() {
        base.addChild(node2);
        base.addChild(node1);
        base.setZValue(1.0f);

        node1.addChild(background);
        node1.setZValue(1.0f);
        node1.setScale(20, 20);

        node2.addChild(background2);
        node2.setZValue(1.0f);
        node2.setScale(20, 20);
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    @Override
    public void update() {
        x1 += Utility.getGameTime().getDeltaTimeF() * 0.05f;
        if (x1 > background.getWidth() * 20) {
            x1 += -background.getWidth() * 40;
        }
        node1.setPosition(x1, 0);
        x2 += Utility.getGameTime().getDeltaTimeF() * 0.05f;
        if (x2 > background.getWidth() * 20) {
            x2 += -background.getWidth() * 40;
        }
        node2.setPosition(x2, 0);
    }

}
