package dangine.harness;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import dangine.demogame.DemoGame;
import dangine.game.DangineGame;
import dangine.utility.Utility;

public class Main {

    static AppGameContainer app;

    public static void main(String[] args) throws SlickException {
        app = new AppGameContainer(new GameHarness("Stardust Symphony", new Provider<DangineGame>() {

            @Override
            public DangineGame get() {
                return new DemoGame();
            }
        }));
        app.setDisplayMode((int) Utility.getGameWindowResolution().x, (int) Utility.getGameWindowResolution().y, false);
        app.start();
    }

}
