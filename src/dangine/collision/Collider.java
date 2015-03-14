package dangine.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineShape;
import dangine.utility.ScreenUtility;

public class Collider implements IsUpdateable, HasDrawable {

    SceneGraphNode node = new SceneGraphNode();
    Vector2f absolutePosition = new Vector2f(0, 0);

    public Collider() {
        node.addChild(new DangineShape(50, 50, Color.yellow));
        node.setPosition(-55, -20);
    }

    @Override
    public void update() {
        absolutePosition = ScreenUtility.getProjectedPosition(node, absolutePosition);
        Debugger.info(absolutePosition.toString());
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
