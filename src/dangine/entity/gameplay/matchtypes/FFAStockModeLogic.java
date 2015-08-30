package dangine.entity.gameplay.matchtypes;

import java.util.ArrayList;
import java.util.List;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.debugger.Debugger;
import dangine.entity.gameplay.MatchEvent;
import dangine.entity.gameplay.PlayerScore;
import dangine.entity.gameplay.ScoreKeeper;
import dangine.graphics.DangineStringPicture;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class FFAStockModeLogic implements MatchTypeLogic {

    boolean hasBots = false;

    public FFAStockModeLogic(boolean hasBots) {
        this.hasBots = hasBots;
    }

    @Override
    public void setupMatch(ScoreKeeper scoreKeeper) {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            scoreKeeper.addPlayerToGame(player.getPlayerId());
        }
        if (hasBots) {
            for (int i = 1; i < Utility.getMatchParameters().getNumberOfBots() + 1; i++) {
                scoreKeeper.addBotToGame(-i);
            }
        }
        updateScoreBoardText(scoreKeeper);
    }

    @Override
    public void updateScoreBoardText(ScoreKeeper scoreKeeper) {
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getPlayerId() < 0) {
                int botStock = scoreKeeper.getPlayerScore(score.getPlayerId()).getStock();
                scoreKeeper.getPlayerIdToTextNode().get(score.getPlayerId())
                        .setText("Bot Avatars Remaining: " + botStock);
                continue;
            }
            int stock = score.getStock();
            scoreKeeper.getPlayerIdToTextNode().get(score.getPlayerId())
                    .setText("P" + score.getPlayerId() + " Avatars Remaining: " + stock);
        }
        scoreKeeper.setTimer(0);
    };

    @Override
    public void playerDefeatsSomeone(int winnerPlayerId, int defeatedPlayerId, ScoreKeeper scoreKeeper) {
        PlayerScore score = scoreKeeper.getPlayerIdToScore().get(defeatedPlayerId);
        score.setStock(score.getStock() - 1);
    }

    @Override
    public boolean isSceneOver(ScoreKeeper scoreKeeper) {
        int playersWithLivesLeft = 0;
        int botsWithLivesLeft = 0;
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                if (score.getPlayerId() >= 0) {
                    playersWithLivesLeft++;
                } else {
                    botsWithLivesLeft++;
                }
            }
        }
        if (botsWithLivesLeft > 0) {
            return playersWithLivesLeft == 0;
        }
        return playersWithLivesLeft <= 1;
    }

    @Override
    public boolean shouldPlayerRespawn(int playerId, ScoreKeeper scoreKeeper) {
        return scoreKeeper.getPlayerIdToScore().get(playerId).getStock() >= 0;
    }

    @Override
    public MatchEvent createVictoryEvent(ScoreKeeper scoreKeeper) {
        Debugger.info("round over");
        SoundPlayer.play(SoundEffect.ROUND_OVER);
        List<Integer> playersLeft = new ArrayList<Integer>();
        List<Integer> botsLeft = new ArrayList<Integer>();
        for (PlayerScore score : scoreKeeper.getPlayerIdToScore().values()) {
            if (score.getStock() >= 0) {
                if (score.getPlayerId() >= 0) {
                    playersLeft.add(score.getPlayerId());
                } else {
                    botsLeft.add(score.getPlayerId());
                }
            }
        }
        if (botsLeft.size() > 0) {
            return new BotVictoryEvent();
        } else if (playersLeft.size() == 1) {
            return new VictoryEvent(playersLeft.get(0));
        } else if (playersLeft.size() == 0) {
            return new TieVictoryEvent();
        } else if (playersLeft.size() > 0) {
            return new VictoryEvent(playersLeft);
        }

        return null;
    }

    @Override
    public void addPlayer(int playerId, ScoreKeeper scoreKeeper) {
        SceneGraphNode score = new SceneGraphNode();
        DangineStringPicture text = new DangineStringPicture();
        score.setPosition(getLabelLocationFromPlayerId(playerId));
        score.addChild(text);
        scoreKeeper.getBase().addChild(score);
        scoreKeeper.getPlayerIdToTextNode().put(playerId, text);

        PlayerScore playerScore = new PlayerScore(playerId);
        scoreKeeper.getPlayerIdToScore().put(playerId, playerScore);
    }

    @Override
    public void addBot(int botId, ScoreKeeper scoreKeeper) {
        SceneGraphNode score = new SceneGraphNode();
        DangineStringPicture text = new DangineStringPicture();
        score.setPosition(getLabelLocationFromPlayerId(scoreKeeper.getPlayerIdToScore().size()));
        score.addChild(text);
        scoreKeeper.getBase().addChild(score);
        scoreKeeper.getPlayerIdToTextNode().put(botId, text);

        PlayerScore playerScore = new PlayerScore(botId);
        scoreKeeper.getPlayerIdToScore().put(botId, playerScore);
    }

    private Vector2f getLabelLocationFromPlayerId(int playerId) {
        int row = playerId / 2;
        float y = Utility.getResolution().y - (100 * (row + 1));
        int width = playerId % 2;
        float x = 100 + (Utility.getResolution().x / 2.0f) * width;
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
