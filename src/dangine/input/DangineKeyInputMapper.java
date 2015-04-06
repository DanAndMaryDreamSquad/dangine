package dangine.input;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Input;

import dangine.utility.Utility;

public class DangineKeyInputMapper {
    enum Action {
        BUTTON_ONE, BUTTON_TWO, BUTTON_THREE, UP, DOWN, LEFT, RIGHT;
    }

    private static final Map<Action, Integer> DEFAULTS = createDefaults();

    private static Map<Action, Integer> createDefaults() {
        Map<Action, Integer> result = new HashMap<Action, Integer>();
        result.put(Action.BUTTON_ONE, Input.KEY_SPACE);
        result.put(Action.BUTTON_TWO, Input.KEY_LSHIFT);
        result.put(Action.BUTTON_THREE, Input.KEY_ESCAPE);
        result.put(Action.UP, Input.KEY_W);
        result.put(Action.DOWN, Input.KEY_S);
        result.put(Action.LEFT, Input.KEY_A);
        result.put(Action.RIGHT, Input.KEY_D);

        return Collections.unmodifiableMap(result);
    }

    private static final Map<Action, Integer> DEFAULTS_P2 = createP2Defaults();

    private static Map<Action, Integer> createP2Defaults() {
        Map<Action, Integer> result = new HashMap<Action, Integer>();
        result.put(Action.BUTTON_ONE, Input.KEY_ENTER);
        result.put(Action.BUTTON_TWO, Input.KEY_RSHIFT);
        result.put(Action.BUTTON_THREE, Input.KEY_BACK);
        result.put(Action.UP, Input.KEY_UP);
        result.put(Action.DOWN, Input.KEY_DOWN);
        result.put(Action.LEFT, Input.KEY_LEFT);
        result.put(Action.RIGHT, Input.KEY_RIGHT);

        return Collections.unmodifiableMap(result);
    }

    Map<Action, Integer> keyToAction;

    public DangineKeyInputMapper(int id) {
        if (id == 0) {
            keyToAction = DEFAULTS;
        } else {
            keyToAction = DEFAULTS_P2;
        }
    }

    private boolean isBeingPressed(Action button) {
        Input slickInput = Utility.getGameContainer().getInput();
        return slickInput.isKeyDown(keyToAction.get(button));
    }

    public DangineSampleInput getInput(DangineSampleInput input) {
        input.setUp(isBeingPressed(Action.UP));
        input.setDown(isBeingPressed(Action.DOWN));
        input.setLeft(isBeingPressed(Action.LEFT));
        input.setRight(isBeingPressed(Action.RIGHT));
        input.setButtonOne(isBeingPressed(Action.BUTTON_ONE));
        input.setButtonTwo(isBeingPressed(Action.BUTTON_TWO));
        input.setButtonThree(isBeingPressed(Action.BUTTON_THREE));
        return input;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        Action[] order = { Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT, Action.BUTTON_ONE, Action.BUTTON_TWO,
                Action.BUTTON_THREE };
        for (Action a : order) {
            Integer i = keyToAction.get(a);
            buffer.append(a.toString()).append(" -> ").append(Input.getKeyName(i)).append("\n");
        }
        return buffer.toString();
    }
}
