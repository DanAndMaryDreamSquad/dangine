package dangine.utility;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;

public class VersioningSceneGraph implements HasDrawable {

    final SceneGraphNode base = new SceneGraphNode();
    final DangineText text = new DangineText();

    public VersioningSceneGraph() {
        base.addChild(text);
        text.setText(DangineVersioning.getVersion());
        base.setPosition(0, Utility.getResolution().y - 20);
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public SceneGraphNode getBase() {
        return base;
    }

}
