package dangine.entity.world;

public enum Middleground {

    NONE("", 0, 0, 0, Tessellation.NONE), //
    SQUARE_CLOUDS_LIGHT("squareclouds2", 18.0f, 0.05f, 0, Tessellation.NONE), //
    SQUARE_CLOUDS_LIGHT_2("squareclouds2", 18.0f, 0.08f, 0, Tessellation.FLIP_TO_SMOOTH), //
    SQUARE_CLOUDS_LIGHT_SMALL("squareclouds4", 10.0f, 0.05f, 0, Tessellation.NONE), //
    SQUARE_CLOUDS_LIGHT_SMALL_2("squareclouds4", 12.0f, 0.08f, 0, Tessellation.FLIP_TO_SMOOTH), //
    STARS("stars1", 19.0f, 0.005f, -0.005f, Tessellation.NONE), //
    SNOW_TREES("snowtrees1", 19.0f, 0.005f * 20, 0.0f, Tessellation.NONE), //
    CIRCLE_CLOUDS("circleclouds1", 19.0f, 0.05f, 0, Tessellation.FLIP_TO_SMOOTH), //
    CIRCLE_CLOUDS_2("circleclouds2", 10.0f, 0.05f, 0, Tessellation.FLIP_TO_SMOOTH);

    final String bgImage;
    final float scale;
    final float panSpeedX;
    final float panSpeedY;
    final Tessellation tessellation;

    Middleground(String bgImage, float scale, float panSpeedX, float panSpeedY, Tessellation tessellation) {
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

}
