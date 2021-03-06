package dangine.entity.gameplay.matchtypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.debugger.Debugger;
import dangine.entity.gameplay.LifeIndicator;
import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.PlayerScore;
import dangine.entity.gameplay.ScoreKeeper;
import dangine.graphics.DangineFont;
import dangine.graphics.DangineOpenGL;
import dangine.graphics.DangineStringPicture;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class TeamStockModeLogic implements MatchTypeLogic {

    boolean hasBots = false;

    public TeamStockModeLogic(boolean hasBots) {
        this.hasBots = hasBots;
    }

    @Override
    public void setupMatch(ScoreKeeper scoreKeeper) {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            scoreKeeper.addPlayerToGame(player.getPlayerId());
        }
        for (int i = 1; i < Utility.getMatchParameters().getNumberOfBots() + 1; i++) {
            scoreKeeper.addBotToGame(-i);
        }
        for (int teamId : Utility.getMatchParameters().getPlayerIdToTeam().values()) {
            if (!scoreKeeper.getTeamIdToScore().containsKey(teamId)) {
                addTeam(teamId, scoreKeeper);
            }
        }
        updateScoreBoardText(scoreKeeper);
    }

    public void addTeam(int teamId, ScoreKeeper scoreKeeper) {
        SceneGraphNode score = new SceneGraphNode();
        DangineStringPicture text = new DangineStringPicture();
        score.setPosition(getLabelLocationFromPlayerId(teamId));
        score.addChild(text);
        scoreKeeper.getBase().addChild(score);
        scoreKeeper.getTeamIdToTextNode().put(teamId, text);

        PlayerScore teamScore = new PlayerScore(teamId);
        scoreKeeper.getTeamIdToScore().put(teamId, teamScore);
    }

    @Override
    public void updateScoreBoardText(ScoreKeeper scoreKeeper) {
        for (Integer team : Utility.getMatchParameters().getPlayerIdToTeam().values()) {
            int teamWins = Utility.getMatchParameters().getRoundKeeper().getVictoriesForId(team);
            if (team < 0) {
                scoreKeeper.getTeamIdToTextNode().get(team).setText("bot rounds won:" + teamWins);
            } else {
                scoreKeeper.getTeamIdToTextNode().get(team).setText("team" + team + " rounds won:" + teamWins);
            }
        }
        scoreKeeper.setTimer(0);
    };

    @Override
    public void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper) {
        PlayerScore score = scoreKeeper.getPlayerIdToScore().get(defeatedPlayerId);
        score.setStock(score.getStock() - 1);

        if (Utility.getMatchParameters().isVampireMode() && winnerPlayerId != defeatedPlayerId) {
            PlayerScore winnerScore = scoreKeeper.getPlayerIdToScore().get(winnerPlayerId);
            if (winnerScore.getStock() < (float) Utility.getMatchParameters().getStartingStock() * 1.5f) {
                winnerScore.setStock(winnerScore.getStock() + 1);
            }
            List<LifeIndicator> lifeIndicators = Utility.getActiveScene().getUpdateables(LifeIndicator.class);
            for (LifeIndicator lifeIndicator : lifeIndicators) {
                if (winnerPlayerId == lifeIndicator.getOwnerId()) {
                    lifeIndicator.updateLives(winnerScore.getStock());
                }
            }
        }
    }

    @Override
    public boolean isSceneOver(ScoreKeeper scoreKeeper) {
        Set<Integer> teams = new HashSet<Integer>();
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                int teamId = Utility.getMatchParameters().getPlayerTeam(score.getPlayerId());
                teams.add(teamId);
            }
        }
        return teams.size() <= 1;
    }

    @Override
    public boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper) {
        return scoreKeeper.getPlayerIdToScore().get(playerId).getStock() >= 0;
    }

    @Override
    public MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper) {
        Debugger.info("round over");
        SoundPlayer.play(SoundEffect.ROUND_OVER);
        Set<Integer> teamsLeft = new HashSet<Integer>();
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                int teamId = Utility.getMatchParameters().getPlayerTeam(score.getPlayerId());
                teamsLeft.add(teamId);
            }
        }
        if (teamsLeft.size() > 1) {
            Debugger.warn("Somehow multiple teams won");
            return new TieVictoryEvent();
        }
        if (teamsLeft.size() == 0) {
            return new TieVictoryEvent();
        }

        return new TeamVictoryEvent(teamsLeft.iterator().next());
    }

    @Override
    public void addPlayer(int playerId, ScoreKeeper scoreKeeper) {
        // SceneGraphNode score = new SceneGraphNode();
        // DangineStringPicture text = new DangineStringPicture();
        // score.setPosition(getLabelLocationFromPlayerId(playerId));
        // score.addChild(text);
        // scoreKeeper.getBase().addChild(score);
        // scoreKeeper.getPlayerIdToTextNode().put(playerId, text);

        PlayerScore playerScore = new PlayerScore(playerId);
        scoreKeeper.getPlayerIdToScore().put(playerId, playerScore);
    }

    @Override
    public void addBot(int botId, ScoreKeeper scoreKeeper) {
        // SceneGraphNode score = new SceneGraphNode();
        // DangineStringPicture text = new DangineStringPicture();
        // score.setPosition(getLabelLocationFromPlayerId(scoreKeeper.getPlayerIdToScore().size()));
        // score.addChild(text);
        // scoreKeeper.getBase().addChild(score);
        // scoreKeeper.getPlayerIdToTextNode().put(botId, text);

        PlayerScore playerScore = new PlayerScore(botId);
        scoreKeeper.getPlayerIdToScore().put(botId, playerScore);
        Utility.getMatchParameters().addPlayerTeam(botId, -1);
    }

    private Vector2f getLabelLocationFromPlayerId(int playerId) {
        int row = playerId / 2;
        float y = Utility.getResolution().y - (100 * (row + 1));
        int width = playerId % 2;
        float x = 100 + (Utility.getResolution().x / 2.0f) * width;
        if (playerId < 0) {
            y -= DangineStringPicture.STRING_SCALE * DangineFont.CHARACTER_HEIGHT_IN_PIXELS
                    * DangineOpenGL.getWindowWorldAspectY();
            x = 100;
        }
        y = Math.abs(y);
        x = Math.abs(x);
        return new Vector2f(x, y);
    }

    @Override
    public void onPlayerScores(int playerWhoScores, ScoreKeeper scoreKeeper) {
        Debugger.warn(this.getClass() + " has no onPlayerScores() logic");
    }

    @Override
    public void onTeamScores(int teamWhoScored, ScoreKeeper scoreKeeper) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean playerHasLivesLeft(int playerId, ScoreKeeper scoreKeeper) {
        return scoreKeeper.getPlayerScore(playerId).getStock() > 0;
    }

}
