package dangine.entity.world;

import dangine.utility.MathUtility;

public enum World {

    EXTRADIMENSIONAL("cloudy", 40.0f, -0.05f, 0.025f, Tessellation.NONE), //
    NETHER("nether", 1.0f, -0.04f, -0.04f, Tessellation.FLIP_TO_SMOOTH), //
    SPIRAL("spiral3", 1.0f, 0, 0, Tessellation.CENTER), //
    CRYSTAL("crystal", 1.0f, 0.015f, 0.015f, Tessellation.FLIP_TO_SMOOTH), //
    SKYLAND("sky2", 20.0f, 0.035f, -0.035f, Tessellation.NONE);

    final String bgImage;
    final float scale;
    final float panSpeedX;
    final float panSpeedY;
    final Tessellation tessellation;

    World(String bgImage, float scale, float panSpeedX, float panSpeedY, Tessellation tessellation) {
        this.bgImage = bgImage;
        this.scale = scale;
        this.panSpeedX = panSpeedX;
        this.panSpeedY = panSpeedY;
        this.tessellation = tessellation;
    }

    public String getBgImage() {
        return bgImage;
    }

    public float getScale() {
        return scale;
    }

    public float getPanSpeedX() {
        return panSpeedX;
    }

    public float getPanSpeedY() {
        return panSpeedY;
    }

    public Tessellation getTessellation() {
        return tessellation;
    }

    public static World randomWorld() {
        World[] vals = World.values();
        return vals[MathUtility.randomInt(0, vals.length - 1)];
    }
}
