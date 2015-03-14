package dangine.utility;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import dangine.harness.GameHarness;
import dangine.player.Players;
import dangine.time.GameTime;

public class Utility {
    private static GameContainer gameContainer;
    private static GameTime gameTime;
    private static Players players;

    public static void initialize(GameHarness gameharness, GameContainer gameContainer) {
        Utility.gameContainer = gameContainer;
        Utility.gameTime = new GameTime();
        Utility.players = new Players();
    }

    public static void devMode() {
        gameContainer.setTargetFrameRate(60);
        gameContainer.setVSync(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        players.newPlayer();
    }

    public static Graphics getGraphics() {
        return gameContainer.getGraphics();
    }

    public static GameTime getGameTime() {
        return gameTime;
    }

    public static GameContainer getGameContainer() {
        return gameContainer;
    }

    public static Players getPlayers() {
        return players;
    }

}
