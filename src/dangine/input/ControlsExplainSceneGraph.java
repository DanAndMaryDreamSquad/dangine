package dangine.input;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class ControlsExplainSceneGraph implements HasDrawable {

    final SceneGraphNode base = new SceneGraphNode();
    final DangineStringPicture text = new DangineStringPicture();

    public ControlsExplainSceneGraph(int playerId) {
        base.addChild(text);
        base.setScale(1.0f, 1.0f);
        float y = 0.0f;
        if (playerId == 1 || playerId == 5) {
            y = Utility.getResolution().y / 3.0f;
        }
        float x = (Utility.getResolution().x / 4.0f) * playerId;
        if (playerId >= 1) {
            x = (Utility.getResolution().x / 4.0f) * (playerId - 1);
        }
        if (playerId == 5) {
            x = (Utility.getResolution().x / 4.0f) * 3;
        }
        base.setPosition(x, y);
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
