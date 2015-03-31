package dangine.game;

import dangine.scene.SceneSchema;

public interface DangineGame {

    public void update();

    public void draw();

    public void init();

    public void init(SceneSchema schema);

}
