package dangine.scene;

import dangine.entity.world.World;
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
    }
}
