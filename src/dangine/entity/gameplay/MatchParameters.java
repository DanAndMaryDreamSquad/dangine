package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;

import dangine.entity.movement.AttackMode;
import dangine.entity.movement.MovementMode;

public class MatchParameters {

    Color defaultColor = new Color(255, 0, 0);
    Map<Integer, Color> playerIdToColor = new HashMap<Integer, Color>();
    int startingStock = 3;
    MovementMode movementMode = MovementMode.FREE;
    AttackMode attackMode = AttackMode.HOLD_TO_CHARGE;

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

    public MovementMode getMovementMode() {
        return movementMode;
    }

    public void setMovementMode(MovementMode movementMode) {
        this.movementMode = movementMode;
    }

    public AttackMode getAttackMode() {
        return attackMode;
    }

    public void setAttackMode(AttackMode attackMode) {
        this.attackMode = attackMode;
    }

}
