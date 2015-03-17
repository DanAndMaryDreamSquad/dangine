package dangine.entity;

import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;

public class Obstruction implements IsUpdateable, HasDrawable {

    SceneGraphNode node = new SceneGraphNode();

    public Obstruction() {
        node.addChild(new DangineImage("block"));
        node.setPosition(300, 350);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
