package dangine.menu;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineStringPicture;
import dangine.input.ControlsExplainSceneGraph;
import dangine.menu.DangineMenuItem.Action;
import dangine.player.DanginePlayer;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;

public class ControlsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    DangineStringPicture antiAliasText = new DangineStringPicture();

    List<ControlsExplainSceneGraph> sceneGraphs = new ArrayList<ControlsExplainSceneGraph>();

    public ControlsMenu() {
        selector.setOnEscape(getExitMenuAction());
        menu.addItem(new DangineMenuItem("Done", getExitMenuAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());
        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.6f));
        menu.getItem(0).getBase().addChild(selector.getDrawable());

        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            ControlsExplainSceneGraph explain = new ControlsExplainSceneGraph(player.getPlayerId());
            Utility.getActiveScene().getParentNode().addChild(explain.getDrawable());
            sceneGraphs.add(explain);
        }

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

    private Action getExitMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                DangineSavedSettings.INSTANCE.save();
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(ControlsMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(ControlsMenu.this.getDrawable());

                for (ControlsExplainSceneGraph graph : sceneGraphs) {
                    Utility.getActiveScene().getParentNode().removeChild(graph.getDrawable());
                }
            }

        };
    }

}
