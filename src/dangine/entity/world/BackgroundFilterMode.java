package dangine.entity.world;

import dangine.graphics.DangineTexturedQuad;

public enum BackgroundFilterMode {

    BLOCKY(0) {
        @Override
        public DangineTexturedQuad setFilter(DangineTexturedQuad backgroundQuad) {
            return backgroundQuad;
        }
    },
    SMOOTH(1) {
        @Override
        public DangineTexturedQuad setFilter(DangineTexturedQuad backgroundQuad) {
            return backgroundQuad.withFilterModeLinear();
        }
    };

    int filterMode;

    BackgroundFilterMode(int filterMode) {
        this.filterMode = filterMode;
    }

    abstract public DangineTexturedQuad setFilter(DangineTexturedQuad backgroundQuad);

    public BackgroundFilterMode nextMode() {
        return BackgroundFilterMode.values()[(this.ordinal() + 1) % BackgroundFilterMode.values().length];
    }

    public static BackgroundFilterMode fromInt(int mode) {
        for (BackgroundFilterMode bgfilterMode : BackgroundFilterMode.values()) {
            if (bgfilterMode.getFilterMode() == mode) {
                return bgfilterMode;
            }
        }
        return null;
    }

    public int getFilterMode() {
        return filterMode;
    }
}
