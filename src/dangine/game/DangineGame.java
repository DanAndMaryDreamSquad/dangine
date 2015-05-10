package dangine.game;

import dangine.scene.SceneSchema;

public interface DangineGame {

    public void update();

    public void populateRenderQueue();

    public void init();

    public void init(SceneSchema schema);

}
