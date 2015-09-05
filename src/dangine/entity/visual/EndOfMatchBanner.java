package dangine.entity.visual;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class EndOfMatchBanner implements IsUpdateable, HasDrawable {

    final int HEIGHT = 200;
    final float MAX_TIME_ONE = 1000;
    final float MAX_TIME_TWO = 2000;
    final float MAX_TIME_THREE = 3000;
    final float MAX_TIME_END = 7000;

    SceneGraphNode top;
    SceneGraphNode topText;
    SceneGraphNode middle;
    SceneGraphNode middleText;
    SceneGraphNode bottom;
    SceneGraphNode bottomText;

    SceneGraphNode base = new SceneGraphNode();

    float timer = 0;
    boolean addedTop = false;
    boolean addedMiddle = false;
    boolean addedBottom = false;

    public EndOfMatchBanner(String topMessage, String middleMessage, String bottomMessage) {
        top = StartOfRoundBanner.makeBannerStrip(HEIGHT);
        top.setPosition(top.getPosition().x, -Utility.getResolution().y * 0.25f);

        middle = StartOfRoundBanner.makeBannerStrip(HEIGHT);
        middle.setPosition(middle.getPosition().x, 0);

        bottom = StartOfRoundBanner.makeBannerStrip(HEIGHT);
        bottom.setPosition(bottom.getPosition().x, Utility.getResolution().y * 0.25f);

        topText = StartOfRoundBanner.makeTextNode(topMessage);
        topText.setPosition(topText.getPosition().x, -Utility.getResolution().y * 0.25f);

        middleText = StartOfRoundBanner.makeTextNode(middleMessage);
        middleText.setPosition(middleText.getPosition().x, 0);

        bottomText = StartOfRoundBanner.makeTextNode(bottomMessage);
        bottomText.setPosition(bottomText.getPosition().x, Utility.getResolution().y * 0.25f);

        base.setZValue(-100f);
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > MAX_TIME_ONE && !addedTop) {
            base.addChild(top);
            base.addChild(topText);
            addedTop = true;
        }
        if (timer > MAX_TIME_TWO && !addedMiddle) {
            base.addChild(middle);
            base.addChild(middleText);
            addedMiddle = true;
        }
        if (timer > MAX_TIME_THREE && !addedBottom) {
            base.addChild(bottom);
            base.addChild(bottomText);
            addedBottom = true;
        }
        if (timer > MAX_TIME_END) {
            Utility.getActiveScene().removeUpdateable(this);
            Utility.getActiveScene().getParentNode().removeChild(getDrawable());
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

}