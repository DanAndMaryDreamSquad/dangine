package dangine.scenegraph.drawable;

import java.util.HashMap;
import java.util.Map;

import dangine.debugger.Debugger;
import dangine.player.DanginePlayer;
import dangine.utility.Utility;

public class BloxPool {

    Map<Integer, BloxSceneGraph> idToBlox = new HashMap<Integer, BloxSceneGraph>();

    public void create() {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            idToBlox.put(player.getPlayerId(), new BloxSceneGraph());
        }
        for (int i = 1; i < Utility.getMatchParameters().getNumberOfBots() + 1; i++) {
            idToBlox.put(-i, new BloxSceneGraph());
        }
    }

    public BloxSceneGraph get(int id) {
        if (!idToBlox.containsKey(id)) {
            Debugger.warn("couldnt find blox scene graph for " + id);
            idToBlox.put(id, new BloxSceneGraph());
        }
        BloxSceneGraph bloxSceneGraph = idToBlox.get(id);
        bloxSceneGraph.reset();
        return bloxSceneGraph;
    }

}
