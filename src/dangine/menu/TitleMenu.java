package dangine.menu;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.VersioningSceneGraph;

public class TitleMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    SceneGraphNode node = new SceneGraphNode();
    StardustLogo logo = new StardustLogo();
    List<DangineMenuItem> items = new ArrayList<DangineMenuItem>();

    VersioningSceneGraph version = new VersioningSceneGraph();

    boolean skipFrame = false;

    public TitleMenu() {
        node.addChild(menu.getDrawable());
        node.addChild(logo.getDrawable());
        node.addChild(version.getDrawable());
        items = new ArrayList<DangineMenuItem>();
        items.add(new DangineMenuItem("Versus", getPlayVersusAction()));
        items.add(new DangineMenuItem("Settings", getSettingsMenuAction()));
        items.add(new DangineMenuItem("Graphics", getGraphicsMenuAction()));
        items.add(new DangineMenuItem("Controls", getControlsMenuAction()));
        items.add(new DangineMenuItem("Resolution", getResolutionMenuAction()));
        items.add(new DangineMenuItem("Reset Controller Assignments", getResetControllerAssignments()));
        items.add(new DangineMenuItem("Exit", getExitGameAction()));
        for (int i = 0; i < items.size(); i++) {
            menu.addItem(items.get(i));
        }

        DangineFormatter.formatAndCenter(items);
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition((Utility.getResolution().x * 0.5f), Utility.getResolution().y * (0.65f));
    }

    @Override
    public void update() {
        if (Utility.getPlayers().getPlayers().isEmpty()) {
            skipFrame = true;
            return;
        }
        if (skipFrame) {
            skipFrame = false;
            return;
        }
        selector.update();
        selector.scan(menu.getItems());
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    private Action getPlayVersusAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.VERSUS);
                MatchTypeMenu matchTypeMenu = new MatchTypeMenu();
                Utility.getActiveScene().addUpdateable(matchTypeMenu);
                Utility.getActiveScene().getParentNode().addChild(matchTypeMenu.getDrawable());
                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
            }
        };
    }

    private Action getSettingsMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                SettingsMenu settingsMenu = new SettingsMenu();
                Utility.getActiveScene().addUpdateable(settingsMenu);
                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
                Utility.getActiveScene().getParentNode().addChild(settingsMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
            }
        };
    }

    private Action getGraphicsMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                GraphicsMenu graphicsMenu = new GraphicsMenu();
                Utility.getActiveScene().addUpdateable(graphicsMenu);
                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
                Utility.getActiveScene().getParentNode().addChild(graphicsMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
            }
        };
    }

    private Action getControlsMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                ControlsMenu controlsMenu = new ControlsMenu();
                Utility.getActiveScene().addUpdateable(controlsMenu);
                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
                Utility.getActiveScene().getParentNode().addChild(controlsMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(logo.getDrawable());
            }
        };
    }

    private Action getResolutionMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                ResolutionMenu resolutionMenu = new ResolutionMenu();
                Utility.getActiveScene().addUpdateable(resolutionMenu);
                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
                Utility.getActiveScene().getParentNode().addChild(resolutionMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
            }
        };
    }

    private Action getResetControllerAssignments() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getActiveScene().getMatchOrchestrator().addEvent(new ClearControlsEvent());
                selector.scan(items.subList(0, 1));
            }
        };
    }

    private Action getExitGameAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.setCloseRequested(true);
            }
        };
    }
}