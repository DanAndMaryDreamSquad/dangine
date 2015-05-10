package dangine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.utils.GdxNativesLoader;

import dangine.game.DangineGame;
import dangine.harness.Provider;
import dangine.utility.Utility;

public class GameLoop {
    Provider<DangineGame> provider;
    DangineGame dangineGame;

    /** time at last frame */
    long lastFrame;

    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;

    public GameLoop(Provider<DangineGame> provider) {
        this.provider = provider;
    }

    public void run() {
        GdxNativesLoader.load();
        DangineOpenGL.setupOpenGL();
        Utility.initialize(null, null, this);
        this.dangineGame = provider.get();
        this.dangineGame.init();
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            Utility.getGameTime().updateTime(delta);

            update();
            draw();

            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
        DangineOpenGL.destroyOpenGL();
    }

    private void update() {
        Utility.getPlayers().updateInput();
        dangineGame.update();
        updateFPS();
    }

    private void draw() {
        // Clear The Screen And The Depth Buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        dangineGame.populateRenderQueue();
        Utility.getRenderQueue32().render();
        Utility.getRenderQueue32().clear();
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     * 
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     * 
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

}
