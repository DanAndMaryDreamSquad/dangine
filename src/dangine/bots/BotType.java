package dangine.bots;

public enum BotType {

    NORMAL {
        @Override
        public boolean ignoresKnockback() {
            return false;
        }

        @Override
        public boolean ignoresObstacles() {
            return false;
        }
    },
    NO_KNOCKBACK {
        @Override
        public boolean ignoresKnockback() {
            return true;
        }

        @Override
        public boolean ignoresObstacles() {
            return false;
        }
    },
    IGNORE_OBSTACLES {
        @Override
        public boolean ignoresKnockback() {
            return false;
        }

        @Override
        public boolean ignoresObstacles() {
            return true;
        }
    },
    BOSS_MODE {
        @Override
        public boolean ignoresKnockback() {
            return true;
        }

        @Override
        public boolean ignoresObstacles() {
            return true;
        }
    };

    public abstract boolean ignoresKnockback();

    public abstract boolean ignoresObstacles();

    public BotType nextType() {
        return BotType.values()[(this.ordinal() + 1) % BotType.values().length];
    }

}
