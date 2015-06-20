package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.ScoreKeeper;

public interface MatchTypeLogic {

    void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper);

    boolean isSceneOver(ScoreKeeper scoreKeeper);

    boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper);

    MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper);

}
