package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.matchtypes.MatchTypeLogic;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class ScoreKeeper implements IsUpdateable, HasDrawable {

    MatchTypeLogic matchTypeLogic;
    boolean matchOver = false;
    SceneGraphNode base = new SceneGraphNode();
    Map<Integer, PlayerScore> playerIdToScore = new HashMap<Integer, PlayerScore>();
    Map<Integer, PlayerScore> teamIdToScore = new HashMap<Integer, PlayerScore>();
    Map<Integer, DangineStringPicture> playerIdToTextNode = new HashMap<Integer, DangineStringPicture>();
    Map<Integer, DangineStringPicture> teamIdToTextNode = new HashMap<Integer, DangineStringPicture>();
    float timer = 0;
    final float FADE_TIME = 4000;
    final float FADE_DELAY = 2000;

    public ScoreKeeper() {
        this.matchTypeLogic = Utility.getMatchParameters().getMatchType().createMatchTypeLogic();
    }

    public void setupMatch() {
        this.matchTypeLogic.setupMatch(this);
    }

    public void addPlayerToGame(int playerId) {
        matchTypeLogic.addPlayer(playerId, this);
    }

    public void addBotToGame(int botId) {
        matchTypeLogic.addBot(botId, this);
    }

    @Override
    public void update() {
        if (timer < FADE_TIME) {
            float alpha = 1.0f;
            timer += Utility.getGameTime().getDeltaTimeF();
            if (timer > FADE_DELAY) {
                alpha = 1.0f - ((timer - FADE_DELAY) / (FADE_TIME - FADE_DELAY));
                alpha = Math.max(0, alpha);
            }
            for (DangineStringPicture text : playerIdToTextNode.values()) {
                text.setAlpha(alpha);
            }
            for (DangineStringPicture text : teamIdToTextNode.values()) {
                text.setAlpha(alpha);
            }
        }
    }

    public void onPlayerScores(int playerWhoScored) {
        matchTypeLogic.onPlayerScores(playerWhoScored, this);
        updatePlayerScores();
        checkForEndGame();
    }

    public void onTeamScores(int teamWhoScored) {
        matchTypeLogic.onTeamScores(teamWhoScored, this);
        updatePlayerScores();
        checkForEndGame();
    }

    public void onPlayerDefeatsAnother(int defeatedPlayer, int playerWhoDefeatedOther) {
        matchTypeLogic.playerDefeatsSomeone(playerWhoDefeatedOther, defeatedPlayer, this);
        updatePlayerScores();
        checkForEndGame();
    }

    private void checkForEndGame() {
        if (isSceneOver() && !matchOver) {
            matchOver = true;
            Utility.getActiveScene().getMatchOrchestrator().addEvent(matchTypeLogic.createVictoryEvent(this));
        }
    }

    public boolean isSceneOver() {
        return matchTypeLogic.isSceneOver(this);
    }

    public boolean shouldPlayerRespawn(int playerId) {
        return matchTypeLogic.shouldPlayerRespawn(playerId, this);
    }

    public void applyEndOfRound() {
        MatchEvent event = matchTypeLogic.createVictoryEvent(this);
        Utility.getActiveScene().getMatchOrchestrator().addEvent(event);
    }

    private void updatePlayerScores() {
        if (Utility.getPlayers().getPlayers().isEmpty()) {
            return;
        }
        // matchTypeLogic.updateScoreBoardText(this);
    }

    public PlayerScore getPlayerScore(int playerId) {
        for (PlayerScore score : playerIdToScore.values()) {
            if (score.getPlayerId() == playerId) {
                return score;
            }
        }
        return null;
    }

    public boolean playerHasLivesLeft(int playerId) {
        return matchTypeLogic.playerHasLivesLeft(playerId, this);
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public Map<Integer, PlayerScore> getPlayerIdToScore() {
        return playerIdToScore;
    }

    public Map<Integer, DangineStringPicture> getPlayerIdToTextNode() {
        return playerIdToTextNode;
    }

    public SceneGraphNode getBase() {
        return base;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public Map<Integer, PlayerScore> getTeamIdToScore() {
        return teamIdToScore;
    }

    public Map<Integer, DangineStringPicture> getTeamIdToTextNode() {
        return teamIdToTextNode;
    }

}
