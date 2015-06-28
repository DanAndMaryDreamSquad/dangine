package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.gameplay.soccer.SoccerBall;
import dangine.utility.Utility;

public class GoalScoreEvent implements MatchEvent {

    int goalOwner;
    int goalScorer;

    public GoalScoreEvent(int goalOwner, int goalScorer) {
        this.goalOwner = goalOwner;
        this.goalScorer = goalScorer;
    }

    @Override
    public void process() {
        Debugger.info("goooooooooooooooooooooal!!");
        SoccerBall soccerBall = Utility.getActiveScene().getUpdateable(SoccerBall.class);
        soccerBall.getPosition().set(Utility.getResolution().x / 2, Utility.getResolution().y / 2);
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().onPlayerScores(goalScorer);
        soccerBall.getMovement().setVelocity(0, 0);
    }

}
