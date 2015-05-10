package dangine.scene;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.world.World;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class TitleSceneSchema32 implements SceneSchema {

    SceneGraphNode node = new SceneGraphNode();

    public void apply(Scene scene) {
        float logoScale = 8.0f;
        DanginePicture logo = new DanginePicture("logoblack");
//        World.randomWorld().createWorld(scene);
        node.addChild(logo);
        Vector2f center = new Vector2f(Utility.getResolution()).scale(0.5f);
        Vector2f dimensions = new Vector2f(logo.getWidth(), logo.getHeight()).scale(logoScale / 2);
        node.setPosition(center.sub(dimensions));
        node.setScale(logoScale, logoScale);
        scene.getParentNode().addChild(node);

    }
}
