package dangine.entity.gameplay;

import java.util.ArrayList;
import java.util.List;

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
    SceneGraphNode score1 = new SceneGraphNode();
    DangineText text1 = new DangineText();
    SceneGraphNode score2 = new SceneGraphNode();
    DangineText text2 = new DangineText();
    List<PlayerScore> scores = new ArrayList<PlayerScore>();
    float timer = 0;
    final float FADE_TIME = 4000;
    final float FADE_DELAY = 2000;

    public ScoreKeeper() {
        score1.addChild(text1);
        score1.setPosition(100, 500);
        score2.addChild(text2);
        score2.setPosition(500, 500);
        base.addChild(score1);
        base.addChild(score2);
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
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
            text1.setAlpha(alpha);
            text2.setAlpha(alpha);
        }
    }

    public void addBotToGame() {
        scores.add(new PlayerScore(-1));
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
        for (PlayerScore score : scores) {
            if (score.getStock() < 0) {
                return true;
            }
        }
        return false;
    }

    public void applyEndOfRound() {
        List<Integer> playersLeft = new ArrayList<Integer>();
        for (PlayerScore score : scores) {
            if (score.getStock() >= 0) {
                playersLeft.add(score.getPlayerId());
            }
        }
        if (playersLeft.size() == 1) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new VictoryEvent(playersLeft.get(0)));
        }
        if (playersLeft.size() == 0) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new VictoryEvent(null));
        }
    }

    private void updatePlayerScores() {
        int p1Stock = getPlayerScore(0).getStock();
        text1.setText("Avatars Remaining: " + p1Stock);
        if (Utility.getPlayers().getPlayers().size() >= 2) {
            int p2Stock = getPlayerScore(1).getStock();
            text2.setText("Avatars Remaining: " + p2Stock);
        }
        if (Utility.getPlayers().getPlayers().size() < 2) {
            if (getPlayerScore(-1) != null) {
                int p2Stock = getPlayerScore(-1).getStock();
                text2.setText("Avatars Remaining: " + p2Stock);
            }
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
