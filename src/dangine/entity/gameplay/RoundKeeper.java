package dangine.entity.gameplay;

import java.util.HashMap;
import java.util.Map;

public class RoundKeeper {

    int roundsRequiredToWin = 2;
    int currentRound;
    Map<Integer, Integer> idToVictories;
    Map<Integer, ScoreKeeper> roundToScoreKeepers;

    public RoundKeeper() {
        clear();
    }

    public void onIdWonRound(int victorId) {
        Integer current = getVictoriesForId(victorId);
        current++;
        idToVictories.put(victorId, current);
    }

    public boolean shouldPlayAnotherRound() {
        for (Integer playerId : idToVictories.keySet()) {
            if (idToVictories.get(playerId) >= roundsRequiredToWin) {
                return false;
            }
        }
        return true;
    }

    public void incrementRound() {
        currentRound++;
    }

    public int getVictoriesForId(int playerOrTeamId) {
        Integer pid = new Integer(playerOrTeamId);
        if (!idToVictories.containsKey(pid)) {
            return 0;
        }
        return idToVictories.get(pid);
    }

    public Integer getWinner() {
        for (Integer id : idToVictories.keySet()) {
            if (idToVictories.get(id) >= roundsRequiredToWin) {
                return id;
            }
        }
        return null;
    }

    public void clear() {
        idToVictories = new HashMap<Integer, Integer>();
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
