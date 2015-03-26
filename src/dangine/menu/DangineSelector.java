package dangine.menu;

import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineShape;
import dangine.utility.Utility;

public class DangineSelector implements IsUpdateable, HasDrawable {

    int playerId = 0;
    final float ROTATION_SPEED = 0.16f;
    float angle = 0.0f;
    SceneGraphNode node = new SceneGraphNode();
    DangineShape shape = new DangineShape();
    DangineMenuItem currentItem = null;

    public DangineSelector() {
        node.addChild(shape);
        node.setCenterOfRotation(10, 10);
    }

    public DangineSelector(int playerId) {
        this.playerId = playerId;
        node.addChild(shape);
        node.setCenterOfRotation(10, 10);
    }

    @Override
    public void update() {
        angle += Utility.getGameTime().getDeltaTimeF() * ROTATION_SPEED;
        node.setAngle(angle);

        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        DangineSampleInput prevInput = Utility.getPlayers().getPlayer(playerId).getPreviousInput();
        if (currentItem != null) {
            currentItem.activate(input, prevInput);
        }
    }

    public void scan(List<DangineMenuItem> items) {
        if (items.isEmpty()) {
            return;
        }
        DangineMenuItem newSelection = null;
        if (currentItem == null) {
            newSelection = items.get(0);
        }
        int i = items.indexOf(currentItem);
        if (i == -1) {
            i = 0;
        }
        DangineSampleInput currentInput = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        DangineSampleInput previousInput = Utility.getPlayers().getPlayer(playerId).getPreviousInput();
        if (currentInput.isUp() && !previousInput.isUp()) {
            i--;
        }
        if (currentInput.isDown() && !previousInput.isDown()) {
            i++;
        }
        if (i < 0) {
            i = items.size() - 1;
        }
        if (i >= items.size()) {
            i = 0;
        }
        newSelection = items.get(i);
        if (newSelection != currentItem) {
            if (currentItem != null) {
                currentItem.getBase().removeChild(getDrawable());
            }
            newSelection.getBase().addChild(getDrawable());
            currentItem = newSelection;
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }
}
