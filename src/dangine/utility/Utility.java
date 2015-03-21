package dangine.utility;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import dangine.harness.GameHarness;
import dangine.player.Players;
import dangine.scene.Scene;
import dangine.scenegraph.RenderQueue;
import dangine.time.GameTime;

public class Utility {
    private static GameContainer gameContainer;
    private static GameHarness gameHarness;
    private static GameTime gameTime;
    private static Players players;
    private static Scene activeScene;
    private static RenderQueue renderQueue;
    private static Vector2f resolution = new Vector2f(800, 600);

    public static void initialize(GameHarness gameharness, GameContainer gameContainer) {
        Utility.gameHarness = gameharness;
        Utility.gameContainer = gameContainer;
        Utility.gameTime = new GameTime();
        Utility.players = new Players();
        Utility.renderQueue = new RenderQueue();
    }

    public static void devMode() {
        gameContainer.setTargetFrameRate(60);
        gameContainer.setAlwaysRender(false);
        gameContainer.setVSync(true);
        // GL11.glEnable(GL11.GL_DEPTH_TEST);
        players.newPlayer();
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

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static void setActiveScene(Scene activeScene) {
        Utility.activeScene = activeScene;
    }

    public static RenderQueue getRenderQueue() {
        return renderQueue;
    }

    public static Vector2f getResolution() {
        return resolution;
    }

    public static GameHarness getGameHarness() {
        return gameHarness;
    }

}
