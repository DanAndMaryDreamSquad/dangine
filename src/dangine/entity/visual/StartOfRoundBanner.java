package dangine.entity.visual;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineBox;
import dangine.graphics.DangineFont;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class StartOfRoundBanner implements IsUpdateable, HasDrawable {

    final int HEIGHT = 200;
    final float MAX_TIME = 2500;
    final float SPEED = 1.0f;

    SceneGraphNode base = new SceneGraphNode();

    float timer = 0;

    public StartOfRoundBanner() {
        base = StartOfRoundBanner.makeBannerStrip(HEIGHT);
        String message = "ROUND " + (Utility.getMatchParameters().getRoundKeeper().getCurrentRound() + 1);
        base.addChild(StartOfRoundBanner.makeTextNode(message));
        base.setZValue(-100f);

        // TODO inspirational quotes
        // quoteNode.addChild(quote);
        // base.addChild(quoteNode);
        // String quoteMessage = "quote!";
        // length = DangineFont.getLengthInPixels(quoteMessage);
        // height = DangineFont.getHeightInPixels(quoteMessage);
        // scale = 1.0f;
        // x = (Utility.getResolution().x / 2) - (length * scale * 0.5f);
        // y = (Utility.getResolution().y / 2) - (height * scale * 0.5f);
        // quoteNode.setPosition(x, y + 200);
        // quoteNode.setScale(scale, scale);
        // quote.setText(quoteMessage);
    }

    public static SceneGraphNode makeBannerStrip(int height) {
        DangineBox left = new DangineBox((int) Utility.getResolution().x / 2, height, new Color(Color.BLUE));
        left.setColor(new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), new Color(Color.BLACK), new Color(Color.BLACK));

        DangineBox right = new DangineBox((int) Utility.getResolution().x / 2, height, new Color(Color.YELLOW));
        right.setColor(new Color(Color.BLACK), new Color(Color.BLACK), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0));
        SceneGraphNode leftNode = new SceneGraphNode();
        SceneGraphNode rightNode = new SceneGraphNode();
        float y = (Utility.getResolution().y / 2) - (height / 2);
        leftNode.setPosition(0, y);
        rightNode.setPosition(Utility.getResolution().x / 2, y);
        leftNode.addChild(left);
        rightNode.addChild(right);
        SceneGraphNode node = new SceneGraphNode();
        node.addChild(leftNode);
        node.addChild(rightNode);
        return node;
    }
    
    public static SceneGraphNode makeTextNode(String message) {
        SceneGraphNode textNode = new SceneGraphNode();
        DangineStringPicture text = new DangineStringPicture("", new Color(Color.WHITE));
        int length = DangineFont.getLengthInPixels(message);
        int height = DangineFont.getHeightInPixels(message);
        float scale = 4.0f;
        float x = (Utility.getResolution().x / 2) - (length * scale * 0.5f);
        float y = (Utility.getResolution().y / 2) - (height * scale * 0.5f);
        textNode.setPosition(x, y);
        textNode.setScale(scale, scale);
        textNode.addChild(text);
        text.setText(message);
        SceneGraphNode node = new SceneGraphNode();
        node.addChild(textNode);
        return node;
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > MAX_TIME - 1000) {
            float percentLeft = (MAX_TIME - timer) / 1000.0f;
            base.setPosition((1.0f - percentLeft) * Utility.getResolution().x, 0);
        }
        if (timer > MAX_TIME) {
            Utility.getActiveScene().removeUpdateable(this);
            Utility.getActiveScene().getParentNode().removeChild(getDrawable());
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

}
