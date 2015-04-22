package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.menu.DangineMenuItem.Action;
import dangine.scene.CharacterSelect;
import dangine.utility.Utility;

public class TitleMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    public TitleMenu() {
        menu.addItem(new DangineMenuItem("Versus", getPlayVersusAction()));
        menu.addItem(new DangineMenuItem("Experimental Bot Mode", getBotModeAction()));
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
                CharacterSelect characterSelect = new CharacterSelect();
                Utility.getActiveScene().addUpdateable(characterSelect);
                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
            }
        };
    }

    private Action getBotModeAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.BOT_MATCH);
                CharacterSelect characterSelect = new CharacterSelect();
                Utility.getActiveScene().addUpdateable(characterSelect);
                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
//                MatchStarter matchStarter = new MatchStarter(MatchType.BOT_MATCH);
//                Utility.getActiveScene().addUpdateable(matchStarter);
//                Utility.getActiveScene().removeUpdateable(TitleMenu.this);
//                Utility.getActiveScene().getParentNode().removeChild(TitleMenu.this.getDrawable());
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