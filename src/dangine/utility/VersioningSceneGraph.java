package dangine.utility;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;

public class VersioningSceneGraph implements HasDrawable {

    final SceneGraphNode base = new SceneGraphNode();
    // final DangineText text = new DangineText();
    final DangineStringPicture text = new DangineStringPicture();

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
