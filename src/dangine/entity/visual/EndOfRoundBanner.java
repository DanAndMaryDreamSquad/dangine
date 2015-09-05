package dangine.entity.visual;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class EndOfRoundBanner implements IsUpdateable, HasDrawable {

    final int HEIGHT = 200;
    final float MAX_TIME_ONE = 1500;
    final float MAX_TIME_TWO = 3000;
    final float MAX_TIME_END = 6000;

    SceneGraphNode top;
    SceneGraphNode topText;
    SceneGraphNode bottom;
    SceneGraphNode bottomText;
        
    SceneGraphNode base = new SceneGraphNode();

    float timer = 0;
    boolean addedTop = false;
    boolean addedBottom = false;

    public EndOfRoundBanner(String topMessage, String bottomMessage) {
        top = StartOfRoundBanner.makeBannerStrip(HEIGHT);
        top.setPosition(top.getPosition().x, -Utility.getResolution().y * 0.25f);

        bottom = StartOfRoundBanner.makeBannerStrip(HEIGHT);
        bottom.setPosition(bottom.getPosition().x, Utility.getResolution().y * 0.25f);

        topText = StartOfRoundBanner.makeTextNode(topMessage);
        topText.setPosition(topText.getPosition().x, -Utility.getResolution().y * 0.25f);
        
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
        if (timer > MAX_TIME_TWO && !addedBottom) {
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