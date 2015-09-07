package dangine.menu;

import dangine.entity.gameplay.MatchEvent;
import dangine.input.DangineControllerAssignments;
import dangine.utility.Utility;

public class ClearControlsEvent implements MatchEvent {

    @Override
    public void process() {
        Utility.getPlayers().removeAllPlayers();
        DangineControllerAssignments.clear();

        ControlsAssigner controlsAssigner = new ControlsAssigner(false);
        controlsAssigner.waitForABit();
        Utility.getActiveScene().addUpdateable(controlsAssigner);
    }

}
