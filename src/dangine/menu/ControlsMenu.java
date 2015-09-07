package dangine.menu;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineStringPicture;
import dangine.input.ControlsExplainSceneGraph;
import dangine.input.DangineKeyRemapper;
import dangine.menu.DangineMenuItem.Action;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;

public class ControlsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    DangineStringPicture antiAliasText = new DangineStringPicture();

    List<ControlsExplainSceneGraph> sceneGraphs = new ArrayList<ControlsExplainSceneGraph>();

    public ControlsMenu() {
        selector.setOnEscape(getExitMenuAction());

        List<DangineMenuItem> items = new ArrayList<DangineMenuItem>();
        items.add(new DangineMenuItem("Done", getExitMenuAction()));
        items.add(new DangineMenuItem("Reconfigure left keyboard", getReconfigureAction(true)));
        items.add(new DangineMenuItem("Reconfigure right keyboard", getReconfigureAction(false)));
        for (int i = 0; i < items.size(); i++) {
            menu.addItem(items.get(i));
        }

        DangineFormatter.formatAndCenter(items);
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.6f));

        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            ControlsExplainSceneGraph explain = new ControlsExplainSceneGraph(player.getPlayerId());
            Utility.getActiveScene().getParentNode().addChild(explain.getDrawable());
            sceneGraphs.add(explain);
        }

        DangineStringPicture helpText = new DangineStringPicture();
        helpText.setText("xbox 360 controllers can be used, but not remapped."
                + "\nfor two player keyboard controls, use of " + "\nctrl, shift, enter and alt"
                + "\ncan help with max key-pressed limits");

        SceneGraphNode helpTextNode = new SceneGraphNode();
        helpTextNode.setPosition(-700, 200);
        helpTextNode.addChild(helpText);

        menu.getBase().addChild(helpTextNode);
    }

    @Override
    public IsDrawable getDrawable() {
        return menu.getDrawable();
    }

    @Override
    public void update() {
        selector.update();
        selector.scan(menu.getItems());
    }

    private Action getReconfigureAction(final boolean leftSide) {
        return new Action() {

            @Override
            public void execute() {
                DangineKeyRemapper remapper = new DangineKeyRemapper(leftSide);
                Utility.getActiveScene().addUpdateable(remapper);
                Utility.getActiveScene().getParentNode().addChild(remapper.getDrawable());
                destroy();
            }

        };
    }

    private Action getExitMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                DangineSavedSettings.INSTANCE.save();
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                destroy();
            }

        };
    }

    private void destroy() {
        Utility.getActiveScene().removeUpdateable(ControlsMenu.this);
        Utility.getActiveScene().getParentNode().removeChild(ControlsMenu.this.getDrawable());

        for (ControlsExplainSceneGraph graph : sceneGraphs) {
            Utility.getActiveScene().getParentNode().removeChild(graph.getDrawable());
        }
    }

}
