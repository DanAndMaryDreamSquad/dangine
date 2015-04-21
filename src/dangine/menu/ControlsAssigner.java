package dangine.menu;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.input.ControlsExplainSceneGraph;
import dangine.input.DangineControllerAssignments;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class ControlsAssigner implements IsUpdateable, HasDrawable {

    SceneGraphNode node = new SceneGraphNode();
    DangineText text = new DangineText();

    public ControlsAssigner() {
        node.addChild(text);
        node.setPosition(Utility.getResolution().x / 2, Utility.getResolution().y - 20);
    }

    @Override
    public void update() {
        DangineControllerAssignments.scan();
        text.setText(DangineControllerAssignments.getOptions());
        while (DangineControllerAssignments.getSize() > Utility.getPlayers().getPlayers().size()) {
            Debugger.info("new player");
            Utility.getPlayers().newPlayer();
            ControlsExplainSceneGraph explain = new ControlsExplainSceneGraph(
                    Utility.getPlayers().getPlayers().size() - 1);
            Utility.getActiveScene().getParentNode().addChild(explain.getDrawable());
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
