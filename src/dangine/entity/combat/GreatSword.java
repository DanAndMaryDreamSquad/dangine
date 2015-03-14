package dangine.entity.combat;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class GreatSword implements IsUpdateable, HasDrawable {

    int playerId = 0;
    final GreatSwordSceneGraph greatsword = new GreatSwordSceneGraph();
    final GreatSwordAnimator animator = new GreatSwordAnimator(greatsword);

    @Override
    public void update() {
        DangineSampleInput input = Utility.getPlayers().getPlayer(playerId).getCurrentInput();
        if (input.isButtonOne()) {
            animator.swinging();
        }
        if (input.isButtonTwo()) {
            animator.idle();
        }

        animator.update();
    }

    @Override
    public IsDrawable getDrawable() {
        return greatsword;
    }
}
