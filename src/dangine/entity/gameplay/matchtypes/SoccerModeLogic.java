package dangine.entity.gameplay.matchtypes;

import dangine.debugger.Debugger;
import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.PlayerScore;
import dangine.entity.gameplay.ScoreKeeper;
import dangine.entity.gameplay.soccer.SoccerBall;
import dangine.entity.gameplay.soccer.SoccerGoal;
import dangine.graphics.DangineStringPicture;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class SoccerModeLogic implements MatchTypeLogic {

    @Override
    public void setupMatch(ScoreKeeper scoreKeeper) {
        SoccerBall soccerBall = new SoccerBall();
        Utility.getActiveScene().addUpdateable(soccerBall);
        Utility.getActiveScene().getCameraNode().addChild(soccerBall.getDrawable());
        SoccerGoal soccerGoal = new SoccerGoal(0);
        Utility.getActiveScene().addUpdateable(soccerGoal);
        Utility.getActiveScene().getCameraNode().addChild(soccerGoal.getDrawable());
        soccerGoal = new SoccerGoal(1);
        Utility.getActiveScene().addUpdateable(soccerGoal);
        Utility.getActiveScene().getCameraNode().addChild(soccerGoal.getDrawable());

        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            scoreKeeper.addPlayerToGame(player.getPlayerId());
            if (!Utility.getMatchParameters().getPlayerIdToTeam().containsKey(player.getPlayerId())) {
                Utility.getMatchParameters().addPlayerTeam(player.getPlayerId(), 0);
            }
        }
        for (int teamId : Utility.getMatchParameters().getPlayerIdToTeam().values()) {
            if (!scoreKeeper.getTeamIdToScore().containsKey(teamId)) {
                addTeam(teamId, scoreKeeper);
            }
        }

        if (!scoreKeeper.getTeamIdToScore().containsKey(0)) {
            addTeam(0, scoreKeeper);
        }

        if (!scoreKeeper.getTeamIdToScore().containsKey(1)) {
            addTeam(1, scoreKeeper);
        }
        updateScoreBoardText(scoreKeeper);
    }

    @Override
    public void updateScoreBoardText(ScoreKeeper scoreKeeper) {
        for (PlayerScore score : scoreKeeper.getTeamIdToScore().values()) {
            int goals = score.getScore();
            scoreKeeper.getTeamIdToTextNode().get(score.getPlayerId())
                    .setText("Team " + score.getPlayerId() + " Goals: " + goals);
        }
    };

    @Override
    public void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper) {
    }

    @Override
    public boolean isSceneOver(ScoreKeeper scoreKeeper) {
        for (PlayerScore score : scoreKeeper.getTeamIdToScore().values()) {
            if (score.getScore() >= Utility.getMatchParameters().getGoalsRequired()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper) {
        return true;
    }

    @Override
    public MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper) {
        Debugger.info("round over");
        if (scoreKeeper.getTeamIdToScore().get(0).getScore() > scoreKeeper.getTeamIdToScore().get(1).getScore()) {
            return new TeamVictoryEvent(0);
        } else if (scoreKeeper.getTeamIdToScore().get(0).getScore() < scoreKeeper.getTeamIdToScore().get(1).getScore()) {
            return new TeamVictoryEvent(1);
        }
        return new TieVictoryEvent();
    }

    @Override
    public void addPlayer(int playerId, ScoreKeeper scoreKeeper) {
        PlayerScore playerScore = new PlayerScore(playerId);
        scoreKeeper.getPlayerIdToScore().put(playerId, playerScore);
        playerScore.setStock(0);
    }

    @Override
    public void addBot(int botId, ScoreKeeper scoreKeeper) {
        PlayerScore playerScore = new PlayerScore(botId);
        scoreKeeper.getPlayerIdToScore().put(botId, playerScore);
        playerScore.setStock(0);
    }

    public void addTeam(int teamId, ScoreKeeper scoreKeeper) {
        SceneGraphNode score = new SceneGraphNode();
        DangineStringPicture text = new DangineStringPicture();
        score.setPosition(getLabelLocationFromTeamId(teamId));
        score.addChild(text);
        scoreKeeper.getBase().addChild(score);
        scoreKeeper.getTeamIdToTextNode().put(teamId, text);

        PlayerScore teamScore = new PlayerScore(teamId);
        scoreKeeper.getTeamIdToScore().put(teamId, teamScore);
    }

    private Vector2f getLabelLocationFromTeamId(int teamId) {
        int row = teamId / 2;
        float y = Utility.getResolution().y - (100 * (row + 1));
        int width = teamId % 2;
        float x = ((Utility.getResolution().x / 2.0f) * width) + (Utility.getResolution().x / 8.0f);
        return new Vector2f(x, y);
    }

    @Override
    public void onPlayerScores(int playerWhoScores, ScoreKeeper scoreKeeper) {
        PlayerScore playerScore = scoreKeeper.getPlayerIdToScore().get(playerWhoScores);
        int score = playerScore.getScore();

        playerScore.setScore(score + 1);
    }

    @Override
    public void onTeamScores(int teamWhoScored, ScoreKeeper scoreKeeper) {
        PlayerScore teamScore = scoreKeeper.getTeamIdToScore().get(teamWhoScored);
        teamScore.setScore(teamScore.getScore() + 1);
    }

    @Override
    public boolean playerHasLivesLeft(int playerId, ScoreKeeper scoreKeeper) {
        return true;
    }

}
