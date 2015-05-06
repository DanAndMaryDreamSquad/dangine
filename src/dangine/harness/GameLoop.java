package dangine.harness;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.utils.GdxNativesLoader;

import dangine.demogame.DemoGame;
import dangine.game.DangineGame;
import dangine.image.Resources;
import dangine.utility.Utility;

public class GameLoop {
 
    /** time at last frame */
    long lastFrame;
     
    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;
     
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
 
        initGL(); // init OpenGL
        initGame();
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        DangineGame game = new DemoGame();
        game.init();
        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            
            game.update();
            update(delta);
            renderGL();
 
            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
 
        Display.destroy();
    }
     
    public void update(int delta) {
        updateFPS(); // update FPS Counter
    }
     
    /** 
     * Calculate how many milliseconds have passed 
     * since last frame.
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
     
    public void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
    public void initGame() {
        GdxNativesLoader.load();
        Utility.initialize(null, null, this);
        Resources.initialize();
    }
 
    public void renderGL() {
        // Clear The Screen And The Depth Buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
 
    }
     
    public static void main(String[] argv) {
        GameLoop timerExample = new GameLoop();
        timerExample.start();
    }
}
