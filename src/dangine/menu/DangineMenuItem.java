package dangine.menu;

import org.newdawn.slick.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;

public class DangineMenuItem implements HasDrawable {

    SceneGraphNode base = new SceneGraphNode();

    public DangineMenuItem(String text) {
        base.addChild(new DangineText(text, Color.black));
    }

    public SceneGraphNode getBase() {
        return base;
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

}
