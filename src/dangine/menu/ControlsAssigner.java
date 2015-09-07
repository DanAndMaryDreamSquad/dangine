package dangine.menu;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineStringPicture;
import dangine.input.DangineControllerAssignments;
import dangine.player.DanginePlayer;
import dangine.scene.CharacterSelect;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class ControlsAssigner implements IsUpdateable, HasDrawable {

    SceneGraphNode node = new SceneGraphNode();
    DangineStringPicture text = new DangineStringPicture();
    CharacterSelect characterSelect = null;
    boolean allowMoreThanOneAssignment;
    final float WAIT_TIMER_MAX = 300f;
    float waitTimer = 0;
    boolean shouldWait = false;

    public ControlsAssigner(boolean allowMoreThanOneAssignment) {
        this.allowMoreThanOneAssignment = allowMoreThanOneAssignment;
        node.addChild(text);
        node.setPosition(0, 0);
        text.setText(DangineControllerAssignments.getOptions());
    }

    public ControlsAssigner withCharacterSelect(CharacterSelect characterSelect) {
        this.characterSelect = characterSelect;
        return this;
    }

    public void waitForABit() {
        shouldWait = true;
        waitTimer = 0;
    }

    @Override
    public void update() {
        if (shouldWait) {
            Utility.getActiveScene().getParentNode().removeChild(node);
            waitTimer += Utility.getGameTime().getDeltaTimeF();
            if (waitTimer > WAIT_TIMER_MAX) {
                Utility.getActiveScene().getParentNode().addChild(node);
                shouldWait = false;
            }
            return;
        }
        DangineControllerAssignments.scan();
        if (DangineControllerAssignments.getSize() > Utility.getPlayers().getPlayers().size()) {
            Debugger.info("new player assigned to controller");
            DanginePlayer player = Utility.getPlayers().newPlayer();
            if (characterSelect != null) {
                characterSelect.onNewPlayer(player);
            }
            text.setText(DangineControllerAssignments.getOptions());
        }
        if (!allowMoreThanOneAssignment && Utility.getPlayers().getPlayers().size() > 0) {
            destroy();
        }
    }

    public void destroy() {
        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getParentNode().removeChild(node);
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
