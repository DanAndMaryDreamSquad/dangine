package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

import dangine.player.DanginePlayer;
import dangine.utility.Utility;

public class RoundKeeper {

    int roundsRequiredToWin = 2;
    int currentRound;
    Map<Integer, Integer> playerIdToVictories;
    Map<Integer, ScoreKeeper> roundToScoreKeepers;

    public RoundKeeper() {
        clear();
    }

    public void onPlayerWonRound(int victorId) {
        Integer current = getVictoriesForPlayer(victorId);
        current++;
        playerIdToVictories.put(victorId, current);
    }

    public boolean shouldPlayAnotherRound() {
        for (Integer playerId : playerIdToVictories.keySet()) {
            if (playerIdToVictories.get(playerId) >= roundsRequiredToWin) {
                return false;
            }
        }
        return true;
    }

    public void incrementRound() {
        currentRound++;
    }

    public int getVictoriesForPlayer(int playerId) {
        Integer pid = new Integer(playerId);
        if (!playerIdToVictories.containsKey(pid)) {
            return 0;
        }
        return playerIdToVictories.get(pid);
    }

    public DanginePlayer getWinner() {
        for (Integer playerId : playerIdToVictories.keySet()) {
            if (playerIdToVictories.get(playerId) >= roundsRequiredToWin) {
                return Utility.getPlayers().getPlayer(playerId);
            }
        }
        return null;
    }

    public void clear() {
        playerIdToVictories = new HashMap<Integer, Integer>();
        roundToScoreKeepers = new HashMap<Integer, ScoreKeeper>();
        currentRound = 0;
    }

    public int getRoundsRequiredToWin() {
        return roundsRequiredToWin;
    }

    public void setRoundsRequiredToWin(int roundsRequiredToWin) {
        this.roundsRequiredToWin = roundsRequiredToWin;
    }

    public int getCurrentRound() {
        return currentRound;
    }

}
