package dangine.entity.gameplay;

import dangine.utility.Utility;

public class MatchEndEvent implements MatchEvent {

    @Override
    public void process() {
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().applyEndOfRound();
    }

}
