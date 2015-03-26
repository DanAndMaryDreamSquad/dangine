package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;

public class MatchParameters {

    Map<Integer, Color> playerIdToColor = new HashMap<Integer, Color>();

    public void addPlayerColor(int playerId, Color color) {
        playerIdToColor.put(playerId, color);
    }

    public Map<Integer, Color> getPlayerIdToColor() {
        return playerIdToColor;
    }

    public Color getPlayerColor(int playerId) {
        Integer i = new Integer(playerId);
        Color color = playerIdToColor.get(i);
        return color;
    }

}
