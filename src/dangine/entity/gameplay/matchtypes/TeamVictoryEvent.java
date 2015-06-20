package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.MatchRestarter;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class TeamVictoryEvent implements MatchEvent {

    SceneGraphNode node = new SceneGraphNode();
    DangineStringPicture text = new DangineStringPicture();

    Integer victorId;

    public TeamVictoryEvent(Integer winningTeam) {
        this.victorId = winningTeam;
        text.setText("Team: " + winningTeam + " is the victor!");

        node.setPosition(Utility.getResolution().x / 2, Utility.getResolution().y / 2);
        node.addChild(text);
    }

    @Override
    public void process() {
        Utility.getActiveScene().getParentNode().addChild(node);
        Utility.getActiveScene().addUpdateable(new MatchRestarter());
    }
}