package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.MatchRestarter;
import dangine.entity.visual.EndOfMatchBanner;
import dangine.entity.visual.EndOfRoundBanner;
import dangine.utility.Utility;

public class TeamVictoryEvent implements MatchEvent {

    Integer victorId;

    public TeamVictoryEvent(Integer winningTeam) {
        this.victorId = winningTeam;
        Utility.getMatchParameters().getRoundKeeper().onIdWonRound(winningTeam);
    }

    @Override
    public void process() {
        Utility.getActiveScene().addUpdateable(new MatchRestarter());

        if (Utility.getMatchParameters().getRoundKeeper().shouldPlayAnotherRound()) {
            EndOfRoundBanner banner = new EndOfRoundBanner("victory", "team" + victorId);
            Utility.getActiveScene().addUpdateable(banner);
            Utility.getActiveScene().getParentNode().addChild(banner.getDrawable());
        } else {
            int matchWinner = Utility.getMatchParameters().getRoundKeeper().getWinner();
            EndOfMatchBanner banner = new EndOfMatchBanner("match", "champions", "team" + matchWinner);
            Utility.getActiveScene().addUpdateable(banner);
            Utility.getActiveScene().getParentNode().addChild(banner.getDrawable());
        }
    }
}