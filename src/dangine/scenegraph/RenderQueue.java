package dangine.scenegraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderQueue {

    private List<RenderData> data = new ArrayList<RenderData>();
    private boolean needsResort = false;

    public void render() {
        if (needsResort) {
            Collections.sort(data);
            needsResort = false;
        }
        for (RenderData item : data) {
            item.draw();
        }
    }

    public boolean add(RenderData item) {
        invalidSortedOrder();
        return data.add(item);
    }

    public boolean remove(RenderData item) {
        return data.remove(item);
    }

    public void clear() {
        data.clear();
    }

    public void invalidSortedOrder() {
        needsResort = true;
    }

}
