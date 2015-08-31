package dangine.scene;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.MusicEffect;
import dangine.entity.world.World;
import dangine.menu.ControlsAssigner;
import dangine.menu.TitleMenu;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.VersioningSceneGraph;

public class TitleSceneSchema32 implements SceneSchema {

    SceneGraphNode node = new SceneGraphNode();

    public void apply(Scene scene) {
        World.randomWorld().createWorld(scene);
        TitleMenu menu = new TitleMenu();
        scene.addUpdateable(menu);
        scene.getParentNode().addChild(menu.getDrawable());

        VersioningSceneGraph version = new VersioningSceneGraph();
        Utility.getActiveScene().getParentNode().addChild(version.getDrawable());

        ControlsAssigner controlsAssigner = new ControlsAssigner(false);
        scene.addUpdateable(controlsAssigner);
        scene.getParentNode().addChild(controlsAssigner.getDrawable());

        DangineMusicPlayer.startTrack(MusicEffect.TITLE_MENU);

    }
}
