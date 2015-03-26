package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.IsUpdateable;
import dangine.utility.Utility;

public class MatchStarter implements IsUpdateable {

    MatchParameters matchParameters = null;

    public MatchStarter() {
        Debugger.info("Hit enter to start...");
    }

    public MatchStarter(MatchParameters matchParameters) {
        this.matchParameters = matchParameters;
        Debugger.info("Hit enter to start...");
    }

    @Override
    public void update() {
        Debugger.info("Starting match");
        if (matchParameters != null) {
            Utility.getGameHarness().startMatch(matchParameters);
        } else {
            Utility.getGameHarness().startMatch();
        }
    }
}
