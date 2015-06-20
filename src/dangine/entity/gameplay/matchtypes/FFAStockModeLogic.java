package dangine.entity.gameplay.matchtypes;

import java.util.ArrayList;
import java.util.List;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.debugger.Debugger;
import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.PlayerScore;
import dangine.entity.gameplay.ScoreKeeper;

public class FFAStockModeLogic implements MatchTypeLogic {

    @Override
    public void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper) {
        PlayerScore score = scoreKeeper.getPlayerIdToScore().get(defeatedPlayerId);
        score.setStock(score.getStock() - 1);
    }

    @Override
    public boolean isSceneOver(ScoreKeeper scoreKeeper) {
        int playersWithLivesLeft = 0;
        int botsWithLivesLeft = 0;
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                if (score.getPlayerId() >= 0) {
                    playersWithLivesLeft++;
                } else {
                    botsWithLivesLeft++;
                }
            }
        }
        if (botsWithLivesLeft > 0) {
            return playersWithLivesLeft == 0;
        }
        return playersWithLivesLeft <= 1;
    }

    @Override
    public boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper) {
        return scoreKeeper.getPlayerIdToScore().get(playerId).getStock() >= 0;
    }

    @Override
    public MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper) {
        Debugger.info("round over");
        SoundPlayer.play(SoundEffect.ROUND_OVER);
        List<Integer> playersLeft = new ArrayList<Integer>();
        List<Integer> botsLeft = new ArrayList<Integer>();
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                if (score.getPlayerId() >= 0) {
                    playersLeft.add(score.getPlayerId());
                } else {
                    botsLeft.add(score.getPlayerId());
                }
            }
        }
        if (botsLeft.size() > 0) {
            return new BotVictoryEvent();
        } else if (playersLeft.size() == 1) {
            return new VictoryEvent(playersLeft.get(0));
        } else if (playersLeft.size() == 0) {
            return new TieVictoryEvent();
        } else if (playersLeft.size() > 0) {
            return new VictoryEvent(playersLeft);
        }

        return null;
    }

}
