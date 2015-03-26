package dangine.menu;

import org.newdawn.slick.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;

public class DangineMenuItem implements HasDrawable {

    enum SelectionStyle {
        SIMPLE {
            @Override
            boolean shouldDoActionA(DangineSampleInput currentInput, DangineSampleInput previousInput) {
                return currentInput.isButtonOne() && !previousInput.isButtonOne();
            }

            @Override
            boolean shouldDoActionB(DangineSampleInput currentInput, DangineSampleInput previousInput) {
                return false;
            }
        },
        LEFT_RIGHT {
            @Override
            boolean shouldDoActionA(DangineSampleInput currentInput, DangineSampleInput previousInput) {
                return currentInput.isRight() && !previousInput.isRight();
            }

            @Override
            boolean shouldDoActionB(DangineSampleInput currentInput, DangineSampleInput previousInput) {
                return currentInput.isLeft() && !previousInput.isLeft();
            }
        };

        abstract boolean shouldDoActionA(DangineSampleInput currentInput, DangineSampleInput previousInput);

        abstract boolean shouldDoActionB(DangineSampleInput currentInput, DangineSampleInput previousInput);
    }

    interface Action {
        void execute();
    }

    SelectionStyle selectionStyle = SelectionStyle.SIMPLE;
    SceneGraphNode base = new SceneGraphNode();
    Action actionA = null;
    Action actionB = null;

    public DangineMenuItem(String text, Action onActivate) {
        base.addChild(new DangineText(text, Color.black));
        this.actionA = onActivate;
    }

    public DangineMenuItem(String text, Action actionA, Action actionB) {
        base.addChild(new DangineText(text, Color.black));
        this.actionA = actionA;
        this.actionB = actionB;
        selectionStyle = SelectionStyle.LEFT_RIGHT;
    }

    public SceneGraphNode getBase() {
        return base;
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public void activate(DangineSampleInput currentInput, DangineSampleInput previousInput) {
        if (selectionStyle.shouldDoActionA(currentInput, previousInput)) {
            actionA.execute();
        }
        if (selectionStyle.shouldDoActionB(currentInput, previousInput)) {
            actionB.execute();
        }
    }

}
