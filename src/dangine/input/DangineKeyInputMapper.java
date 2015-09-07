package dangine.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class DangineKeyInputMapper implements DangineInputMapper {
    enum Action {
        BUTTON_ONE("B_ONE"), BUTTON_TWO("B_TWO"), BUTTON_THREE("B_TREE"), UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT(
                "RIGHT");
        final String name;

        Action(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public static final Map<Action, Integer> DEFAULTS = createDefaults();

    private static Map<Action, Integer> createDefaults() {
        Map<Action, Integer> result = new HashMap<Action, Integer>();
        // result.put(Action.BUTTON_ONE, Input.KEY_SPACE);
        // result.put(Action.BUTTON_TWO, Input.KEY_LSHIFT);
        // result.put(Action.BUTTON_THREE, Input.KEY_ESCAPE);
        // result.put(Action.UP, Input.KEY_W);
        // result.put(Action.DOWN, Input.KEY_S);
        // result.put(Action.LEFT, Input.KEY_A);
        // result.put(Action.RIGHT, Input.KEY_D);
        result.put(Action.BUTTON_ONE, Keyboard.KEY_SPACE);
        result.put(Action.BUTTON_TWO, Keyboard.KEY_LSHIFT);
        result.put(Action.BUTTON_THREE, Keyboard.KEY_ESCAPE);
        result.put(Action.UP, Keyboard.KEY_W);
        result.put(Action.DOWN, Keyboard.KEY_S);
        result.put(Action.LEFT, Keyboard.KEY_A);
        result.put(Action.RIGHT, Keyboard.KEY_D);

        // return Collections.unmodifiableMap(result);
        return result;
    }

    public static final Map<Action, Integer> DEFAULTS_P2 = createP2Defaults();

    private static Map<Action, Integer> createP2Defaults() {
        Map<Action, Integer> result = new HashMap<Action, Integer>();
        // result.put(Action.BUTTON_ONE, Input.KEY_ENTER);
        // result.put(Action.BUTTON_TWO, Input.KEY_RSHIFT);
        // result.put(Action.BUTTON_THREE, Input.KEY_BACK);
        // result.put(Action.UP, Input.KEY_UP);
        // result.put(Action.DOWN, Input.KEY_DOWN);
        // result.put(Action.LEFT, Input.KEY_LEFT);
        // result.put(Action.RIGHT, Input.KEY_RIGHT);
        result.put(Action.BUTTON_ONE, Keyboard.KEY_RETURN);
        result.put(Action.BUTTON_TWO, Keyboard.KEY_RSHIFT);
        result.put(Action.BUTTON_THREE, Keyboard.KEY_BACK);
        result.put(Action.UP, Keyboard.KEY_UP);
        result.put(Action.DOWN, Keyboard.KEY_DOWN);
        result.put(Action.LEFT, Keyboard.KEY_LEFT);
        result.put(Action.RIGHT, Keyboard.KEY_RIGHT);

        // return Collections.unmodifiableMap(result);
        return result;
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
        // Input slickInput = Utility.getGameContainer().getInput();
        // return slickInput.isKeyDown(keyToAction.get(button));
        int key = keyToAction.get(button);
        return DangineOpenGLInput.isKeyDown(key);
    }

    @Override
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

    public void remap(Action action, int newKey) {
        keyToAction.put(action, new Integer(newKey));
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        Action[] order = { Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT, Action.BUTTON_ONE, Action.BUTTON_TWO,
                Action.BUTTON_THREE };
        for (Action a : order) {
            Integer i = keyToAction.get(a);
            buffer.append(a.toString()).append(" -> ").append(Keyboard.getKeyName(i)).append("\n");
        }
        return buffer.toString();
    }
}
