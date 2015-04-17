package dangine.entity.world;

import org.newdawn.slick.Color;

import dangine.scene.Scene;
import dangine.utility.MathUtility;
import dangine.utility.Utility;

public enum World {

    DIMENSION("cloudy") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.EXTRADIMENSIONAL);
            World.addPanningSceneGraphs(scene, background);
            Utility.getMatchParameters().setTextColor(Color.black);
        }
    },
    SUNSET("sunset2") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.SUNSET);
            PanningSceneGraph middleground = new PanningSceneGraph(Middleground.SQUARE_CLOUDS_LIGHT);
            PanningSceneGraph middleground2 = new PanningSceneGraph(Middleground.SQUARE_CLOUDS_LIGHT_2);
            World.addPanningSceneGraphs(scene, background, middleground, middleground2);
            Utility.getMatchParameters().setTextColor(Color.black);
        }
    },
    FOREST("forest1") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.FOREST);
            PanningSceneGraph middleground = new PanningSceneGraph(Middleground.SQUARE_CLOUDS_LIGHT_SMALL);
            World.addPanningSceneGraphs(scene, background, middleground);
            Utility.getMatchParameters().setTextColor(Color.black);
        }
    },
    SNOW("snow1full") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.SNOW_SKY);
            PanningSceneGraph middleground = new PanningSceneGraph(Middleground.SNOW_TREES);
            World.addPanningSceneGraphs(scene, background, middleground);
            Utility.getMatchParameters().setTextColor(Color.black);
        }
    },
    SPACE_ONE("space1") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.SPACE_ONE);
            PanningSceneGraph middleground = new PanningSceneGraph(Middleground.STARS);
            World.addPanningSceneGraphs(scene, background, middleground);
            Utility.getMatchParameters().setTextColor(Color.white);
        }
    },
    SPACE_TWO("space4") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.SPACE_TWO);
            World.addPanningSceneGraphs(scene, background);
            Utility.getMatchParameters().setTextColor(Color.white);
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
