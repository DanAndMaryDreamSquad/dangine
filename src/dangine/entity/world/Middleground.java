package dangine.entity.world;

public enum Middleground {

    NONE("", 0, 0, 0, Tessellation.NONE), CLOUDS_LIGHT("squareclouds2", 8.0f, 0.05f, 0, Tessellation.NONE), CLOUDS_LIGHT_2(
            "squareclouds2", 8.0f, 0.08f, 0, Tessellation.FLIP_TO_SMOOTH);

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
