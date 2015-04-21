package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;

import dangine.entity.Hero;
import dangine.entity.combat.subpower.SubPower;
import dangine.entity.movement.AttackMode;
import dangine.entity.movement.FacingMode;
import dangine.entity.movement.MovementMode;
import dangine.entity.world.Background;
import dangine.entity.world.World;

public class MatchParameters {

    Color defaultColor = new Color(255, 0, 0);
    Map<Integer, Color> playerIdToColor = new HashMap<Integer, Color>();
    Map<Integer, SubPower> playerIdToPower = new HashMap<Integer, SubPower>();
    int startingStock = 1;
    MovementMode movementMode = MovementMode.MOVE_FREE_TURN_SWING_LOCK;
    AttackMode attackMode = AttackMode.HOLD_TO_CHARGE;
    FacingMode facingMode = FacingMode.EIGHT_WAY;
    Background currentBackground = Background.EXTRADIMENSIONAL;
    World currentWorld = World.DIMENSION;
    boolean isRandomWorld = false;
    Color textColor = Color.black;

    public int getStartingStock() {
        return startingStock;
    }

    public void setStartingStock(int startingStock) {
        this.startingStock = startingStock;
    }

    public void addPlayerColor(int playerId, Color color) {
        playerIdToColor.put(playerId, color);
    }

    public void addPlayerPower(int playerId, SubPower power) {
        playerIdToPower.put(playerId, power);
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

    public void givePlayerPower(Hero hero) {
        SubPower power = playerIdToPower.get(hero.getPlayerId());
        if (power != null) {
            power.givePower(hero);
        }
    }

    public SubPower getPlayerPower(int playerId) {
        SubPower power = playerIdToPower.get(playerId);
        if (power == null) {
            return SubPower.NONE;
        }
        return power;
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

    public Map<Integer, SubPower> getPlayerIdToPower() {
        return playerIdToPower;
    }

    public Background getCurrentBackground() {
        return currentBackground;
    }

    public void setCurrentWorld(Background currentWorld) {
        this.currentBackground = currentWorld;
    }

    public FacingMode getFacingMode() {
        return facingMode;
    }

    public void setFacingMode(FacingMode facingMode) {
        this.facingMode = facingMode;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public boolean isRandomWorld() {
        return isRandomWorld;
    }

    public void setRandomWorld(boolean isRandomWorld) {
        this.isRandomWorld = isRandomWorld;
    }

}
