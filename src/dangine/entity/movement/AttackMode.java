package dangine.entity.movement;

public enum AttackMode {
    BUTTONS {
        @Override
        public String description() {
            return "Press B1 for heavy attack, B2 for light attack";
        }
    },
    HOLD_TO_CHARGE {
        @Override
        public String description() {
            return "Tap B1 for light attack, Hold B1 for heavy attack";
        }
    };

    public abstract String description();

    public AttackMode nextMode() {
        return AttackMode.values()[(this.ordinal() + 1) % AttackMode.values().length];
    }

}
