package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.Color;

import dangine.bots.BotType;
import dangine.bots.DangineBot;
import dangine.debugger.Debugger;
import dangine.entity.Hero;
import dangine.entity.combat.subpower.SubPower;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.entity.movement.AttackMode;
import dangine.entity.movement.FacingMode;
import dangine.entity.movement.MovementMode;
import dangine.entity.world.Background;
import dangine.entity.world.World;

public class MatchParameters {

    Color defaultColor = new Color(255, 0, 0);
    Map<Integer, Color> playerIdToColor = new HashMap<Integer, Color>();
    Map<Integer, SubPower> playerIdToPower = new HashMap<Integer, SubPower>();
    final Map<Integer, Integer> playerIdToTeam = new HashMap<Integer, Integer>();
    int startingStock = 3;
    int goalsRequired = 3;
    int numberOfBots = 2;
    MovementMode movementMode = MovementMode.MOVE_FREE_TURN_ALL_LOCK;
    AttackMode attackMode = AttackMode.HOLD_TO_CHARGE;
    FacingMode facingMode = FacingMode.EIGHT_WAY;
    Background currentBackground = Background.EXTRADIMENSIONAL;
    World currentWorld = World.DIMENSION;
    boolean isRandomWorld = false;
    boolean isFriendlyFire = false;
    Color textColor = new Color(Color.BLACK);
    MatchType matchType = MatchType.VERSUS;
    BotType botType = BotType.NORMAL;

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

    public void addPlayerTeam(int playerId, int team) {
        playerIdToTeam.put(playerId, team);
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

    public void givePlayerPower(DangineBot bot) {
        SubPower power = playerIdToPower.get(bot.getBotId());
        if (power != null) {
            power.givePower(bot);
        }
    }

    public SubPower getPlayerPower(int playerId) {
        SubPower power = playerIdToPower.get(playerId);
        if (power == null) {
            playerIdToPower.put(playerId, SubPower.DASH);
            return SubPower.DASH;
        }
        return power;
    }

    public int getPlayerTeam(int playerId) {
        Integer team = playerIdToTeam.get(playerId);
        if (team == null) {
            Debugger.warn("player " + playerId + " mapped to null");
            return 0;
        }
        Debugger.warn("player " + playerId + " mapped to team " + team);
        return team;
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

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public int getNumberOfBots() {
        return numberOfBots;
    }

    public void setNumberOfBots(int numberOfBots) {
        this.numberOfBots = numberOfBots;
    }

    public Map<Integer, Integer> getPlayerIdToTeam() {
        return playerIdToTeam;
    }

    public boolean isFriendlyFire() {
        return isFriendlyFire;
    }

    public void setFriendlyFire(boolean isFriendlyFire) {
        this.isFriendlyFire = isFriendlyFire;
    }

    public BotType getBotType() {
        return botType;
    }

    public void setBotType(BotType botType) {
        this.botType = botType;
    }

    public int getGoalsRequired() {
        return goalsRequired;
    }

}
