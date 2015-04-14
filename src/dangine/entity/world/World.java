package dangine.entity.world;

import dangine.scene.Scene;
import dangine.utility.MathUtility;

public enum World {

    DIMENSION("cloudy") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.EXTRADIMENSIONAL);
            World.addPanningSceneGraphs(scene, background);
        }
    },
    SUNSET("sunset2") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.SUNSET);
            PanningSceneGraph middleground = new PanningSceneGraph(Middleground.CLOUDS_LIGHT);
            PanningSceneGraph middleground2 = new PanningSceneGraph(Middleground.CLOUDS_LIGHT_2);
            World.addPanningSceneGraphs(scene, background, middleground, middleground2);
        }
    },
    CRYSTAL("crystal") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.CRYSTAL);
            World.addPanningSceneGraphs(scene, background);
        }
    };

    final String previewImage;

    World(String previewImage) {
        this.previewImage = previewImage;
    }

    public abstract void createWorld(Scene scene);

    public static World randomWorld() {
        World[] vals = World.values();
        return vals[MathUtility.randomInt(0, vals.length - 1)];
    }

    private static void addPanningSceneGraphs(Scene scene, PanningSceneGraph... graphs) {
        for (PanningSceneGraph panningSceneGraph : graphs) {
            scene.addUpdateable(panningSceneGraph);
            scene.getParentNode().addChild(panningSceneGraph.getDrawable());
        }
    }

    public String getPreviewImage() {
        return previewImage;
    }

}
