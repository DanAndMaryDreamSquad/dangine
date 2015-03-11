package dangine.utility;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import dangine.time.GameTime;

public class Utility {
    private static GameContainer gameContainer;
    private static GameTime gameTime;

    // Utility prevents any other class from instantiating
    public static void initialize(GameContainer gameContainer) {
        Utility.gameContainer = gameContainer;
        Utility.gameTime = new GameTime();
    }

    public static void devMode() {
        gameContainer.setTargetFrameRate(60);
        gameContainer.setVSync(true);
        gameContainer.setAlwaysRender(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static Graphics getGraphics() {
        return gameContainer.getGraphics();
    }

    public static GameTime getGameTime() {
        return gameTime;
    }

}
