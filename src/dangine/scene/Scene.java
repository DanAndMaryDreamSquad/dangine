package dangine.scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Matrix4;

import dangine.bots.DangineBot;
import dangine.entity.Bouncer;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.Obstruction;
import dangine.entity.Vortex;
import dangine.entity.combat.CombatResolver;
import dangine.entity.gameplay.MatchOrchestrator;
import dangine.graphics.DangineOpenGL;
import dangine.scenegraph.RenderData;
import dangine.scenegraph.SceneGraphNode;

public class Scene implements IsUpdateable, IsDrawable {

    final Camera camera = new Camera();
    final CombatResolver combatResolver = new CombatResolver();
    final MatchOrchestrator matchOrchestrator = new MatchOrchestrator();
    final SceneGraphNode parentNode = new SceneGraphNode();
    List<IsUpdateable> updateables = new ArrayList<IsUpdateable>();
    List<IsUpdateable> toAdd = new LinkedList<IsUpdateable>();
    List<IsUpdateable> toRemove = new LinkedList<IsUpdateable>();
    final List<Hero> heroes = new LinkedList<Hero>();
    final List<Obstruction> obstructions = new LinkedList<Obstruction>();
    final List<Vortex> vortexes = new LinkedList<Vortex>();
    final List<Bouncer> bouncers = new LinkedList<Bouncer>();

    public Scene() {
        // parentNode.addChild(camera.getDrawable());
        this.addUpdateable(camera);
        // float xResolutionScale = Utility.getGameWindowResolution().x /
        // Utility.getResolution().x;
        // float yResolutionScale = Utility.getGameWindowResolution().y /
        // Utility.getResolution().y;
        // parentNode.setScale(xResolutionScale, yResolutionScale);
        parentNode.setMatrix(new Matrix4().setToOrtho(0, DangineOpenGL.WIDTH, DangineOpenGL.HEIGHT, 0, 1000, -1000));
    }

    @Override
    public void update() {
        for (IsUpdateable update : updateables) {
            update.update();
        }
        combatResolver.resolveCombat();
        matchOrchestrator.resolveMatchEvents();
        addAll();
        removeAll();
    }

    @Override
    public void draw() {
        // parentNode.draw();
        // parentNode.updateTransformsAndPropagate();
        parentNode.propagate();
    }

    public void addUpdateable(IsUpdateable updateable) {
        toAdd.add(updateable);
    }

    public void removeUpdateable(IsUpdateable updateable) {
        toRemove.add(updateable);
    }

    private void addAll() {
        for (IsUpdateable update : toAdd) {
            if (update instanceof Hero) {
                heroes.add((Hero) update);
            }
            if (update instanceof Obstruction) {
                obstructions.add((Obstruction) update);
            }
            if (update instanceof Vortex) {
                vortexes.add((Vortex) update);
            }
            if (update instanceof Bouncer) {
                bouncers.add((Bouncer) update);
            }
            updateables.add(update);
        }
        toAdd.clear();
    }

    private void removeAll() {
        for (IsUpdateable update : toRemove) {
            if (update instanceof Hero) {
                heroes.remove(update);
            }
            if (update instanceof Obstruction) {
                obstructions.remove((Obstruction) update);
            }
            if (update instanceof Vortex) {
                vortexes.remove((Vortex) update);
            }
            if (update instanceof Bouncer) {
                bouncers.remove((Bouncer) update);
            }
            updateables.remove(update);
        }
        toRemove.clear();
    }

    public Hero getHero(int id) {
        for (Hero hero : heroes) {
            if (hero.getPlayerId() == id) {
                return hero;
            }
        }
        return null;
    }

    public Iterator<Hero> getHeroes() {
        return heroes.iterator();
    }

    public DangineBot getBot(int id) {
        for (IsUpdateable u : updateables) {
            if (u instanceof DangineBot) {
                DangineBot bot = (DangineBot) u;
                if (bot.getBotId() == id) {
                    return bot;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getUpdateable(Class<? extends T> cls) {
        for (IsUpdateable u : updateables) {
            if (cls.isInstance(u)) {
                return (T) u;
            }
        }
        return null;
    }

    public SceneGraphNode getParentNode() {
        return parentNode;
    }

    public SceneGraphNode getCameraNode() {
        return camera.getCameraNode();
    }

    public CombatResolver getCombatResolver() {
        return combatResolver;
    }

    @Override
    public RenderData getRenderData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IsDrawable copy() {
        // TODO Auto-generated method stub
        return null;
    }

    public MatchOrchestrator getMatchOrchestrator() {
        return matchOrchestrator;
    }

    public List<Obstruction> getObstructions() {
        return obstructions;
    }

    public List<Vortex> getVortexes() {
        return vortexes;
    }

    public List<Bouncer> getBouncers() {
        return bouncers;
    }
}
