package dangine.entity.gameplay.matchtypes;

import dangine.debugger.Debugger;
import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.PlayerScore;
import dangine.entity.gameplay.ScoreKeeper;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class SoccerModeLogic implements MatchTypeLogic {

    @Override
    public void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper) {
    }

    @Override
    public boolean isSceneOver(ScoreKeeper scoreKeeper) {
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= Utility.getMatchParameters().getStartingStock()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper) {
        return true;
    }

    @Override
    public MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper) {
        Debugger.info("round over");
        // TODO
        return new TieVictoryEvent();
    }

    @Override
    public void addPlayer(int playerId, ScoreKeeper scoreKeeper) {
        SceneGraphNode score = new SceneGraphNode();
        DangineStringPicture text = new DangineStringPicture();
        score.setPosition(getLabelLocationFromPlayerId(playerId));
        score.addChild(text);
        scoreKeeper.getBase().addChild(score);
        scoreKeeper.getPlayerIdToTextNode().put(playerId, text);

        PlayerScore playerScore = new PlayerScore(playerId);
        scoreKeeper.getPlayerIdToScore().put(playerId, playerScore);
        playerScore.setStock(0);
    }

    @Override
    public void addBot(int botId, ScoreKeeper scoreKeeper) {
        SceneGraphNode score = new SceneGraphNode();
        DangineStringPicture text = new DangineStringPicture();
        score.setPosition(getLabelLocationFromPlayerId(scoreKeeper.getPlayerIdToScore().size()));
        score.addChild(text);
        scoreKeeper.getBase().addChild(score);
        scoreKeeper.getPlayerIdToTextNode().put(botId, text);

        PlayerScore playerScore = new PlayerScore(botId);
        scoreKeeper.getPlayerIdToScore().put(botId, playerScore);
        playerScore.setStock(0);
    }

    private Vector2f getLabelLocationFromPlayerId(int playerId) {
        int row = playerId / 2;
        float y = Utility.getResolution().y - (20 * (row + 1));
        int width = playerId % 2;
        float x = (Utility.getResolution().x / 2.0f) * width;
        return new Vector2f(x, y);
    }

    @Override
    public void onPlayerScores(int playerWhoScores, ScoreKeeper scoreKeeper) {
        Debugger.warn(this.getClass() + " has no onPlayerScores() logic");
    }

}
