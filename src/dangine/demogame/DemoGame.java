package dangine.demogame;

import dangine.game.DangineGame;
import dangine.scene.Scene;
import dangine.scene.SceneSchema;
import dangine.utility.Utility;

public class DemoGame implements DangineGame {

    Scene scene = new Scene();

    @Override
    public void init(SceneSchema schema) {
        Utility.setActiveScene(scene);
        schema.apply(scene);
    }

    @Override
    public void update() {
        scene.update();
    }

    @Override
    public void populateRenderQueue() {
        scene.draw();
    }

}
