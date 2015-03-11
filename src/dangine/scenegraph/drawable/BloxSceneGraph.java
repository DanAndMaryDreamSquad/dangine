package dangine.scenegraph.drawable;

import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;

public class BloxSceneGraph implements IsDrawable {

    SceneGraphNode body = new SceneGraphNode();

    public BloxSceneGraph() {
        body.addChild(new DangineShape());
    }

    @Override
    public void draw() {
        body.draw();
    }

    public SceneGraphNode getBody() {
        return body;
    }

}
