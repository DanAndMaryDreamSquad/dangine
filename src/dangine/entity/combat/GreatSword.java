package dangine.entity.combat;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;

public class GreatSword implements IsUpdateable, HasDrawable {
    final GreatSwordSceneGraph greatsword = new GreatSwordSceneGraph();
    final GreatSwordAnimator animator = new GreatSwordAnimator(greatsword);

    @Override
    public void update() {
        animator.update();
    }

    @Override
    public IsDrawable getDrawable() {
        return greatsword;
    }
}
