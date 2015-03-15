package dangine.harness;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import dangine.game.DangineGame;
import dangine.image.Resources;
import dangine.utility.Utility;

public class GameHarness extends BasicGame {
    Provider<DangineGame> provider;
    DangineGame dangineGame;

    public GameHarness(String title, Provider<DangineGame> provider) {
        super(title);
        this.provider = provider;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Utility.initialize(this, gc);
        Utility.devMode();
        Resources.initialize();
        dangineGame = provider.get();
        dangineGame.init();
    }

    @Override
    public void render(GameContainer gc, Graphics graphics) throws SlickException {
        dangineGame.draw();
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Utility.getGameTime().updateTime(delta);
        Utility.getPlayers().updateInput();
        dangineGame.update();
    }

}
