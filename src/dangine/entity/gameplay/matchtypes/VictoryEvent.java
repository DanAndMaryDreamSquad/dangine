package dangine.entity.gameplay.matchtypes;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.MatchRestarter;
import dangine.entity.visual.EndOfMatchBanner;
import dangine.entity.visual.EndOfRoundBanner;
import dangine.player.DanginePlayer;
import dangine.utility.Utility;

public class VictoryEvent implements MatchEvent {

    Integer victorId;

    public VictoryEvent(Integer playerId) {
        this.victorId = playerId;
        Utility.getMatchParameters().getRoundKeeper().onIdWonRound(playerId);
    }

    @Override
    public void process() {
        Utility.getActiveScene().addUpdateable(new MatchRestarter());

        if (Utility.getMatchParameters().getRoundKeeper().shouldPlayAnotherRound()) {
            EndOfRoundBanner banner = new EndOfRoundBanner("victory", "player" + victorId);
            Utility.getActiveScene().addUpdateable(banner);
            Utility.getActiveScene().getParentNode().addChild(banner.getDrawable());
        } else {
            int matchWinner = Utility.getMatchParameters().getRoundKeeper().getWinner();
            DanginePlayer winner = Utility.getPlayers().getPlayer(matchWinner);
            EndOfMatchBanner banner = new EndOfMatchBanner("match", "champion", "player" + winner.getPlayerId());
            Utility.getActiveScene().addUpdateable(banner);
            Utility.getActiveScene().getParentNode().addChild(banner.getDrawable());
        }
    }

}
