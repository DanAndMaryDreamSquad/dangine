package dangine.input;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class ControlsExplainSceneGraph implements HasDrawable {

    final SceneGraphNode base = new SceneGraphNode();
    final DangineText text = new DangineText();

    public ControlsExplainSceneGraph(int playerId) {
        base.addChild(text);
        String help = Utility.getPlayers().getPlayer(playerId).getInputMapper().toString();
        text.setText("Player " + playerId + " Controls:\n" + help);
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public SceneGraphNode getBase() {
        return base;
    }

}
