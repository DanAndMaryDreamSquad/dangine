package dangine.utility;

import dangine.entity.gameplay.GameParameters;
import dangine.entity.gameplay.MatchParameters;
import dangine.graphics.DangineOpenGL;
import dangine.graphics.GameLoop;
import dangine.graphics.RenderQueue32;
import dangine.player.Players;
import dangine.scene.Scene;
import dangine.scenegraph.RenderQueue;
import dangine.time.GameTime;

public class Utility {
    private static GameTime gameTime;
    private static Players players;
    private static Scene activeScene;
    private static RenderQueue renderQueue;
    private static RenderQueue32 renderQueue32;
    private static GameLoop gameLoop;
    private static MatchParameters matchParameters = new MatchParameters();
    private static GameParameters gameParameters = new GameParameters();
    private static boolean isCloseRequested = false;
    private static boolean isReRunRequested = false;

    public static void initialize(GameLoop gameLoop) {
        Utility.gameTime = new GameTime();
        Utility.players = new Players();
        Utility.renderQueue = new RenderQueue();
        Utility.renderQueue32 = new RenderQueue32();
        Utility.gameLoop = gameLoop;
    }

    public static MatchParameters getMatchParameters() {
        return matchParameters;
    }

    public static void devMode() {
        // GL11.glEnable(GL11.GL_DEPTH_TEST);
        // players.newPlayer();
        // players.newPlayer();
    }

    public static GameTime getGameTime() {
        return gameTime;
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

    public static RenderQueue32 getRenderQueue32() {
        return renderQueue32;
    }

    public static Vector2f getResolution() {
        return DangineOpenGL.WORLD_RESOLUTION;
    }

    public static Vector2f getGameWindowResolution() {
        return DangineOpenGL.WINDOW_RESOLUTION;
    }

    public static GameLoop getGameLoop() {
        return gameLoop;
    }

    public static GameParameters getGameParameters() {
        return gameParameters;
    }

    public static boolean isCloseRequested() {
        return isCloseRequested;
    }

    public static void setCloseRequested(boolean isCloseRequested) {
        Utility.isCloseRequested = isCloseRequested;
    }

    public static boolean isReRunRequested() {
        return isReRunRequested;
    }

    public static void setReRunRequested(boolean isReRunRequested) {
        Utility.isReRunRequested = isReRunRequested;
    }

}
