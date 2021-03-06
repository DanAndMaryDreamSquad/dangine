package dangine.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.utils.GdxNativesLoader;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.DangineOpenAL;
import dangine.audio.DangineSounds;
import dangine.debugger.Debugger;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.game.DangineGame;
import dangine.harness.Provider;
import dangine.image.Resources;
import dangine.input.DangineOpenGLInput;
import dangine.scene.CharacterSelectSchema;
import dangine.scene.InstantMatchSceneSchema;
import dangine.scene.MatchSceneSchema;
import dangine.scene.TitleSceneSchema32;
import dangine.utility.DangineSavedSettings;
import dangine.utility.FunctionKeyEvents;
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
        int refreshRate = Display.getDesktopDisplayMode().getFrequency();
        Debugger.info("Detected desktop refresh rate: " + refreshRate);
        GdxNativesLoader.load();
        DangineOpenGL.setupOpenGL();
        DangineOpenAL.setupOpenAL();
        Utility.initialize(this);
        Resources.initialize();
        DangineShaders.setupShaders();
        DangineMusicPlayer.initialize();
        Cursor emptyCursor;
        try {
            emptyCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
            Mouse.setNativeCursor(emptyCursor);
        } catch (LWJGLException e) {
            Debugger.warn("Couldn't create invisible cursor!");
            e.printStackTrace();
        }
        Utility.getGameParameters().setMusicVolume(
                ((float) DangineSavedSettings.INSTANCE.getMusicVolumePercent()) / 100.0f);
        Utility.getGameParameters().setSoundEffectVolume(
                ((float) DangineSavedSettings.INSTANCE.getSoundVolumePercent()) / 100.0f);
        DangineMusicPlayer.startMusicPlayerThread();
        this.startTitleMenu();
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        while (!Display.isCloseRequested() && !Utility.isCloseRequested()) {
            int delta = getDelta();
            Utility.getGameTime().updateTime(delta);

            try {
                update();
            } catch (Exception e) {
                e.printStackTrace();
                DangineMusicPlayer.destroyMusicPlayerThread();
                break;
            }
            draw();

            Display.update();
            Display.sync(refreshRate); // TODO make this change based on the
                                       // resolution selected
        }
        DangineMusicPlayer.destroyMusicPlayerThread();
        DangineOpenGL.destroyOpenGL();
        DangineSounds.destroySounds();
        DangineOpenAL.destroyOpenAL();
    }

    private void update() {
        DangineOpenGLInput.poll();
        FunctionKeyEvents.processFunctionKeys();
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

    public void startTitleMenu() {
        Utility.getMatchParameters().getRoundKeeper().clear();
        dangineGame = provider.get();
        dangineGame.init(new TitleSceneSchema32());
    }

    public void startCharacterSelect() {
        Utility.getMatchParameters().getRoundKeeper().clear();
        dangineGame = provider.get();
        dangineGame.init(new CharacterSelectSchema());
    }

    public void startMatch() {
        dangineGame = provider.get();
        dangineGame.init(new MatchSceneSchema());
    }

    public void startInstanceTestMatch() {
        Utility.getMatchParameters().setMatchType(MatchType.SOCCER);
        dangineGame = provider.get();
        dangineGame.init(new InstantMatchSceneSchema());
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
