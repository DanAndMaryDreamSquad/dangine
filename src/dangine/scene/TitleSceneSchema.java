package dangine.scene;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.world.World;
import dangine.graphics.DanginePicture;
import dangine.input.ControlsExplainSceneGraph;
import dangine.menu.ControlsAssigner;
import dangine.menu.TitleMenu;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class TitleSceneSchema implements SceneSchema {

    SceneGraphNode node = new SceneGraphNode();

    public void apply(Scene scene) {
        float logoScale = 8.0f;
        DanginePicture logo = new DanginePicture("logoblack");
        World.randomWorld().createWorld(scene);
        node.addChild(logo);
        Vector2f center = new Vector2f(Utility.getResolution()).scale(0.5f);
        Vector2f dimensions = new Vector2f(logo.getWidth(), logo.getHeight()).scale(logoScale / 2);
        node.setPosition(center.sub(dimensions));
        node.setScale(logoScale, logoScale);
        scene.getParentNode().addChild(node);

        TitleMenu menu = new TitleMenu();
        scene.addUpdateable(menu);
        scene.getParentNode().addChild(menu.getDrawable());

        List<ControlsExplainSceneGraph> controlsGraphs = new ArrayList<ControlsExplainSceneGraph>();
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            ControlsExplainSceneGraph graph = new ControlsExplainSceneGraph(player.getPlayerId());
            scene.getParentNode().addChild(graph.getDrawable());
            controlsGraphs.add(graph);
        }

        ControlsAssigner controlsAssigner = new ControlsAssigner(true);
        scene.addUpdateable(controlsAssigner);
        scene.getParentNode().addChild(controlsAssigner.getDrawable());

    }

}
