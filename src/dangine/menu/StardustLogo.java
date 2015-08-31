package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class StardustLogo implements HasDrawable {
    SceneGraphNode node = new SceneGraphNode();

    public StardustLogo() {
        float logoScale = 8.0f;
        DanginePicture logo = new DanginePicture("logoblack");
        node.addChild(logo);
        Vector2f center = new Vector2f(Utility.getResolution()).scale(0.5f);
        Vector2f dimensions = new Vector2f(logo.getWidth(), logo.getHeight()).scale(logoScale / 2);
        node.setPosition(center.sub(dimensions));
        node.setScale(logoScale, logoScale);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
