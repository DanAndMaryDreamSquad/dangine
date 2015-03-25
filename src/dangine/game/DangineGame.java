package dangine.game;

import dangine.scene.MatchSceneSchema;

public interface DangineGame {

    public void update();

    public void draw();

    public void init();

    public void init(MatchSceneSchema schema);

}
