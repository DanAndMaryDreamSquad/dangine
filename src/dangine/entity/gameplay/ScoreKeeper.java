package dangine.entity.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class ScoreKeeper implements IsUpdateable, HasDrawable {

    boolean matchOver = false;
    SceneGraphNode base = new SceneGraphNode();
    List<SceneGraphNode> scoreNodes = new ArrayList<SceneGraphNode>();
    List<DangineText> textNodes = new ArrayList<DangineText>();
    List<PlayerScore> scores = new ArrayList<PlayerScore>();
    Map<Integer, DangineText> playerIdToTextNode = new HashMap<Integer, DangineText>();
    float timer = 0;
    final float FADE_TIME = 4000;
    final float FADE_DELAY = 2000;

    public ScoreKeeper() {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            SceneGraphNode score = new SceneGraphNode();
            DangineText text = new DangineText();
            playerIdToTextNode.put(player.getPlayerId(), text);
            score.addChild(text);
            scoreNodes.add(score);
            textNodes.add(text);
            base.addChild(score);
            int row = player.getPlayerId() / 2;
            float y = Utility.getResolution().y - (20 * (row + 1));
            int width = player.getPlayerId() % 2;
            float x = (Utility.getResolution().x / 2.0f) * width;
            score.setPosition(x, y);
            scores.add(new PlayerScore(player.getPlayerId()));
        }
        updatePlayerScores();
    }

    @Override
    public void update() {
        if (timer < FADE_TIME) {
            float alpha = 1.0f;
            timer += Utility.getGameTime().getDeltaTimeF();
            if (timer > FADE_DELAY) {
                alpha = 1.0f - ((timer - FADE_DELAY) / (FADE_TIME - FADE_DELAY));
            }
            for (DangineText text : textNodes) {
                text.setAlpha(alpha);
            }
        }
    }

    public void addBotToGame(int botId) {
        scores.add(new PlayerScore(botId));
        SceneGraphNode score = new SceneGraphNode();
        DangineText text = new DangineText();
        playerIdToTextNode.put(botId, text);
        score.addChild(text);
        scoreNodes.add(score);
        textNodes.add(text);
        base.addChild(score);

        int row = scores.size() / 2;
        float y = Utility.getResolution().y - (20 * (row + 1));
        int width = scores.size() % 2;
        float x = (Utility.getResolution().x / 2.0f) * width;
        score.setPosition(x, y);
        updatePlayerScores();
    }

    public void deductStock(int playerId) {
        PlayerScore score = getPlayerScore(playerId);
        score.setStock(score.getStock() - 1);
        updatePlayerScores();
        if (checkSceneOver() && !matchOver) {
            matchOver = true;
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new MatchEndEvent());
        }
    }

    public boolean checkSceneOver() {
        int playersWithLivesLeft = 0;
        int botsWithLivesLeft = 0;
        for (PlayerScore score : scores) {
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

    public boolean hasLivesLeft(int playerId) {
        return getPlayerScore(playerId).getStock() >= 0;
    }

    public void applyEndOfRound() {
        Debugger.info("round over");
        List<Integer> playersLeft = new ArrayList<Integer>();
        List<Integer> botsLeft = new ArrayList<Integer>();
        for (PlayerScore score : scores) {
            if (score.getStock() >= 0) {
                if (score.getPlayerId() >= 0) {
                    playersLeft.add(score.getPlayerId());
                } else {
                    botsLeft.add(score.getPlayerId());
                }
            }
        }
        if (botsLeft.size() > 0) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new VictoryEvent(botsLeft));
        } else if (playersLeft.size() == 1) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new VictoryEvent(playersLeft.get(0)));
        } else if (playersLeft.size() == 0) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new VictoryEvent(playersLeft));
        }
    }

    private void updatePlayerScores() {
        if (Utility.getPlayers().getPlayers().isEmpty()) {
            return;
        }
        for (PlayerScore score : scores) {
            if (score.getPlayerId() < 0) {
                int botStock = getPlayerScore(score.getPlayerId()).getStock();
                playerIdToTextNode.get(score.playerId).setText("Bot Avatars Remaining: " + botStock);
                continue;
            }
            int stock = score.getStock();
            playerIdToTextNode.get(score.playerId).setText("P" + score.getPlayerId() + " Avatars Remaining: " + stock);
        }
        timer = 0;
    }

    private PlayerScore getPlayerScore(int playerId) {
        for (PlayerScore score : scores) {
            if (score.getPlayerId() == playerId) {
                return score;
            }
        }
        return null;
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

}
