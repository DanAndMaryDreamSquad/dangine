package dangine.menu;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter;
import dangine.menu.DangineMenuItem.Action;
import dangine.utility.Utility;

public class TitleMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    public TitleMenu() {
        menu.addItem(new DangineMenuItem("Play Game", getPlayGameAction()));
        menu.addItem(new DangineMenuItem("Settings", getSettingsMenuAction()));
        menu.addItem(new DangineMenuItem("Exit", getExitGameAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.75f));
    }

    @Override
    public void update() {
        selector.update();
        selector.scan(menu.getItems());
    }

    @Override
    public IsDrawable getDrawable() {
        return menu.getDrawable();
    }

    private Action getPlayGameAction() {
        return new Action() {

            @Override
            public void execute() {
                MatchStarter matchStarter = new MatchStarter();
                Utility.getActiveScene().addUpdateable(matchStarter);
            }
        };
    }

    private Action getSettingsMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                Debugger.info("Settings are not ready yet");
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