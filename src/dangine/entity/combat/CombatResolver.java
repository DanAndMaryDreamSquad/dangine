package dangine.entity.combat;

import java.util.ArrayList;
import java.util.List;

public class CombatResolver {

    List<CombatEvent> events = new ArrayList<CombatEvent>();

    public void resolveCombat() {
        for (CombatEvent event : events) {
            event.process(events);
        }
        events.clear();
    }

    public void addEvent(CombatEvent event) {
        events.add(event);
    }

}
