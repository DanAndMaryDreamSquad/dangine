package dangine.menu;

import java.util.ArrayList;
import java.util.List;

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
        List<DangineMenuItem> items = new ArrayList<DangineMenuItem>();
        items.add(new DangineMenuItem("Free For All", getFreeForAllAction()));
        items.add(new DangineMenuItem("Team Battle", getTeamBattleAction()));
        items.add(new DangineMenuItem("Bot Battle", getBotBattleAction()));
        items.add(new DangineMenuItem("Co-op VS Bots", getCoopVsBotsAction()));
        items.add(new DangineMenuItem("Win By Two", getWinByTwoOptions()));
        // items.add(new DangineMenuItem("Soccer Mode", getSoccerModeOption()));
        items.add(new DangineMenuItem("Back", getOnEscapeAction()));

        for (int i = 0; i < items.size(); i++) {
            menu.addItem(items.get(i));
        }
        DangineFormatter.formatDoubleWide(menu.getBase().getChildNodes());
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x * 0.25f, Utility.getResolution().y * (0.25f));

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

    @SuppressWarnings("unused")
    private Action getSoccerModeOption() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setMatchType(MatchType.SOCCER);
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
