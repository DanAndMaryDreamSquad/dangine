package dangine.entity.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombatResolver {

    public enum EventType {
        SWORD, //
        HERO, //
        OBSTRUCTION, //
        PROJECTILE, //
        BALL; //
    }

    static final Map<EventType, List<EventType>> typeToTargets = initializeTargets();

    private static Map<EventType, List<EventType>> initializeTargets() {
        Map<EventType, List<EventType>> result = new HashMap<EventType, List<EventType>>();
        result.put(EventType.SWORD, new ArrayList<CombatResolver.EventType>());
        result.get(EventType.SWORD).add(EventType.SWORD);

        result.put(EventType.HERO, new ArrayList<CombatResolver.EventType>());
        result.get(EventType.HERO).add(EventType.SWORD);

        result.put(EventType.OBSTRUCTION, new ArrayList<CombatResolver.EventType>());
        result.get(EventType.OBSTRUCTION).add(EventType.HERO);

        result.put(EventType.PROJECTILE, new ArrayList<CombatResolver.EventType>());
        result.get(EventType.PROJECTILE).add(EventType.SWORD);
        result.get(EventType.PROJECTILE).add(EventType.HERO);
        result.get(EventType.PROJECTILE).add(EventType.OBSTRUCTION);
        result.get(EventType.PROJECTILE).add(EventType.PROJECTILE);

        result.put(EventType.BALL, new ArrayList<CombatResolver.EventType>());
        result.get(EventType.BALL).add(EventType.SWORD);
        return Collections.unmodifiableMap(result);
    }

    Map<EventType, List<CombatEvent>> typeToEvents = new HashMap<CombatResolver.EventType, List<CombatEvent>>();

    List<CombatEvent> events = new ArrayList<CombatEvent>();
    public static int COUNTER;

    public CombatResolver() {
        for (EventType type : EventType.values()) {
            typeToEvents.put(type, new ArrayList<CombatEvent>());
        }
    }

    public void resolveCombat() {
        for (CombatEvent event : events) {
            for (EventType targetType : event.getTargets()) {
                List<CombatEvent> targetEvents = typeToEvents.get(targetType);
                event.process(targetEvents);
            }
        }
        events.clear();
        for (EventType type : EventType.values()) {
            typeToEvents.get(type).clear();
        }
    }

    public void addEvent(CombatEvent event) {
        events.add(event);
        typeToEvents.get(event.getType()).add(event);
    }

    public static Map<EventType, List<EventType>> getTypeToTargets() {
        return typeToTargets;
    }

}
