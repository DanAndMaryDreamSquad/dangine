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

    public ControlsAssigner(boolean allowMoreThanOneAssignment) {
        this.allowMoreThanOneAssignment = allowMoreThanOneAssignment;
        node.addChild(text);
        node.setPosition(0, 0);
    }

    public ControlsAssigner withCharacterSelect(CharacterSelect characterSelect) {
        this.characterSelect = characterSelect;
        return this;
    }

    @Override
    public void update() {
        DangineControllerAssignments.scan();
        text.setText(DangineControllerAssignments.getOptions());
        while (DangineControllerAssignments.getSize() > Utility.getPlayers().getPlayers().size()) {
            Debugger.info("new player assigned to controller");
            DanginePlayer player = Utility.getPlayers().newPlayer();
            if (characterSelect != null) {
                characterSelect.onNewPlayer(player);
            }
        }
        if (!allowMoreThanOneAssignment && Utility.getPlayers().getPlayers().size() > 0) {
            Utility.getActiveScene().removeUpdateable(this);
            Utility.getActiveScene().getParentNode().removeChild(node);
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
