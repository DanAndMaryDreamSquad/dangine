package dangine.input;

import dangine.input.DangineKeyInputMapper.Action;

public interface DangineInputMapper {

    DangineSampleInput getInput(DangineSampleInput input);

    void remap(Action action, int newKey);
}
