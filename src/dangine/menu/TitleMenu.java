package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.menu.DangineMenuItem.Action;
import dangine.utility.Utility;

public class TitleMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    public TitleMenu() {
        menu.addItem(new DangineMenuItem("Versus", getPlayVersusAction()));
        menu.addItem(new DangineMenuItem("Settings", getSettingsMenuAction()));
        menu.addItem(new DangineMenuItem("Exit", getExitGameAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.75f));
    }

    @Override
    public void update() {
        if (Utility.getPlayers().getPlayers().isEmpty()) {
            return;
        }
        selector.update();
        selector.scan(menu.getItems());
    }

    @Override
    public IsDrawable getDrawable() {
        return menu.getDrawable();
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

    private Action getExitGameAction() {
        return new Action() {

            @Override
            public void execute() {
                System.exit(0);
            }
        };
    }
}