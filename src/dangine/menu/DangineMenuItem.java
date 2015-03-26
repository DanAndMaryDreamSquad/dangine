package dangine.menu;

import org.newdawn.slick.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;

public class DangineMenuItem implements HasDrawable {

    interface Action {
        void execute();
    }

    SceneGraphNode base = new SceneGraphNode();
    Action onActivate;

    public DangineMenuItem(String text, Action onActivate) {
        base.addChild(new DangineText(text, Color.black));
        this.onActivate = onActivate;
    }

    public SceneGraphNode getBase() {
        return base;
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public void activate() {
        onActivate.execute();
    }

}
