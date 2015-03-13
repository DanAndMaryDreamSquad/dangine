package dangine.harness;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import dangine.demogame.DemoGame;
import dangine.game.DangineGame;

public class Main {

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new GameHarness("Dan and Mary's Magic Adventure!",
                new Provider<DangineGame>() {

                    @Override
                    public DangineGame get() {
                        return new DemoGame();
                    }
                }));
        app.start();
    }

}
