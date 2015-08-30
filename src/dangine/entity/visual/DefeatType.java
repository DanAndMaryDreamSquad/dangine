package dangine.entity.visual;

import org.lwjgl.util.Color;

import dangine.bots.DangineBot;
import dangine.entity.Hero;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.MathUtility;
import dangine.utility.Utility;

public enum DefeatType {

    SPLIT {
        @Override
        public void applyEffect(float x, float y, int playerId) {
            SceneGraphNode node = null;
            if (playerId >= 0) {
                Hero hero = Utility.getActiveScene().getHero(playerId);
                if (hero != null) {
                    node = (SceneGraphNode) hero.getDrawable();
                }
            } else {
                DangineBot bot = Utility.getActiveScene().getBot(playerId);
                if (bot != null) {
                    node = (SceneGraphNode) bot.getDrawable();
                }
            }
            DefeatedBloxSplitVisual split = new DefeatedBloxSplitVisual(x, y, node);
            split.withDelay();
            Utility.getActiveScene().addUpdateable(split);

            BloxFreezeVisual bloxFreezeVisual = new BloxFreezeVisual(x, y, node);
            Utility.getActiveScene().getCameraNode().addChild(bloxFreezeVisual.getDrawable());
            Utility.getActiveScene().addUpdateable(bloxFreezeVisual);
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
        DefeatType[] vals = { SPLIT, SPLIT };
        return vals[MathUtility.randomInt(0, vals.length - 1)];

    }

}
