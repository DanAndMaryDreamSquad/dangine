package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter;
import dangine.entity.world.World;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class WorldSelectionMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    SceneGraphNode stockTextNode = new SceneGraphNode();
    DangineText stockText = new DangineText();
    SceneGraphNode movementModeTextNode = new SceneGraphNode();
    DangineText movementModeText = new DangineText();

    public WorldSelectionMenu() {
        menu.addItem(new DangineMenuItem("Random: ", getRandomWorldAction()));
        for (World world : World.values()) {
            menu.addItem(new DangineMenuItem(world.name(), getWorldAction(world)));
        }
        menu.addItem(new DangineMenuItem("Back", getExitMenuAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());

        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.75f));
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        updateText();
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

    private void updateText() {
    }

    private Action getRandomWorldAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setCurrentWorld(World.randomWorld());
                MatchStarter matchStarter = new MatchStarter();
                Utility.getActiveScene().addUpdateable(matchStarter);
            }
        };
    }

    private Action getWorldAction(final World world) {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setCurrentWorld(world);
                MatchStarter matchStarter = new MatchStarter();
                Utility.getActiveScene().addUpdateable(matchStarter);
            }
        };
    }

    private Action getExitMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(WorldSelectionMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(WorldSelectionMenu.this.getDrawable());
            }

        };
    }
}