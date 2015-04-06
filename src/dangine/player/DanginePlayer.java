package dangine.player;

import dangine.input.DangineKeyInputMapper;
import dangine.input.DangineSampleInput;

public class DanginePlayer {

    final int playerId;
    DangineKeyInputMapper inputMapper;
    DangineSampleInput input = new DangineSampleInput();
    DangineSampleInput previousInput = new DangineSampleInput();

    public DanginePlayer(int id) {
        inputMapper = new DangineKeyInputMapper(id);
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

    public DangineKeyInputMapper getInputMapper() {
        return inputMapper;
    }

}
