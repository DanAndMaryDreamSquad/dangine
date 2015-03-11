package dangine.harness;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import dangine.game.DemoGame;

public class Main {

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new GameHarness("I love Mary!", new DemoGame()));
        app.start();
    }

}
