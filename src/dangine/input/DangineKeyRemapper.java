package dangine.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineFont;
import dangine.graphics.DangineStringPicture;
import dangine.input.DangineKeyInputMapper.Action;
import dangine.menu.ControlsMenu;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class DangineKeyRemapper implements IsUpdateable, HasDrawable {

    SceneGraphNode base = new SceneGraphNode();
    DangineStringPicture text = new DangineStringPicture();
    Action currentAction = null;
    List<Action> actions = new ArrayList<DangineKeyInputMapper.Action>();
    boolean leftSide;
    boolean wait = false;
    boolean finished = false;
    float waitTimer = 0;
    final float MAX_WAIT = 1000;
    Map<Action, Integer> activeMap;

    public DangineKeyRemapper(boolean leftSide) {
        this.leftSide = leftSide;
        String label;
        if (leftSide) {
            activeMap = DangineKeyInputMapper.DEFAULTS;
            label = "keyboard - left side";
        } else {
            activeMap = DangineKeyInputMapper.DEFAULTS_P2;
            label = "keyboard - right side";
        }
        base.addChild(text);
        actions.add(Action.UP);
        actions.add(Action.DOWN);
        actions.add(Action.LEFT);
        actions.add(Action.RIGHT);
        actions.add(Action.BUTTON_ONE);
        actions.add(Action.BUTTON_TWO);
        actions.add(Action.BUTTON_THREE);

        text.setText("Remapping " + label + "...");
        base.setPosition((Utility.getResolution().x / 2) - (DangineFont.getLengthInPixels(text.getText()) / 2),
                Utility.getResolution().y / 2);
        nextAction();
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    private void nextAction() {
        int i = actions.indexOf(currentAction);
        i++;
        waitTimer = 0;
        wait = true;
        if (i == actions.size()) {
            finished = true;
            return;
        }
        currentAction = actions.get(i);
    }

    @Override
    public void update() {
        if (wait) {
            waitTimer += Utility.getGameTime().getDeltaTimeF();
            if (waitTimer > MAX_WAIT) {
                wait = false;
                if (!finished) {
                    text.setText("Press the key for " + currentAction.toString());
                    base.setPosition((Utility.getResolution().x / 2)
                            - (DangineFont.getLengthInPixels(text.getText()) / 2), Utility.getResolution().y / 2);
                }
            }
            return;
        }
        if (finished) {
            onFinish();
            return;
        }
        int key = DangineOpenGLInput.getFirstKeyDown();
        if (key != -1) {
            activeMap.put(currentAction, key);
            text.setText("Mapped " + currentAction + " to " + Keyboard.getKeyName(key));
            base.setPosition((Utility.getResolution().x / 2) - (DangineFont.getLengthInPixels(text.getText()) / 2),
                    Utility.getResolution().y / 2);
            nextAction();
        }
    }

    public boolean isFinished() {
        return finished;
    }

    private void onFinish() {
        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getParentNode().removeChild(getDrawable());

        ControlsMenu controlsMenu = new ControlsMenu();
        Utility.getActiveScene().addUpdateable(controlsMenu);
        Utility.getActiveScene().getParentNode().addChild(controlsMenu.getDrawable());

    }
}
