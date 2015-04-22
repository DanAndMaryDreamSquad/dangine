package dangine.entity.world;

import dangine.entity.Bouncer;
import dangine.entity.Obstruction;
import dangine.entity.Vortex;
import dangine.scene.Scene;
import dangine.utility.MathUtility;
import dangine.utility.Utility;

public enum ObstaclePack {
    EMPTY {
        @Override
        public void applyObstacles(Scene scene) {
        }
    },
    VORTEX {
        @Override
        public void applyObstacles(Scene scene) {

            float[][] locations = { { 0.5f, 0.5f } };
            for (float[] position : locations) {
                Vortex vortex = new Vortex();
                vortex.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(vortex);
                scene.getCameraNode().addChild(vortex.getDrawable());
            }

        }
    },
    BLOCKS {
        @Override
        public void applyObstacles(Scene scene) {

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
    TWO_VORTEX {
        @Override
        public void applyObstacles(Scene scene) {

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
    FOUR_CORNERS {
        @Override
        public void applyObstacles(Scene scene) {

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
    TRIPLE_BUMPER {
        @Override
        public void applyObstacles(Scene scene) {

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
    FOURTEX {
        @Override
        public void applyObstacles(Scene scene) {

            float[][] locations = { { 0.25f, 0.25f }, { 0.75f, 0.25f }, { 0.25f, 0.75f }, { 0.75f, 0.75f } };
            for (float[] position : locations) {
                Vortex vortex = new Vortex();
                vortex.setCenterPosition(Utility.getResolution().x * position[0], Utility.getResolution().y
                        * position[1]);
                scene.addUpdateable(vortex);
                scene.getCameraNode().addChild(vortex.getDrawable());
            }

        }
    };

    public abstract void applyObstacles(Scene scene);

    public static ObstaclePack randomObstacles() {
        ObstaclePack[] vals = ObstaclePack.values();
        return vals[MathUtility.randomInt(0, vals.length - 1)];
    }

}
