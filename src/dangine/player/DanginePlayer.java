package dangine.player;

import dangine.input.DangineKeyInputMapper;
import dangine.input.DangineSampleInput;

public class DanginePlayer {

    DangineKeyInputMapper inputMapper;
    DangineSampleInput input = new DangineSampleInput();
    DangineSampleInput previousInput = new DangineSampleInput();

    public DanginePlayer() {
        inputMapper = new DangineKeyInputMapper();
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

}
