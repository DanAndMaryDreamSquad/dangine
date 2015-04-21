package dangine.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.controller.device.Button;
import com.esotericsoftware.controller.input.XInputXboxController;

public class NewPlayerMarshal {

    enum Status {
        NOT_CONNECTED, CONNECTED, IN_USE
    }

    Map<Integer, Status> controllerStatus = new HashMap<Integer, NewPlayerMarshal.Status>();
    boolean keyboardInUse = false;

    public NewPlayerMarshal() {
        List<XInputXboxController> controllers = XInputXboxController.getXInputControllers();
        for (XInputXboxController c : controllers) {
            controllerStatus.put(c.getPort(), Status.NOT_CONNECTED);
        }
    }

    public void update() {
        List<XInputXboxController> controllers = XInputXboxController.getXInputControllers();
        for (XInputXboxController c : controllers) {
            c.poll();
            if (controllerStatus.get(c.getPort()) == Status.NOT_CONNECTED && c.isConnected()) {
                controllerStatus.put(c.getPort(), Status.CONNECTED);
            }
            if (controllerStatus.get(c.getPort()) == Status.CONNECTED && !c.isConnected()) {
                controllerStatus.put(c.getPort(), Status.NOT_CONNECTED);
            }
            if (controllerStatus.get(c.getPort()) == Status.CONNECTED) {
                if (c.get(Button.start)) {
                    clickInPlayer(c);
                }
            }
        }

        if (!keyboardInUse) {
            // if (KeyboardManager.getCurrentInput().isKeyDown(Input.KEY_ENTER))
            // {
            // addKeyboardPlayer();
            // }
        }
    }

    public void clickInPlayer(XInputXboxController controller) {
        controllerStatus.put(controller.getPort(), Status.IN_USE);
    }

    public void addKeyboardPlayer() {
        keyboardInUse = true;

    }

    public void freeController(int port) {
        controllerStatus.put(port, Status.NOT_CONNECTED);
    }
}
