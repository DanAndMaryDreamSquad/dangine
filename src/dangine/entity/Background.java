package dangine.entity;

import org.newdawn.slick.Color;

import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineShape;

public class Background implements HasDrawable {

    DangineShape background = new DangineShape(900, 900, Color.pink);
    SceneGraphNode node = new SceneGraphNode();

    public Background() {
        node.addChild(background);
        node.setZValue(1.0f);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
