package dangine.graphics;

import dangine.scenegraph.SceneGraphNode;

public interface IsDrawable32 {

    public void draw();

    public RenderData32 getRenderData32();

    public IsDrawable32 copy();

    public SceneGraphNode getNode();

}
