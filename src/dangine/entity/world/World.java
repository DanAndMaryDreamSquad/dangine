package dangine.entity.world;

import org.newdawn.slick.Color;

import dangine.entity.Bouncer;
import dangine.entity.Obstruction;
import dangine.entity.Vortex;
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

        @Override
        public void createObstacles(Scene scene) {
            float[][] locations = { { 0.5f, 0.15f }, { 0.25f, 0.75f }, { 0.75f, 0.75f } };
            for (float[] position : locations) {
                Bouncer bouncer = new Bouncer();
                bouncer.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(bouncer);
                scene.getCameraNode().addChild(bouncer.getDrawable());
            }
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

        @Override
        public void createObstacles(Scene scene) {
            float[][] locations = { { 0.15f, 0.15f }, { 0.85f, 0.85f }, { 0.85f, 0.15f }, { 0.15f, 0.85f } };
            for (float[] position : locations) {
                Bouncer bouncer = new Bouncer();
                bouncer.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(bouncer);
                scene.getCameraNode().addChild(bouncer.getDrawable());
            }

            float[][] locations2 = { { 0.5f, 0.5f } };
            for (float[] position : locations2) {
                Vortex vortex = new Vortex();
                vortex.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(vortex);
                scene.getCameraNode().addChild(vortex.getDrawable());
            }
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

        @Override
        public void createObstacles(Scene scene) {
            // TODO Auto-generated method stub

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

        @Override
        public void createObstacles(Scene scene) {
            float[][] locations = { { 0.25f, 0.25f }, { 0.75f, 0.25f }, { 0.25f, 0.75f }, { 0.75f, 0.75f } };
            for (float[] position : locations) {
                Obstruction obstruction = new Obstruction();
                obstruction.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(obstruction);
                scene.getCameraNode().addChild(obstruction.getDrawable());
            }
        }
    },
    SPACE_ONE("space1") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.SPACE_ONE);
            PanningSceneGraph middleground = new PanningSceneGraph(Middleground.STARS);
            World.addPanningSceneGraphs(scene, background, middleground);
            Utility.getMatchParameters().setTextColor(Color.black);
        }

        @Override
        public void createObstacles(Scene scene) {
            float[][] locations = { { 0.25f, 0.25f }, { 0.75f, 0.75f } };
            for (float[] position : locations) {
                Vortex vortex = new Vortex();
                vortex.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(vortex);
                scene.getCameraNode().addChild(vortex.getDrawable());
            }
        }
    },
    SPACE_TWO("space4") {
        @Override
        public void createWorld(Scene scene) {
            PanningSceneGraph background = new PanningSceneGraph(Background.SPACE_TWO);
            World.addPanningSceneGraphs(scene, background);
            Utility.getMatchParameters().setTextColor(Color.black);
        }

        @Override
        public void createObstacles(Scene scene) {
            float[][] locations = { { 0.25f, 0.25f }, { 0.75f, 0.25f }, { 0.25f, 0.75f }, { 0.75f, 0.75f } };
            for (float[] position : locations) {
                Vortex vortex = new Vortex();
                vortex.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(vortex);
                scene.getCameraNode().addChild(vortex.getDrawable());
            }

            // TODO Auto-generated method stub

        }
    };

    final String previewImage;

    World(String previewImage) {
        this.previewImage = previewImage;
    }

    public abstract void createWorld(Scene scene);

    public abstract void createObstacles(Scene scene);

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
