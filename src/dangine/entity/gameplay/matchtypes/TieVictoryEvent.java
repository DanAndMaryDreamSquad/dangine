package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.MatchRestarter;
import dangine.entity.visual.EndOfRoundBanner;
import dangine.utility.Utility;

public class TieVictoryEvent implements MatchEvent {
    @Override
    public void process() {
        Utility.getActiveScene().addUpdateable(new MatchRestarter());

        EndOfRoundBanner banner = new EndOfRoundBanner("no one", "claims victory");
        Utility.getActiveScene().addUpdateable(banner);
        Utility.getActiveScene().getParentNode().addChild(banner.getDrawable());
    }
}