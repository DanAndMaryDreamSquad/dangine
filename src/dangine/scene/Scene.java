package dangine.scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dangine.entity.Bouncer;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.Obstruction;
import dangine.entity.Vortex;
import dangine.entity.combat.CombatResolver;
import dangine.entity.gameplay.MatchOrchestrator;
import dangine.scenegraph.RenderData;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class Scene implements IsUpdateable, IsDrawable {

    final Camera camera = new Camera();
    final CombatResolver combatResolver = new CombatResolver();
    final MatchOrchestrator matchOrchestrator = new MatchOrchestrator();
    final SceneGraphNode parentNode = new SceneGraphNode();
    List<IsUpdateable> updateables = new ArrayList<IsUpdateable>();
    List<IsUpdateable> toAdd = new LinkedList<IsUpdateable>();
    List<IsUpdateable> toRemove = new LinkedList<IsUpdateable>();
    final List<Obstruction> obstructions = new LinkedList<Obstruction>();
    final List<Vortex> vortexes = new LinkedList<Vortex>();
    final List<Bouncer> bouncers = new LinkedList<Bouncer>();

    public Scene() {
        parentNode.addChild(camera.getDrawable());
        this.addUpdateable(camera);
        float xResolutionScale = Utility.getGameWindowResolution().x / Utility.getResolution().x;
        float yResolutionScale = Utility.getGameWindowResolution().y / Utility.getResolution().y;
        parentNode.setScale(xResolutionScale, yResolutionScale);
    }

    @Override
    public void update() {
        for (IsUpdateable update : updateables) {
            update.update();
        }
        combatResolver.resolveCombat();
        matchOrchestrator.resolveMatchEvents();
        for (IsUpdateable update : toAdd) {
            updateables.add(update);
        }
        toAdd.clear();
        for (IsUpdateable update : toRemove) {
            updateables.remove(update);
        }
        toRemove.clear();
    }

    @Override
    public void draw() {
        // parentNode.draw();
        parentNode.updateTransformsAndPropagate();
    }

    public void addUpdateable(IsUpdateable updateable) {
        if (updateable instanceof Obstruction) {
            obstructions.add((Obstruction) updateable);
        }
        if (updateable instanceof Vortex) {
            vortexes.add((Vortex) updateable);
        }
        if (updateable instanceof Bouncer) {
            bouncers.add((Bouncer) updateable);
        }
        toAdd.add(updateable);
    }

    public void removeUpdateable(IsUpdateable updateable) {
        toRemove.add(updateable);
    }

    public Hero getHero(int id) {
        for (IsUpdateable u : updateables) {
            if (u instanceof Hero) {
                Hero hero = (Hero) u;
                if (hero.getPlayerId() == id) {
                    return hero;
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
