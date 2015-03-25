package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.IsUpdateable;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class MatchStarter implements IsUpdateable {

    public MatchStarter() {
        Debugger.info("Hit enter to start...");
    }

    @Override
    public void update() {
        DangineSampleInput input = Utility.getPlayers().getPlayer(0).getCurrentInput();
        DangineSampleInput prevInput = Utility.getPlayers().getPlayer(0).getPreviousInput();
        if (input.isButtonOne() && !prevInput.isButtonOne()) {
            Debugger.info("Starting match");
            Utility.getGameHarness().startMatch();
        }

    }

}
