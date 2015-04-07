package dangine.entity.world;

import dangine.utility.MathUtility;

public enum World {

    EXTRADIMENSIONAL("cloudy", 2.0f, 0.05f), //
    SKYLAND("sky2", 2.0f, 0.05f);

    final String bgImage;
    final float scale;
    final float panSpeed;

    World(String bgImage, float scale, float panSpeed) {
        this.bgImage = bgImage;
        this.scale = scale;
        this.panSpeed = panSpeed;
    }

    public String getBgImage() {
        return bgImage;
    }

    public float getScale() {
        return scale;
    }

    public float getPanSpeed() {
        return panSpeed;
    }

    public static World randomWorld() {
        World[] vals = World.values();
        return vals[MathUtility.randomInt(0, vals.length - 1)];
    }

}
