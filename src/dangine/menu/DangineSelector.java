package dangine.menu;

import java.util.List;

import org.lwjgl.util.Color;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineBox;
import dangine.graphics.DangineFont;
import dangine.graphics.DanginePicture;
import dangine.input.DangineSampleInput;
import dangine.menu.DangineMenuItem.Action;
import dangine.menu.DangineMenuItem.SelectionStyle;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Oscillator;
import dangine.utility.Utility;

public class DangineSelector implements IsUpdateable, HasDrawable {

    int playerId = 0;
    final float ROTATION_SPEED = 0.16f;
    float angle = 0.0f;
    SceneGraphNode node = new SceneGraphNode();
    // DangineShape shape = new DangineShape();
    DangineBox shape = new DangineBox();
    DanginePicture pointer = new DanginePicture("arrow");
    SceneGraphNode pointerNode = new SceneGraphNode();
    DanginePicture pointer2 = new DanginePicture("arrow");
    SceneGraphNode pointerNode2 = new SceneGraphNode();
    DangineMenuItem previousItem = null;
    DangineMenuItem currentItem = null;
    boolean isBlockMode = false;
    Action onEscape = null;
    float arrowScale = 4.0f;
    float arrowX;
    float arrowY;
    float wordLength;
    float timer = 0;

    public DangineSelector() {
        node.setCenterOfRotation(10, 10);
        makeArrow();

        switchToBlock();
    }

    public DangineSelector(int playerId) {
        this.playerId = playerId;
        node.setCenterOfRotation(10, 10);
        makeArrow();

        switchToBlock();
    }

    private void makeArrow() {
        arrowScale = 4.0f;
        arrowX = -pointer.getWidth() * arrowScale;
        arrowY = -pointer.getHeight();
        pointerNode.addChild(pointer);
        pointerNode.setScale(arrowScale, arrowScale);
        pointerNode.setPosition(arrowX, arrowY);
        pointer.getQuad().setTextureColor(new Color(Color.RED));

        pointerNode2.addChild(pointer2);
        pointerNode2.setScale(arrowScale, arrowScale);
        pointerNode2.setPosition(arrowX, arrowY);
        pointerNode2.setHorzitontalFlip(-1);
        pointer2.getQuad().setTextureColor(new Color(Color.RED));
    }

    private void switchToBlock() {
        if (!isBlockMode) {
            node.addChild(shape);
            node.removeChild(pointerNode);
            node.removeChild(pointerNode2);
            isBlockMode = true;
        }
    }

    private void switchToArrows() {
        if (isBlockMode) {
            node.addChild(pointerNode);
            node.addChild(pointerNode2);
            node.removeChild(shape);
            isBlockMode = false;
            node.setAngle(0);
        }
        wordLength = DangineFont.getLengthInPixels(currentItem.getItemText().getText());
        pointerNode2.setPosition(arrowX + (-arrowScale * pointer2.getWidth()) + wordLength, arrowY);
    }

    private void switchToNewType() {
        if (currentItem.getSelectionStyle() == SelectionStyle.SIMPLE) {
            switchToBlock();
        }
        if (currentItem.getSelectionStyle() == SelectionStyle.LEFT_RIGHT) {
            switchToArrows();
        }
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (isBlockMode) {
            angle = timer * ROTATION_SPEED;
            node.setAngle(angle);
        } else {
            float x = Oscillator.calculate(-5, 5, 2000, timer);
            pointerNode.setPosition(arrowX + x, arrowY);
            pointerNode2.setPosition(arrowX + (-arrowScale * pointer2.getWidth()) + wordLength + x, arrowY);
        }

        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        DangineSampleInput prevInput = Utility.getPlayers().getPlayer(playerId).getPreviousInput();
        if (currentItem != null) {
            currentItem.activate(input, prevInput);
        }
        if (onEscape != null && input.isButtonThree() && !prevInput.isButtonThree()) {
            SoundPlayer.play(SoundEffect.MENU_BACK);
            onEscape.execute();
        }
        if (currentItem != previousItem) {
            currentItem.onHover();
        }
    }

    public void setOnEscape(Action onEscape) {
        this.onEscape = onEscape;
    }

    public void scan(List<DangineMenuItem> items) {
        previousItem = currentItem;
        if (items.isEmpty()) {
            return;
        }
        DangineMenuItem newSelection = null;
        if (currentItem == null) {
            newSelection = items.get(0);
        }
        int i = items.indexOf(currentItem);
        if (i == -1) {
            i = 0;
        }
        DangineSampleInput currentInput = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        DangineSampleInput previousInput = Utility.getPlayers().getPlayer(playerId).getPreviousInput();
        if (currentInput.isUp() && !previousInput.isUp()) {
            i--;
        }
        if (currentInput.isDown() && !previousInput.isDown()) {
            i++;
        }
        if (i < 0) {
            i = items.size() - 1;
        }
        if (i >= items.size()) {
            i = 0;
        }
        newSelection = items.get(i);
        if (newSelection != currentItem) {
            if (currentItem != null) {
                currentItem.getBase().removeChild(getDrawable());
            }
            newSelection.getBase().addChild(getDrawable());
            currentItem = newSelection;
            switchToNewType();
            SoundPlayer.play(SoundEffect.MENU_TICK);
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    public DangineMenuItem getCurrentItem() {
        return currentItem;
    }
}
