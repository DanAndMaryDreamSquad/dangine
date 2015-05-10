package dangine.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderQueue32 {

    private List<RenderData32> data = new ArrayList<RenderData32>();
    private boolean needsResort = false;

    public void render() {
        if (needsResort) {
            Collections.sort(data);
            needsResort = false;
        }
        for (RenderData32 item : data) {
            item.draw();
        }
    }

    public boolean add(RenderData32 item) {
        invalidSortedOrder();
        return data.add(item);
    }

    public boolean remove(RenderData32 item) {
        return data.remove(item);
    }

    public void clear() {
        data.clear();
    }

    public void invalidSortedOrder() {
        needsResort = true;
    }

}
