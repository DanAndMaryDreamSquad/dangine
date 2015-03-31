package dangine.bots;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.Hero;
import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class DangineBotLogic {

    final int WALL_BUFFER = 100;
    DangineSampleInput input = new DangineSampleInput();
    DangineSampleInput emptyInput = new DangineSampleInput();
    DangineSampleInput swingInput = new DangineSampleInput();

    public DangineSampleInput getWhatToDo(DangineBot bot) {
        Hero target = Utility.getActiveScene().getHero(0);
        if (target == null) {
            avoidWalls(bot);
            return input;
        }
        if (target.isImmunity()) {
            flee(bot, target.getPosition());
        } else {
            approach(bot, target.getPosition());
        }
        avoidWalls(bot);
        return input;
    }

    private void approach(DangineBot bot, Vector2f target) {
        if (target.x < bot.getPosition().x) {
            input.setLeft(true);
            input.setRight(false);
        } else if (target.x > bot.getPosition().x) {
            input.setLeft(false);
            input.setRight(true);
        }

        if (target.y < bot.getPosition().y) {
            input.setUp(true);
            input.setDown(false);
        } else if (target.y > bot.getPosition().y) {
            input.setUp(false);
            input.setDown(true);
        }
    }

    private void flee(DangineBot bot, Vector2f target) {
        this.approach(bot, target);
        boolean tmpV = input.isUp();
        boolean tmpH = input.isRight();
        input.setUp(input.isDown());
        input.setDown(tmpV);
        input.setRight(input.isLeft());
        input.setLeft(tmpH);
    }

    private void avoidWalls(DangineBot bot) {
        float width = Utility.getResolution().x;
        float height = Utility.getResolution().y;
        if (width - WALL_BUFFER < bot.getPosition().x) {
            input.setLeft(true);
            input.setRight(false);
        } else if (WALL_BUFFER > bot.getPosition().x) {
            input.setLeft(false);
            input.setRight(true);
        }

        if (height - WALL_BUFFER < bot.getPosition().y) {
            input.setUp(true);
            input.setDown(false);
        } else if (WALL_BUFFER > bot.getPosition().y) {
            input.setUp(false);
            input.setDown(true);
        }
    }

    public DangineSampleInput getWhatDoWithWeapon(BotGreatsword greatsword) {
        Hero target = Utility.getActiveScene().getHero(0);
        if (target == null) {
            return emptyInput;
        }
        swingInput.setButtonOne(true);
        return swingInput;
    }

}
