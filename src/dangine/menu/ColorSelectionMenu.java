package dangine.menu;

import org.newdawn.slick.Color;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.BloxSceneGraph;

public class ColorSelectionMenu implements IsUpdateable, HasDrawable {

    final int playerId;
    final SceneGraphNode node = new SceneGraphNode();
    final DangineMenu menu = new DangineMenu();
    final DangineSelector selector;
    BloxSceneGraph blox = new BloxSceneGraph();
    BloxAnimator animator = new BloxAnimator(blox);
    Color color = Color.red;
    boolean isDone = false;

    public ColorSelectionMenu(int playerId) {
        this.playerId = playerId;
        selector = new DangineSelector(playerId);
        animator.idle();
        node.addChild(menu.getDrawable());
        node.addChild(blox.getDrawable());
        node.setPosition((200 * (1 + playerId)) + 100, 450);
        blox.getBase().setPosition(-50, 0);
        menu.addItem(new DangineMenuItem("Ready", getReadyAction()));
        menu.addItem(new DangineMenuItem("Next Color", getNextColorAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());
        reColor();
    }

    @Override
    public void update() {
        animator.update();
        if (!isDone()) {
            selector.update();
            selector.scan(menu.getItems());
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    private void reColor() {
        BloxColorer.color(blox, color);
    }

    private Action getReadyAction() {
        return new Action() {

            @Override
            public void execute() {
                Debugger.info("Done");
                isDone = true;
                node.removeChild(menu.getDrawable());
            }
        };
    }

    private Action getNextColorAction() {
        return new Action() {

            @Override
            public void execute() {
                int i = BloxColorer.indexOf(color);
                i++;
                if (i >= BloxColorer.COLORS.length) {
                    i = 0;
                }
                color = BloxColorer.COLORS[i];
                reColor();
            }
        };
    }

    public boolean isDone() {
        return isDone;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Color getColor() {
        return color;
    }

}
