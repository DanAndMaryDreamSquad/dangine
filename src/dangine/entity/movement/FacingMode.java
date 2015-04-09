package dangine.entity.movement;

public enum FacingMode {

    TWO_WAY {
        @Override
        public String description() {
            return "Heros can face left or right";
        }
    },
    FOUR_WAY {
        @Override
        public String description() {
            return "Heros can face up, down, left or right";
        }
    },
    EIGHT_WAY {
        @Override
        public String description() {
            return "Heros can face up, down, left, right, and diagonally";
        }
    };

    abstract public String description();

    public FacingMode nextMode() {
        return FacingMode.values()[(this.ordinal() + 1) % FacingMode.values().length];
    }

}
