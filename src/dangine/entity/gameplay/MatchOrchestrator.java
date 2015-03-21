package dangine.entity.gameplay;

import java.util.ArrayList;
import java.util.List;

public class MatchOrchestrator {
    ScoreKeeper scoreKeeper = new ScoreKeeper();

    List<MatchEvent> events = new ArrayList<MatchEvent>();
    List<MatchEvent> toAdd = new ArrayList<MatchEvent>();

    public MatchOrchestrator() {
    }

    public void resolveMatchEvents() {
        for (MatchEvent event : toAdd) {
            events.add(event);
        }
        toAdd.clear();
        for (MatchEvent event : events) {
            event.process();
        }
        events.clear();
    }

    public void addEvent(MatchEvent event) {
        toAdd.add(event);
    }

    public ScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }

}
