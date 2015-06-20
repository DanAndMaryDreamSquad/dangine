package dangine.entity.gameplay.matchtypes;

import java.util.HashSet;
import java.util.Set;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.debugger.Debugger;
import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.PlayerScore;
import dangine.entity.gameplay.ScoreKeeper;
import dangine.utility.Utility;

public class TeamStockModeLogic implements MatchTypeLogic {

    @Override
    public void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper) {
        PlayerScore score = scoreKeeper.getPlayerIdToScore().get(defeatedPlayerId);
        score.setStock(score.getStock() - 1);
    }

    @Override
    public boolean isSceneOver(ScoreKeeper scoreKeeper) {
        Set<Integer> teams = new HashSet<Integer>();
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                int teamId = Utility.getMatchParameters().getPlayerTeam(score.getPlayerId());
                teams.add(teamId);
            }
        }
        return teams.size() <= 1;
    }

    @Override
    public boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper) {
        return scoreKeeper.getPlayerIdToScore().get(playerId).getStock() >= 0;
    }

    @Override
    public MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper) {
        Debugger.info("round over");
        SoundPlayer.play(SoundEffect.ROUND_OVER);
        Set<Integer> teamsLeft = new HashSet<Integer>();
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                int teamId = Utility.getMatchParameters().getPlayerTeam(score.getPlayerId());
                teamsLeft.add(teamId);
            }
        }
        if (teamsLeft.size() > 1) {
            Debugger.warn("Somehow multiple teams won");
            return new TieVictoryEvent();
        }
        if (teamsLeft.size() == 0) {
            return new TieVictoryEvent();
        }

        return new TeamVictoryEvent(teamsLeft.iterator().next());
    }

}
