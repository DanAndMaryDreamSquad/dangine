package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;

public class MatchParameters {

    Color defaultColor = new Color(255, 0, 0);
    Map<Integer, Color> playerIdToColor = new HashMap<Integer, Color>();
    int startingStock = 3;

    public int getStartingStock() {
        return startingStock;
    }

    public void setStartingStock(int startingStock) {
        this.startingStock = startingStock;
    }

    public void addPlayerColor(int playerId, Color color) {
        playerIdToColor.put(playerId, color);
    }

    public Map<Integer, Color> getPlayerIdToColor() {
        return playerIdToColor;
    }

    public Color getPlayerColor(int playerId) {
        Integer i = new Integer(playerId);
        Color color = playerIdToColor.get(i);
        if (color == null) {
            return defaultColor;
        }
        return color;
    }

}
