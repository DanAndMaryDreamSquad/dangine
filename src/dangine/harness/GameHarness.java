package dangine.harness;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.badlogic.gdx.utils.GdxNativesLoader;

import dangine.game.DangineGame;
import dangine.image.Resources;
import dangine.scene.MatchSceneSchema;
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
        GdxNativesLoader.load();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Utility.initialize(this, gc);
        Utility.devMode();
        Resources.initialize();
        dangineGame = provider.get();
        dangineGame.init();
    }

    public void restart() {
        dangineGame = provider.get();
        dangineGame.init();
    }

    public void startMatch() {
        dangineGame = provider.get();
        dangineGame.init(new MatchSceneSchema());
    }

    @Override
    public void render(GameContainer gc, Graphics graphics) throws SlickException {
        // Matrix4 matrix = new Matrix4();
        // matrix.translate(50, 50, 0);
        // matrix.rotate(0, 0, 1, 45);
        // FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        // fb.put(matrix.getValues(), 0, 16);
        // fb.flip();
        // GL11.glLoadMatrix(fb);

        // SceneGraphNode node = new SceneGraphNode();
        // node.setAngle(5);
        // node.setPosition(200, 0);
        //
        // SceneGraphNode other = new SceneGraphNode();
        // other.setPosition(200, 0);
        // other.addChild(new DangineShape());
        //
        // node.addChild(other);
        // node.updateTransforms();
        //
        dangineGame.draw();

        Utility.getRenderQueue().render();
        Utility.getRenderQueue().clear();
        GL11.glLoadIdentity();

        // graphics.setColor(Color.red);
        // graphics.fillRect(0, 0, 200, 200);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Utility.getGameTime().updateTime(delta);
        Utility.getPlayers().updateInput();
        dangineGame.update();
    }

}
