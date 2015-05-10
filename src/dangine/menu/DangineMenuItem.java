package dangine.menu;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineStringPicture;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.SceneGraphNode;

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

    public interface Action {
        void execute();
    }

    SelectionStyle selectionStyle = SelectionStyle.SIMPLE;
    SceneGraphNode base = new SceneGraphNode();
    // DangineText itemText;
    DangineStringPicture itemText;
    Action actionA = null;
    Action actionB = null;
    Action actionC = null;

    public DangineMenuItem(String text, Action onActivate) {
        itemText = new DangineStringPicture(text, new Color(Color.BLACK));
        base.addChild(itemText);
        this.actionA = onActivate;
    }

    public DangineMenuItem(String text, Action actionA, Action actionB) {
        itemText = new DangineStringPicture(text, new Color(Color.BLACK));
        base.addChild(itemText);
        this.actionA = actionA;
        this.actionB = actionB;
        selectionStyle = SelectionStyle.LEFT_RIGHT;
    }

    public DangineMenuItem withOnHover(Action actionC) {
        this.actionC = actionC;
        return this;
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

    public void onHover() {
        if (actionC != null) {
            actionC.execute();
        }
    }

    public DangineStringPicture getItemText() {
        return itemText;
    }

}
