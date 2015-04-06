package dangine.scene;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.Background;
import dangine.input.ControlsExplainSceneGraph;
import dangine.menu.TitleMenu;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;
import dangine.utility.VersioningSceneGraph;

public class TitleSceneSchema implements SceneSchema {

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

        ControlsExplainSceneGraph controls0 = new ControlsExplainSceneGraph(0);
        ControlsExplainSceneGraph controls1 = new ControlsExplainSceneGraph(1);
        float width = Utility.getResolution().x;
        controls0.getBase().setPosition(width * (1f / 6f), 0);
        controls1.getBase().setPosition(width * (4f / 6f), 0);
        Utility.getActiveScene().getParentNode().addChild(controls0.getDrawable());
        Utility.getActiveScene().getParentNode().addChild(controls1.getDrawable());

        VersioningSceneGraph version = new VersioningSceneGraph();
        Utility.getActiveScene().getParentNode().addChild(version.getDrawable());

    }

}
