package dangine.harness;

import dangine.demogame.LWJGL32Game;
import dangine.game.DangineGame;
import dangine.graphics.GameLoop;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;

public class LWJGLMain {
    public static void main(String[] args) {
        DangineSavedSettings.INSTANCE = DangineSavedSettings.load();
        if (!DangineSavedSettings.INSTANCE.isFullscreen()) {
            System.setProperty("org.lwjgl.opengl.Window.undecorated",
                    String.valueOf(DangineSavedSettings.INSTANCE.isBorderlessWindow()));
        }
        do {
            Utility.setReRunRequested(false);
            Utility.setCloseRequested(false);
            Provider<DangineGame> provider = new Provider<DangineGame>() {

                @Override
                public DangineGame get() {
                    return new LWJGL32Game();
                }
            };
            GameLoop loop = new GameLoop(provider);
            loop.run();
        } while (Utility.isReRunRequested());
    }

}
