package dangine.demogame;

import dangine.game.DangineGame;
import dangine.scene.Scene;
import dangine.scene.SceneSchema;
import dangine.scene.TitleSceneSchema32;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class LWJGL32Game implements DangineGame {

    Scene scene = new Scene();

    @Override
    public void init() {
        Utility.setActiveScene(scene);
        TitleSceneSchema32 schema = new TitleSceneSchema32();
        schema.apply(scene);
        
    }

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
