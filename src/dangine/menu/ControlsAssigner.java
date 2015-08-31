package dangine.menu;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineFont;
import dangine.graphics.DangineOpenGL;
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

    public ControlsAssigner() {
        node.addChild(text);
        float offset = DangineFont.CHARACTER_HEIGHT_IN_PIXELS * DangineStringPicture.STRING_SCALE
                * DangineOpenGL.getWindowWorldAspectY();
        node.setPosition(Utility.getResolution().x / 2, Utility.getResolution().y - offset);
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
            // ControlsExplainSceneGraph explain = new
            // ControlsExplainSceneGraph(
            // Utility.getPlayers().getPlayers().size() - 1);
            // Utility.getActiveScene().getParentNode().addChild(explain.getDrawable());
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
