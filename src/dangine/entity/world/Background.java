package dangine.entity.world;

import dangine.utility.MathUtility;

public enum Background {

    SUNSET("sunset3", 48.0f, 0.05f, 0, Tessellation.FLIP_TO_SMOOTH), //
    EXTRADIMENSIONAL("cloudy", 80.0f, -0.05f, 0.025f, Tessellation.NONE), //
    SKYLAND("sky2", 20.0f, 0.035f, -0.035f, Tessellation.NONE), //
    SPACE_ONE("space1", 38.0f, 0.005f, -0.005f, Tessellation.FLIP_TO_SMOOTH), //
    SPACE_TWO("space4", 19.0f, 0.005f, -0.005f, Tessellation.FLIP_TO_SMOOTH), //
    SNOW_FULL("snow1full", 19.0f, 0.005f, 0.0f, Tessellation.FLIP_TO_SMOOTH), //
    SNOW_SKY("snowsky1", 19.0f, 0.008f, 0.008f, Tessellation.FLIP_TO_SMOOTH), //
    FOREST("forest1", 20.0f, 0.035f, -0.0f, Tessellation.NONE);

    final String bgImage;
    final float scale;
    final float panSpeedX;
    final float panSpeedY;
    final Tessellation tessellation;

    Background(String bgImage, float scale, float panSpeedX, float panSpeedY, Tessellation tessellation) {
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

    public static Background randomBackground() {
        Background[] vals = Background.values();
        return vals[MathUtility.randomInt(0, vals.length - 1)];
    }
}
