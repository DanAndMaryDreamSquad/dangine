package dangine.harness;

import org.newdawn.slick.SlickException;

import dangine.demogame.LWJGL32Game;
import dangine.game.DangineGame;
import dangine.graphics.GameLoop;

public class LWJGLMain {
    public static void main(String[] args) throws SlickException {
        Provider<DangineGame> provider = new Provider<DangineGame>() {
            
            @Override
            public DangineGame get() {
                return new LWJGL32Game();
            }
        };
        GameLoop loop = new GameLoop(provider);
        loop.run();
    }

}
