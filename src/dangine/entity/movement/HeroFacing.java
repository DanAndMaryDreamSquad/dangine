package dangine.entity.movement;

import dangine.bots.DangineBot;
import dangine.entity.Hero;
import dangine.input.DangineSampleInput;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.Utility;

public class HeroFacing {

    float lastDiagonalPressed = 0.0f;
    int diagonalLeniencyFrames = 0;

    public void updateFacing(Hero hero, DangineSampleInput input) {
        if (canTurn(hero)) {
            updateFacing(hero.getBlox(), input, hero.getAnimator());
        }
    }

    public void updateFacing(DangineBot hero, DangineSampleInput input) {
        if (canTurn(hero)) {
            updateFacing(hero.getBlox(), input, hero.getAnimator());
        }
    }

    private boolean canTurn(Hero hero) {
        MovementMode movementMode = Utility.getMatchParameters().getMovementMode();
        return hero.getActiveWeapon() != null && movementMode.canTurn(hero.getActiveWeapon());
    }

    private boolean canTurn(DangineBot hero) {
        MovementMode movementMode = Utility.getMatchParameters().getMovementMode();
        return hero.getActiveWeapon() != null && movementMode.canTurn(hero.getActiveWeapon());
    }

    private void updateFacing(BloxSceneGraph blox, DangineSampleInput input, BloxAnimator animator) {
        switch (Utility.getMatchParameters().getFacingMode()) {
        case TWO_WAY:
            updateFacingLegacy(animator, input);
            break;
        case FOUR_WAY:
            updateFacingFourDirection(blox, input, animator);
            break;
        case EIGHT_WAY:
            updateFacingEightDirections(blox, input, animator);
            break;
        }
    }

    private void updateFacingLegacy(BloxAnimator animator, DangineSampleInput input) {
        int facing = 0;
        if (input.isLeft()) {
            facing++;
        }
        if (input.isRight()) {
            facing--;
        }
        animator.updateFacing(facing);
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
            lastDiagonalPressed = 135.0f;
            diagonalLeniencyFrames = 2;
        }
        if (up && !down && !right && left) {
            angleDesired = 45.0f;
            lastDiagonalPressed = 45.0f;
            diagonalLeniencyFrames = 2;
        }
        if (!up && down && right && !left) {
            angleDesired = 225.0f;
            lastDiagonalPressed = 225.0f;
            diagonalLeniencyFrames = 2;
        }
        if (!up && down && !right && left) {
            angleDesired = 315.0f;
            lastDiagonalPressed = 315.0f;
            diagonalLeniencyFrames = 2;
        }
        angleDesired = checkDiagonalSave(angleDesired);
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

    private float checkDiagonalSave(float angleDesired) {
        if (diagonalLeniencyFrames == 0) {
            return angleDesired;
        }
        diagonalLeniencyFrames--;
        if (Math.abs(angleDesired - lastDiagonalPressed) <= 45) {
            return lastDiagonalPressed;
        }
        return angleDesired;
    }

}
