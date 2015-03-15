package dangine.scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.CombatResolver;
import dangine.scenegraph.SceneGraphNode;

public class Scene implements IsUpdateable, IsDrawable {

    final CombatResolver combatResolver = new CombatResolver();
    final SceneGraphNode parentNode = new SceneGraphNode();
    List<IsUpdateable> updateables = new ArrayList<IsUpdateable>();
    List<IsUpdateable> toAdd = new LinkedList<IsUpdateable>();
    List<IsUpdateable> toRemove = new LinkedList<IsUpdateable>();

    @Override
    public void update() {
        for (IsUpdateable update : updateables) {
            update.update();
        }
        for (IsUpdateable update : toAdd) {
            updateables.add(update);
        }
        toAdd.clear();
        for (IsUpdateable update : toRemove) {
            updateables.remove(update);
        }
        toRemove.clear();
        combatResolver.resolveCombat();
    }

    @Override
    public void draw() {
        parentNode.draw();
    }

    public void addUpdateable(IsUpdateable updateable) {
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

    public SceneGraphNode getParentNode() {
        return parentNode;
    }

    public CombatResolver getCombatResolver() {
        return combatResolver;
    }

}
