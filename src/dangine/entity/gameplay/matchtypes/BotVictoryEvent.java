package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.MatchRestarter;
import dangine.entity.visual.EndOfMatchBanner;
import dangine.entity.visual.EndOfRoundBanner;
import dangine.utility.Utility;

public class BotVictoryEvent implements MatchEvent {

    public BotVictoryEvent() {
        Utility.getMatchParameters().getRoundKeeper().onIdWonRound(-1);
    }

    @Override
    public void process() {
        Utility.getActiveScene().addUpdateable(new MatchRestarter());

        if (Utility.getMatchParameters().getRoundKeeper().shouldPlayAnotherRound()) {
            EndOfRoundBanner banner = new EndOfRoundBanner("victory", "robots");
            Utility.getActiveScene().addUpdateable(banner);
            Utility.getActiveScene().getParentNode().addChild(banner.getDrawable());
        } else {
            EndOfMatchBanner banner = new EndOfMatchBanner("match", "champion", "robots");
            Utility.getActiveScene().addUpdateable(banner);
            Utility.getActiveScene().getParentNode().addChild(banner.getDrawable());
        }
    }
}