package dangine.scene;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.MusicEffect;
import dangine.entity.world.World;
import dangine.graphics.DanginePicture;
import dangine.menu.ControlsAssigner;
import dangine.menu.TitleMenu;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;
import dangine.utility.VersioningSceneGraph;

public class TitleSceneSchema32 implements SceneSchema {

    SceneGraphNode node = new SceneGraphNode();

    public void apply(Scene scene) {
        float logoScale = 8.0f;
        DanginePicture logo = new DanginePicture("logoblack");
        node.addChild(logo);
        scene.getParentNode().addChild(node);
        World.randomWorld().createWorld(scene);
        Vector2f center = new Vector2f(Utility.getResolution()).scale(0.5f);
        Vector2f dimensions = new Vector2f(logo.getWidth(), logo.getHeight()).scale(logoScale / 2);
        node.setPosition(center.sub(dimensions));
        node.setScale(logoScale, logoScale);

        TitleMenu menu = new TitleMenu();
        scene.addUpdateable(menu);
        scene.getParentNode().addChild(menu.getDrawable());

        VersioningSceneGraph version = new VersioningSceneGraph();
        Utility.getActiveScene().getParentNode().addChild(version.getDrawable());

        ControlsAssigner controlsAssigner = new ControlsAssigner();
        scene.addUpdateable(controlsAssigner);
        scene.getParentNode().addChild(controlsAssigner.getDrawable());

        DangineMusicPlayer.startTrack(MusicEffect.TITLE_MENU);
    }
}
