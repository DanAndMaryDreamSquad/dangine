package dangine.harness;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import dangine.game.DangineGame;
import dangine.utility.Utility;

public class GameHarness extends BasicGame {
    DangineGame dangineGame;

    public GameHarness(String title, DangineGame dangineGame) {
        super(title);
        this.dangineGame = dangineGame;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        Utility.initialize(gc);
        Utility.devMode();
        dangineGame.init();
    }

    @Override
    public void render(GameContainer gc, Graphics graphics) throws SlickException {
        dangineGame.draw();
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Utility.getGameTime().updateTime(delta);
        // System.out.println("deltaTime: " + delta + " | currentTick: " +
        // Utility.getGameTime().getTick()
        // + " total time " + Utility.getGameTime().getTotalElapsedTime());
        dangineGame.update();
    }

}
