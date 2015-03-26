package dangine.scene;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.Background;
import dangine.menu.TitleMenu;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class TitleSceneSchema {

    SceneGraphNode node = new SceneGraphNode();

    public void apply(Scene scene) {
        float logoScale = 8.0f;
        DangineImage logo = new DangineImage("logoblack");
        Background background = new Background();
        scene.addUpdateable(background);
        scene.getParentNode().addChild(background.getDrawable());
        node.addChild(logo);
        Vector2f center = new Vector2f(Utility.getResolution()).scale(0.5f);
        Vector2f dimensions = new Vector2f(logo.getWidth(), logo.getHeight()).scale(logoScale / 2);
        node.setPosition(center.sub(dimensions));
        node.setScale(logoScale, logoScale);
        scene.getParentNode().addChild(node);

        TitleMenu menu = new TitleMenu();
        scene.addUpdateable(menu);
        scene.getParentNode().addChild(menu.getDrawable());

    }

}
