package dangine.player;

import dangine.input.DangineControllerAssignments;
import dangine.input.DangineControllerAssignments.Device;
import dangine.input.DangineInputMapper;
import dangine.input.DangineKeyInputMapper;
import dangine.input.DangineSampleInput;
import dangine.input.DangineXboxControllerInputMapper;

public class DanginePlayer {

    final int playerId;
    DangineInputMapper inputMapper;
    DangineSampleInput input = new DangineSampleInput();
    DangineSampleInput previousInput = new DangineSampleInput();

    public DanginePlayer(int id) {
        Device device = DangineControllerAssignments.getDeviceForPlayer(id);
        switch (device) {
        case KEYBOARD_LEFT:
            inputMapper = new DangineKeyInputMapper(0);
            break;
        case KEYBOARD_RIGHT:
            inputMapper = new DangineKeyInputMapper(1);
            break;
        case CONTROLLER_ONE:
            inputMapper = new DangineXboxControllerInputMapper(0);
            break;
        case CONTROLLER_TWO:
            inputMapper = new DangineXboxControllerInputMapper(1);
            break;
        case CONTROLLER_THREE:
            inputMapper = new DangineXboxControllerInputMapper(2);
            break;
        case CONTROLLER_FOUR:
            inputMapper = new DangineXboxControllerInputMapper(3);
            break;
        default:
            break;
        }
        this.playerId = id;
    }

    public void updateInput() {
        previousInput.copyFrom(input);
        inputMapper.getInput(input);
    }

    public DangineSampleInput getCurrentInput() {
        return input;
    }

    public DangineSampleInput getPreviousInput() {
        return previousInput;
    }

    public int getPlayerId() {
        return playerId;
    }

    public DangineInputMapper getInputMapper() {
        return inputMapper;
    }

}
