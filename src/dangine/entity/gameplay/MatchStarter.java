package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.IsUpdateable;
import dangine.utility.Utility;

public class MatchStarter implements IsUpdateable {

    public enum MatchType {
        VERSUS, BOT_MATCH
    }

    MatchType matchType;
    MatchParameters matchParameters = null;

    public MatchStarter() {
        this.matchType = Utility.getMatchParameters().getMatchType();
    }

    public MatchStarter(MatchType matchType) {
        this.matchType = matchType;
    }

    @Override
    public void update() {
        Debugger.info("Starting match " + matchType);
        switch (matchType) {
        case VERSUS:
            Utility.getGameHarness().startMatch();
            return;
        case BOT_MATCH:
            Utility.getGameHarness().startBotMatch();
            return;
        }
    }
}
