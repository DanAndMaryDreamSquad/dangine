package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.MatchRestarter;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class BotVictoryEvent implements MatchEvent {

    SceneGraphNode node = new SceneGraphNode();
    DangineStringPicture text = new DangineStringPicture();

    public BotVictoryEvent() {
        text.setText("The bots have claimed victory!");
        node.setPosition(Utility.getResolution().x / 2, Utility.getResolution().y / 2);
        node.addChild(text);
    }

    @Override
    public void process() {
        Utility.getActiveScene().getParentNode().addChild(node);
        Utility.getActiveScene().addUpdateable(new MatchRestarter());
    }
}