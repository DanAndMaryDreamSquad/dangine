package dangine.entity.movement;

import dangine.entity.combat.IsGreatsword;

public enum MovementMode {

    FREE {
        @Override
        public String description() {
            return "Hero can turn and move freely";
        }

        @Override
        public boolean canTurn(IsGreatsword greatSword) {
            return true;
        }

        @Override
        public boolean canMove(IsGreatsword greatSword) {
            return true;
        }
    },
    SWING_LOCK {
        @Override
        public String description() {
            return "Hero can turn and move freely while charging, but not while swinging";
        }

        @Override
        public boolean canTurn(IsGreatsword greatSword) {
            if (greatSword == null) {
                return true;
            }
            return !greatSword.isSwinging();
        }

        @Override
        public boolean canMove(IsGreatsword greatSword) {
            if (greatSword == null) {
                return true;
            }
            return !greatSword.isSwinging();
        }
    },
    ALL_LOCK {
        @Override
        public String description() {
            return "Hero cannot turn or move while charging or swinging";
        }

        @Override
        public boolean canTurn(IsGreatsword greatSword) {
            if (greatSword == null) {
                return true;
            }
            return !greatSword.isSwinging() && !greatSword.isCharging();
        }

        @Override
        public boolean canMove(IsGreatsword greatSword) {
            if (greatSword == null) {
                return true;
            }
            return !greatSword.isSwinging() && !greatSword.isCharging();
        }
    },
    MOVE_FREE_TURN_SWING_LOCK {
        @Override
        public String description() {
            return "Hero can turn freely while charging, but not while swinging. Can always move freely";
        }

        @Override
        public boolean canTurn(IsGreatsword greatSword) {
            if (greatSword == null) {
                return true;
            }
            return !greatSword.isSwinging();
        }

        @Override
        public boolean canMove(IsGreatsword greatSword) {
            return true;
        }
    },
    MOVE_FREE_TURN_ALL_LOCK {
        @Override
        public String description() {
            return "Hero cannot turn or move while charging or swinging. Can always move freely";
        }

        @Override
        public boolean canTurn(IsGreatsword greatSword) {
            if (greatSword == null) {
                return true;
            }
            return !greatSword.isSwinging() && !greatSword.isCharging();
        }

        @Override
        public boolean canMove(IsGreatsword greatSword) {
            return true;
        }
    };

    public abstract String description();

    public abstract boolean canTurn(IsGreatsword greatSword);

    public abstract boolean canMove(IsGreatsword greatSword);

    public MovementMode nextMode() {
        return MovementMode.values()[(this.ordinal() + 1) % MovementMode.values().length];
    }
}
