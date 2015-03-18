package dangine.entity;

import dangine.scenegraph.RenderData;

public interface IsDrawable {

    public void draw();

    public RenderData getRenderData();

    public IsDrawable copy();

}
