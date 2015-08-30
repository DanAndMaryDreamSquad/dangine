package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.ScoreKeeper;

public interface MatchTypeLogic {

    void setupMatch(ScoreKeeper scoreKeeper);

    void addPlayer(int playerId, ScoreKeeper scoreKeeper);

    void addBot(int botId, ScoreKeeper scoreKeeper);

    void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper);

    void onPlayerScores(int playerWhoScored, ScoreKeeper scoreKeeper);

    void onTeamScores(int teamWhoScored, ScoreKeeper scoreKeeper);

    void updateScoreBoardText(ScoreKeeper scoreKeeper);

    boolean isSceneOver(ScoreKeeper scoreKeeper);

    boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper);

    boolean playerHasLivesLeft(int playerId, ScoreKeeper scoreKeeper);

    MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper);

}
