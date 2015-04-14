package dangine.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter;
import dangine.entity.world.Background;
import dangine.entity.world.World;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class WorldSelectionMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    SceneGraphNode stockTextNode = new SceneGraphNode();
    DangineText stockText = new DangineText();
    SceneGraphNode movementModeTextNode = new SceneGraphNode();
    DangineText movementModeText = new DangineText();
    SceneGraphNode worldPreviewNode = new SceneGraphNode();
    Map<World, DangineImage> worldToPreviewImage = new HashMap<World, DangineImage>();
    DangineText randomPreview = new DangineText("? ? ?\n? ? ?\n? ? ?", Color.black);
    final float PREVIEW_SCALE = 0.25f;

    public WorldSelectionMenu() {
        DangineMenuItem randomItem = new DangineMenuItem("Random", getRandomWorldAction());
        randomItem.withOnHover(getOnHoverRandomAction());
        menu.addItem(randomItem);
        for (World world : World.values()) {
            DangineMenuItem item = new DangineMenuItem(world.name(), getWorldAction(world));
            item.withOnHover(getOnHoverAction(world));
            menu.addItem(item);
            worldToPreviewImage.put(world, new DangineImage(world.getPreviewImage()));
        }
        menu.addItem(new DangineMenuItem("Back", getExitMenuAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());

        worldPreviewNode.setPosition(Utility.getResolution().x / 4, worldPreviewNode.getPosition().y);
        menu.getBase().addChild(worldPreviewNode);

        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.75f));
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        clearPreviewImage();
    }

    @Override
    public IsDrawable getDrawable() {
        return menu.getDrawable();
    }

    @Override
    public void update() {
        selector.update();
        selector.scan(menu.getItems());
    }

    private void clearPreviewImage() {
        for (Entry<World, DangineImage> entry : worldToPreviewImage.entrySet()) {
            DangineImage image = worldToPreviewImage.get(entry.getKey());
            worldPreviewNode.removeChild(image);
        }
        worldPreviewNode.removeChild(randomPreview);
    }

    private Action getRandomWorldAction() {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setCurrentWorld(Background.randomBackground());
                MatchStarter matchStarter = new MatchStarter();
                Utility.getActiveScene().addUpdateable(matchStarter);
            }
        };
    }

    private Action getWorldAction(final World world) {
        return new Action() {

            @Override
            public void execute() {
                Utility.getMatchParameters().setCurrentWorld(world);
                MatchStarter matchStarter = new MatchStarter();
                Utility.getActiveScene().addUpdateable(matchStarter);
            }
        };
    }

    private Action getOnHoverRandomAction() {
        return new Action() {

            @Override
            public void execute() {
                clearPreviewImage();
                worldPreviewNode.addChild(randomPreview);
                worldPreviewNode.setScale(1.0f, 1.0f);
            }
        };
    }

    private Action getOnHoverAction(final World world) {
        return new Action() {

            @Override
            public void execute() {
                clearPreviewImage();
                worldPreviewNode.addChild(worldToPreviewImage.get(world));
                float scale = 400.0f / worldToPreviewImage.get(world).getHeight();
                worldPreviewNode.setScale(scale * PREVIEW_SCALE, scale * PREVIEW_SCALE);
            }
        };
    }

    private Action getExitMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(WorldSelectionMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(WorldSelectionMenu.this.getDrawable());
            }

        };
    }
}