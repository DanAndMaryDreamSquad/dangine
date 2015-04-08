package dangine.entity.movement;

import dangine.bots.DangineBot;
import dangine.entity.Hero;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Utility;

public class HeroFacing {

    private boolean canTurn(Hero hero) {
        MovementMode movementMode = Utility.getMatchParameters().getMovementMode();
        return hero.getActiveWeapon() != null && movementMode.canTurn(hero.getActiveWeapon());
    }

    private boolean canTurn(DangineBot hero) {
        MovementMode movementMode = Utility.getMatchParameters().getMovementMode();
        return hero.getActiveWeapon() != null && movementMode.canTurn(hero.getActiveWeapon());
    }

    public void updateFacingLegacy(Hero hero, DangineSampleInput input) {
        if (canTurn(hero)) {
            int facing = 0;
            if (input.isLeft()) {
                facing++;
            }
            if (input.isRight()) {
                facing--;
            }
            hero.getAnimator().updateFacing(facing);
        }
    }

    public void updateFacing(DangineBot hero, DangineSampleInput input) {
        if (canTurn(hero)) {
            updateFacingFourDirection(hero.getBlox(), input, hero.getAnimator());
        }
    }

    public void updateFacing(Hero hero, DangineSampleInput input) {
        if (canTurn(hero)) {
            updateFacingFourDirection(hero.getBlox(), input, hero.getAnimator());
        }
    }

    private void updateFacingFourDirection(BloxSceneGraph blox, DangineSampleInput input, BloxAnimator animator) {
        boolean up = input.isUp();
        boolean right = input.isRight();
        boolean down = input.isDown();
        boolean left = input.isLeft();
        if (!up && !down && !right && !left) {
            return;
        }
        float angleDesired = 0;
        float currentAngle = blox.getBase().getAngle();
        currentAngle = accountForFlippedImage(currentAngle, animator);
        if (up) {
            angleDesired = 90.0f;
        }
        if (down) {
            angleDesired = 270.0f;
        }
        if (right) {
            angleDesired = 180.0f;
        }
        if (left) {
            angleDesired = 0.0f;
        }
        if (heroFlipped180(angleDesired, currentAngle)) {
            animator.flipFacingDirection();
        }
        float angleWithFlipAccounted = accountForFlippedImage(angleDesired, animator);
        blox.getBase().setAngle(angleWithFlipAccounted);
    }

    private float accountForFlippedImage(float angleToProcess, BloxAnimator animator) {
        if (animator.getFacingDirection() == -1) {
            angleToProcess -= 180.0f;
            if (angleToProcess < 0) {
                angleToProcess += 360.0f;
            }
        }
        return angleToProcess;
    }

    private boolean heroFlipped180(float angleDesired, float currentAngle) {
        return Math.abs(angleDesired - currentAngle) == 180;
    }

    @SuppressWarnings("unused")
    private void updateFacingEightDirections(BloxSceneGraph blox, DangineSampleInput input, BloxAnimator animator) {
        boolean up = input.isUp();
        boolean right = input.isRight();
        boolean down = input.isDown();
        boolean left = input.isLeft();
        float angleDesired = 0;
        float currentAngle = blox.getBase().getAngle();
        currentAngle = accountForFlippedImage(currentAngle, animator);
        if (!up && !down && !right && !left) {
            return;
        }
        if (up && !down && !right && !left) {
            angleDesired = 90.0f;
        }
        if (!up && down && !right && !left) {
            angleDesired = 270.0f;
        }
        if (!up && !down && right && !left) {
            angleDesired = 180.0f;
        }
        if (!up && !down && !right && left) {
            angleDesired = 0.0f;
        }
        if (up && !down && right && !left) {
            angleDesired = 135.0f;
        }
        if (up && !down && !right && left) {
            angleDesired = 45.0f;
        }
        if (!up && down && right && !left) {
            angleDesired = 225.0f;
        }
        if (!up && down && !right && left) {
            angleDesired = 315.0f;
        }
        if (heroFlipped180(angleDesired, currentAngle)) {
            animator.flipFacingDirection();
        }
        float angleWithFlipAccounted = accountForFlippedImage(angleDesired, animator);
        blox.getBase().setAngle(angleWithFlipAccounted);
    }

}
