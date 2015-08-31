package dangine.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import com.esotericsoftware.controller.device.Button;
import com.esotericsoftware.controller.input.XInputXboxController;

import dangine.input.DangineKeyInputMapper.Action;

public class DangineControllerAssignments {

    static Map<Integer, Device> playerIdToInputDevice = new HashMap<Integer, Device>();

    public enum Device {
        KEYBOARD_LEFT {
            @Override
            public boolean isSelectedForUse() {
                if (inUse(this)) {
                    return false;
                }
                int key = DangineKeyInputMapper.DEFAULTS.get(Action.BUTTON_ONE);
                // Input slickInput = Utility.getGameContainer().getInput();
                // return slickInput.isKeyDown(key);
                if (DangineOpenGLInput.isKeyDown(key)) {
                    return true;
                }
                return false;
            }
        },
        KEYBOARD_RIGHT {
            @Override
            public boolean isSelectedForUse() {
                if (inUse(this)) {
                    return false;
                }
                int key = DangineKeyInputMapper.DEFAULTS_P2.get(Action.BUTTON_ONE);
                if (DangineOpenGLInput.isKeyDown(key)) {
                    return true;
                }
                return false;
            }
        },
        CONTROLLER_ONE {
            @Override
            public boolean isSelectedForUse() {
                if (inUse(this)) {
                    return false;
                }
                return checkController(1);
            }
        },
        CONTROLLER_TWO {
            @Override
            public boolean isSelectedForUse() {
                if (inUse(this)) {
                    return false;
                }
                return checkController(2);
            }
        },
        CONTROLLER_THREE {
            @Override
            public boolean isSelectedForUse() {
                if (inUse(this)) {
                    return false;
                }
                return checkController(3);
            }
        },
        CONTROLLER_FOUR {
            @Override
            public boolean isSelectedForUse() {
                if (inUse(this)) {
                    return false;
                }
                return checkController(4);
            }
        };
        public abstract boolean isSelectedForUse();

        public static boolean checkController(int port) {
            List<XInputXboxController> controllers = XInputXboxController.getXInputControllers();
            for (XInputXboxController c : controllers) {
                c.poll();
                if (c.getPort() == port && c.isConnected()) {
                    Button key = DangineXboxControllerInputMapper.DEFAULTS.get(Action.BUTTON_ONE);
                    if (c.get(key)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static boolean inUse(Device device) {
        return playerIdToInputDevice.values().contains(device);
    }

    public static void scan() {
        Device[] devices = Device.values();
        for (Device device : devices) {
            if (device.isSelectedForUse()) {
                playerIdToInputDevice.put(playerIdToInputDevice.size(), device);
            }
        }
    }

    public static void clear() {
        playerIdToInputDevice.clear();
    }

    public static int getSize() {
        return playerIdToInputDevice.size();
    }

    public static Device getDeviceForPlayer(int playerId) {
        return playerIdToInputDevice.get(new Integer(playerId));
    }

    public static void forceSetDeviceForPlayer(int playerId, Device device) {
        playerIdToInputDevice.put(playerId, device);
    }

    public static String getStatus() {
        StringBuffer buffer = new StringBuffer();
        for (Entry<Integer, Device> entry : playerIdToInputDevice.entrySet()) {
            buffer.append(entry.getKey() + " -> " + entry.getValue() + "\n");
        }
        return buffer.toString();
    }

    public static String getOptions() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("- Press -\n");
        if (!inUse(Device.KEYBOARD_LEFT)) {
            int key = DangineKeyInputMapper.DEFAULTS.get(Action.BUTTON_ONE);
            buffer.append(Keyboard.getKeyName(key) + "\n");
        }
        if (!inUse(Device.KEYBOARD_RIGHT)) {
            int key = DangineKeyInputMapper.DEFAULTS_P2.get(Action.BUTTON_ONE);
            buffer.append(Keyboard.getKeyName(key) + "\n");
        }
        if (!inUse(Device.CONTROLLER_ONE) || !inUse(Device.CONTROLLER_TWO) || !inUse(Device.CONTROLLER_THREE)
                || !inUse(Device.CONTROLLER_FOUR)) {
            Button key = DangineXboxControllerInputMapper.DEFAULTS.get(Action.BUTTON_ONE);
            buffer.append("Xbox " + key + "\n");
        }
        buffer.append("- to join -");
        return buffer.toString();
    }
}
