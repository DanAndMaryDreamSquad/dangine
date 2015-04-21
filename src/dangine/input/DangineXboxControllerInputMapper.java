package dangine.input;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.controller.device.Axis;
import com.esotericsoftware.controller.device.Button;
import com.esotericsoftware.controller.input.XInputXboxController;

import dangine.input.DangineKeyInputMapper.Action;

public class DangineXboxControllerInputMapper implements DangineInputMapper {

    final float DEAD_ZONE = 0.25f;
    final float DEAD_MAGNITUDE = 0.25f;

    XInputXboxController xboxController;
    Map<Action, Button> keyToAction;

    public static final Map<Action, Button> DEFAULTS = createP1Defaults();

    private static Map<Action, Button> createP1Defaults() {
        Map<Action, Button> result = new HashMap<Action, Button>();
        result.put(Action.BUTTON_ONE, Button.a);
        result.put(Action.BUTTON_TWO, Button.x);
        result.put(Action.BUTTON_THREE, Button.b);

        return Collections.unmodifiableMap(result);
    }

    public DangineXboxControllerInputMapper(int port) {
        keyToAction = createP1Defaults();
        List<XInputXboxController> controllers = XInputXboxController.getXInputControllers();
        XInputXboxController p1 = controllers.get(port);
        p1.poll();
        xboxController = p1;
    }

    @Override
    public DangineSampleInput getInput(DangineSampleInput input) {
        xboxController.poll();
        input = fillJoysticks(input);
        input = fillButtons(input);
        return input;
    }

    protected DangineSampleInput fillButtons(DangineSampleInput input) {
        boolean oneDown = xboxController.get(keyToAction.get(Action.BUTTON_ONE));
        boolean twoDown = xboxController.get(keyToAction.get(Action.BUTTON_TWO));
        boolean threeDown = xboxController.get(keyToAction.get(Action.BUTTON_THREE));
        input.setButtonOne(oneDown);
        input.setButtonTwo(twoDown);
        input.setButtonThree(threeDown);
        return input;
    }

    protected DangineSampleInput fillJoysticks(DangineSampleInput input) {
        input.setLeft(false);
        input.setRight(false);
        input.setUp(false);
        input.setDown(false);
        float lxVal = xboxController.get(Axis.leftStickX);
        float lyVal = xboxController.get(Axis.leftStickY);
        if (lxVal < -DEAD_ZONE) {
            input.setLeft(true);
        }
        if (lxVal > DEAD_ZONE) {
            input.setRight(true);
        }
        if (lyVal > DEAD_ZONE) {
            input.setUp(true);
        }
        if (lyVal < -DEAD_ZONE) {
            input.setDown(true);
        }
        // float rxVal = xboxController.get(Axis.rightStickX);
        // float ryVal = xboxController.get(Axis.rightStickY);
        // float magnitude = (rxVal * rxVal) + (ryVal * ryVal);
        return input;
    }

    public int getPort() {
        return xboxController.getPort();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Movement").append(" -> ").append("Left Stick").append("\n");

        Action[] order = { Action.BUTTON_ONE, Action.BUTTON_TWO, Action.BUTTON_THREE };
        for (Action a : order) {
            Button b = keyToAction.get(a);
            buffer.append(a.toString()).append(" -> ").append(b.toString()).append("\n");
        }
        return buffer.toString();
    }
}
