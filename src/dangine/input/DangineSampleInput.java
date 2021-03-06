package dangine.input;

public class DangineSampleInput {

    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;
    boolean buttonOne = false;
    boolean buttonTwo = false;
    boolean buttonThree = false;

    public void setInput(boolean up, boolean down, boolean left, boolean right, boolean buttonOne, boolean buttonTwo,
            boolean buttonThree) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.buttonOne = buttonOne;
        this.buttonTwo = buttonTwo;
        this.buttonThree = buttonThree;
    }

    public void copyFrom(DangineSampleInput input) {
        setInput(input.up, input.down, input.left, input.right, input.buttonOne, input.buttonTwo, input.buttonThree);
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isButtonOne() {
        return buttonOne;
    }

    public void setButtonOne(boolean buttonOne) {
        this.buttonOne = buttonOne;
    }

    public boolean isButtonTwo() {
        return buttonTwo;
    }

    public void setButtonTwo(boolean buttonTwo) {
        this.buttonTwo = buttonTwo;
    }

    public boolean isButtonThree() {
        return buttonThree;
    }

    public void setButtonThree(boolean buttonThree) {
        this.buttonThree = buttonThree;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" | up: ").append(up);
        buffer.append(" | down: ").append(down);
        buffer.append(" | left: ").append(left);
        buffer.append(" | right: ").append(right);
        buffer.append(" | button one: ").append(buttonOne);
        buffer.append(" | button two: ").append(buttonTwo);
        buffer.append(" | button three: ").append(buttonThree);
        return buffer.toString();
    }

}
