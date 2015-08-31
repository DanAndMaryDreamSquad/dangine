package dangine.scene;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.MusicEffect;
import dangine.entity.world.World;
import dangine.menu.ControlsAssigner;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.VersioningSceneGraph;

public class CharacterSelectSchema implements SceneSchema {

    SceneGraphNode node = new SceneGraphNode();

    public void apply(Scene scene) {
        World.randomWorld().createWorld(scene);

        CharacterSelect menu = new CharacterSelect();
        scene.addUpdateable(menu);

        VersioningSceneGraph version = new VersioningSceneGraph();
        Utility.getActiveScene().getParentNode().addChild(version.getDrawable());

        ControlsAssigner controlsAssigner = new ControlsAssigner(true);
        controlsAssigner.withCharacterSelect(menu);
        scene.addUpdateable(controlsAssigner);
        scene.getParentNode().addChild(controlsAssigner.getDrawable());

        DangineMusicPlayer.startTrack(MusicEffect.CHARACTER_SELECT);
    }
}
