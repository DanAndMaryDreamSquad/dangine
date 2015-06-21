package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.menu.DangineMenuItem.Action;
import dangine.utility.Utility;

public class MatchTypeMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    public MatchTypeMenu() {
        selector.setOnEscape(getOnEscapeAction());
        menu.addItem(new DangineMenuItem("Free For All", getFreeForAllAction()));
        menu.addItem(new DangineMenuItem("Team Battle", getTeamBattleAction()));
        menu.addItem(new DangineMenuItem("Bot Battle", getBotBattleAction()));
        menu.addItem(new DangineMenuItem("Co-op VS Bots", getCoopVsBotsAction()));
        menu.addItem(new DangineMenuItem("Win By Two", getWinByTwoOptions()));
        menu.addItem(new DangineMenuItem("Back", getOnEscapeAction()));
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

    private Action getFreeForAllAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.VERSUS);
                removeMatchTypeMenu();
                goToCharacterSelect();
            }
        };
    }

    private Action getTeamBattleAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.TEAM_VERSUS);
                removeMatchTypeMenu();
                goToCharacterSelect();
            }
        };
    }

    private Action getBotBattleAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.BOT_MATCH);
                removeMatchTypeMenu();
                goToCharacterSelect();
            }
        };
    }

    private Action getCoopVsBotsAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.COOP_VS_BOTS);
                removeMatchTypeMenu();
                goToCharacterSelect();
            }
        };
    }

    private Action getWinByTwoOptions() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.WIN_BY_TWO);
                removeMatchTypeMenu();
                goToCharacterSelect();
            }
        };
    }

    private void goToCharacterSelect() {
        Utility.getGameLoop().startCharacterSelect();
    }

    private void removeMatchTypeMenu() {
        Utility.getActiveScene().removeUpdateable(MatchTypeMenu.this);
        Utility.getActiveScene().getParentNode().removeChild(MatchTypeMenu.this.getDrawable());
    }

    private Action getOnEscapeAction() {
        return new Action() {

            @Override
            public void execute() {
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                removeMatchTypeMenu();
            }
        };
    }

}
