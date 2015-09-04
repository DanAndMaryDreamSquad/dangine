package dangine.entity.gameplay.matchtypes;

import java.util.List;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.MatchRestarter;
import dangine.graphics.DangineFont;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class VictoryEvent implements MatchEvent {

    SceneGraphNode node = new SceneGraphNode();
    DangineStringPicture text = new DangineStringPicture();

    Integer victorId;

    public VictoryEvent(Integer playerId) {
        this.victorId = playerId;
        text.setText("Player: " + playerId + " is the victor!");

        float xOffset = DangineFont.getLengthInPixels(text.getText()) * 0.5f;
        node.setPosition((Utility.getResolution().x * 0.5f) - xOffset, Utility.getResolution().y / 2);
        node.addChild(text);
        Utility.getMatchParameters().getRoundKeeper().onPlayerWonRound(playerId);
    }

    public VictoryEvent(List<Integer> playerIds) {
        if (playerIds.isEmpty()) {
            text.setText("No one is the winner!");
        } else if (allBots(playerIds)) {
            text.setText("The bots have claimed victory!");
        } else {
            text.setText("Players: " + idsString(playerIds) + " are the victors!");
        }
        float xOffset = DangineFont.getLengthInPixels(text.getText()) * 0.5f;
        node.setPosition((Utility.getResolution().x * 0.5f) - xOffset, Utility.getResolution().y / 2);
        node.addChild(text);
        for (Integer id : playerIds) {
            Utility.getMatchParameters().getRoundKeeper().onPlayerWonRound(id);
        }
    }

    private boolean allBots(List<Integer> playerIds) {
        boolean result = true;
        for (Integer id : playerIds) {
            if (id >= 0) {
                result = false;
            }
        }
        return result;
    }

    private String idsString(List<Integer> playerIds) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < playerIds.size(); i++) {
            Integer id = playerIds.get(i);
            buffer.append(id.toString());
            if (i != playerIds.size() - 1) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }

    @Override
    public void process() {
        Utility.getActiveScene().getParentNode().addChild(node);
        Utility.getActiveScene().addUpdateable(new MatchRestarter());
    }

}
