package dangine.entity.visual;

import org.lwjgl.util.Color;

import dangine.utility.MathUtility;
import dangine.utility.Utility;

public enum DefeatType {

    SPLIT {
        @Override
        public void applyEffect(float x, float y, int playerId) {
            DefeatedBloxSplitVisual split = new DefeatedBloxSplitVisual(x, y, -30, playerId);
            Utility.getActiveScene().getCameraNode().addChild(split.getDrawable());
            Utility.getActiveScene().addUpdateable(split);
        }
    },
    KNOCK {
        @Override
        public void applyEffect(float x, float y, int playerId) {
            Color color = Utility.getMatchParameters().getPlayerColor(playerId);
            DefeatedBloxKnockVisual split = new DefeatedBloxKnockVisual(x, y, -30, color);
            Utility.getActiveScene().getCameraNode().addChild(split.getDrawable());
            Utility.getActiveScene().addUpdateable(split);
        }
    },
    SPIN {
        @Override
        public void applyEffect(float x, float y, int playerId) {
            Color c = Utility.getMatchParameters().getPlayerColor(playerId);
            DefeatedBloxSpinVisual spin = new DefeatedBloxSpinVisual(x, y, -30, c);
            Utility.getActiveScene().getCameraNode().addChild(spin.getDrawable());
            Utility.getActiveScene().addUpdateable(spin);
        }
    };

    public abstract void applyEffect(float x, float y, int playerId);

    public static DefeatType randomEffect() {
        DefeatType[] vals = DefeatType.values();
        return vals[MathUtility.randomInt(0, vals.length - 1)];
    }

    public static DefeatType randomSwordEffect() {
        DefeatType[] vals = { SPLIT, KNOCK };
        return vals[MathUtility.randomInt(0, vals.length - 1)];

    }

}
