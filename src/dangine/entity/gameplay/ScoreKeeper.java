package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.matchtypes.MatchTypeLogic;
import dangine.graphics.DangineStringPicture;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class ScoreKeeper implements IsUpdateable, HasDrawable {

    MatchTypeLogic matchTypeLogic;
    boolean matchOver = false;
    SceneGraphNode base = new SceneGraphNode();
    Map<Integer, PlayerScore> playerIdToScore = new HashMap<Integer, PlayerScore>();
    Map<Integer, DangineStringPicture> playerIdToTextNode = new HashMap<Integer, DangineStringPicture>();
    float timer = 0;
    final float FADE_TIME = 4000;
    final float FADE_DELAY = 2000;

    public ScoreKeeper() {
        this.matchTypeLogic = Utility.getMatchParameters().getMatchType().createMatchTypeLogic();

        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            this.matchTypeLogic.addPlayer(player.getPlayerId(), this);
        }
        updatePlayerScores();
    }

    public void addBotToGame(int botId) {
        matchTypeLogic.addBot(botId, this);
        updatePlayerScores();
    }

    @Override
    public void update() {
        if (timer < FADE_TIME) {
            float alpha = 1.0f;
            timer += Utility.getGameTime().getDeltaTimeF();
            if (timer > FADE_DELAY) {
                alpha = 1.0f - ((timer - FADE_DELAY) / (FADE_TIME - FADE_DELAY));
            }
            for (DangineStringPicture text : playerIdToTextNode.values()) {
                text.setAlpha(alpha);
            }
        }
    }

    public void onPlayerDefeatsAnother(int defeatedPlayer, int playerWhoDefeatedOther) {
        matchTypeLogic.playerDefeatsSomeone(playerWhoDefeatedOther, defeatedPlayer, this);
        updatePlayerScores();
        if (checkSceneOver() && !matchOver) {
            matchOver = true;
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new MatchEndEvent());
        }
    }

    public boolean checkSceneOver() {
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
        for (PlayerScore score : playerIdToScore.values()) {
            if (score.getPlayerId() < 0) {
                int botStock = getPlayerScore(score.getPlayerId()).getStock();
                playerIdToTextNode.get(score.playerId).setText("Bot Avatars Remaining: " + botStock);
                continue;
            }
            int stock = score.getStock();
            playerIdToTextNode.get(score.playerId).setText("P" + score.getPlayerId() + " Avatars Remaining: " + stock);
        }
        timer = 0;
    }

    private PlayerScore getPlayerScore(int playerId) {
        for (PlayerScore score : playerIdToScore.values()) {
            if (score.getPlayerId() == playerId) {
                return score;
            }
        }
        return null;
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

}
