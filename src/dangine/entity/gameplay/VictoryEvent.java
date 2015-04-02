package dangine.entity.gameplay;

import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class VictoryEvent implements MatchEvent {

    SceneGraphNode node = new SceneGraphNode();
    DangineText text = new DangineText();

    Integer victorId;

    public VictoryEvent(Integer playerId) {
        this.victorId = playerId;
        if (victorId == null) {
            text.setText("No one is the winner!");
        } else {
            text.setText("Player: " + playerId + " is the victor!");
        }
        node.setPosition(Utility.getResolution().x / 2, Utility.getResolution().y / 2);
        node.addChild(text);

    }

    @Override
    public void process() {
        Utility.getActiveScene().getParentNode().addChild(node);
        Utility.getActiveScene().addUpdateable(new MatchRestarter());
    }

}
