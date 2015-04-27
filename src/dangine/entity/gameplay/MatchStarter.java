package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.IsUpdateable;
import dangine.utility.Utility;

public class MatchStarter implements IsUpdateable {

    public enum MatchType {
        VERSUS {
            @Override
            public boolean isTeamMode() {
                return false;
            }
        },
        TEAM_VERSUS {
            @Override
            public boolean isTeamMode() {
                return true;
            }
        },
        BOT_MATCH {
            @Override
            public boolean isTeamMode() {
                return false;
            }
        },
        COOP_VS_BOTS {
            @Override
            public boolean isTeamMode() {
                return true;
            }
        };
        public abstract boolean isTeamMode();

        public static int getColliderId(int heroId) {
            switch (Utility.getMatchParameters().getMatchType()) {
            case VERSUS:
            case BOT_MATCH:
                return heroId;
            case TEAM_VERSUS:
            case COOP_VS_BOTS:
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
            Utility.getGameHarness().startMatch();
            return;
        case TEAM_VERSUS:
            Utility.getGameHarness().startMatch();
            return;
        case BOT_MATCH:
        case COOP_VS_BOTS:
            Utility.getGameHarness().startBotMatch();
            return;
        }
    }
}
