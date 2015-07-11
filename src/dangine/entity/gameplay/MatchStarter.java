package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.matchtypes.FFAStockModeLogic;
import dangine.entity.gameplay.matchtypes.MatchTypeLogic;
import dangine.entity.gameplay.matchtypes.SoccerModeLogic;
import dangine.entity.gameplay.matchtypes.TeamStockModeLogic;
import dangine.entity.gameplay.matchtypes.WinByTwoLogic;
import dangine.utility.Utility;

public class MatchStarter implements IsUpdateable {

    public enum MatchType {
        VERSUS {
            @Override
            public boolean isTeamMode() {
                return false;
            }

            @Override
            public MatchTypeLogic createMatchTypeLogic() {
                return new FFAStockModeLogic(false);
            }
        },
        TEAM_VERSUS {
            @Override
            public boolean isTeamMode() {
                return true;
            }

            @Override
            public MatchTypeLogic createMatchTypeLogic() {
                return new TeamStockModeLogic(false);
            }
        },
        BOT_MATCH {
            @Override
            public boolean isTeamMode() {
                return false;
            }

            @Override
            public MatchTypeLogic createMatchTypeLogic() {
                return new FFAStockModeLogic(true);
            }
        },
        COOP_VS_BOTS {
            @Override
            public boolean isTeamMode() {
                return true;
            }

            @Override
            public MatchTypeLogic createMatchTypeLogic() {
                return new TeamStockModeLogic(true);
            }
        },
        WIN_BY_TWO {
            @Override
            public boolean isTeamMode() {
                return false;
            }

            @Override
            public MatchTypeLogic createMatchTypeLogic() {
                return new WinByTwoLogic();
            }
        },
        SOCCER {
            @Override
            public boolean isTeamMode() {
                return true;
            }

            @Override
            public MatchTypeLogic createMatchTypeLogic() {
                return new SoccerModeLogic();
            }
        };
        public abstract boolean isTeamMode();

        public abstract MatchTypeLogic createMatchTypeLogic();

        public static int getColliderId(int heroId) {
            switch (Utility.getMatchParameters().getMatchType()) {
            case VERSUS:
            case BOT_MATCH:
            case WIN_BY_TWO:
                return heroId;
            case TEAM_VERSUS:
            case COOP_VS_BOTS:
            case SOCCER:
                if (Utility.getMatchParameters().isFriendlyFire()) {
                    return heroId;
                }
                return Utility.getMatchParameters().getPlayerTeam(heroId);
            }
            return heroId;
        }
    }

    MatchType matchType;
    MatchParameters matchParameters = null;

    public MatchStarter() {
        this.matchType = Utility.getMatchParameters().getMatchType();
    }

    public MatchStarter(MatchType matchType) {
        this.matchType = matchType;
    }

    @Override
    public void update() {
        Debugger.info("Starting match " + matchType);
        switch (matchType) {
        case VERSUS:
        case WIN_BY_TWO:
            Utility.getGameLoop().startMatch();
            return;
        case TEAM_VERSUS:
        case SOCCER:
            Utility.getGameLoop().startMatch();
            return;
        case BOT_MATCH:
        case COOP_VS_BOTS:
            Utility.getGameLoop().startBotMatch();
            return;
        }
    }
}
