package dangine.entity.gameplay;

import java.util.ArrayList;
import java.util.List;

public class MatchOrchestrator {

    List<MatchEvent> events = new ArrayList<MatchEvent>();

    public void resolveMatchEvents() {
        for (MatchEvent event : events) {
            event.process();
        }
        events.clear();
    }

    public void addEvent(MatchEvent event) {
        events.add(event);
    }

}
