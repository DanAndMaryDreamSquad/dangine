package dangine.scenegraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderQueue {

    List<RenderData> data = new ArrayList<RenderData>();

    public void render() {
        Collections.sort(data);
        for (RenderData item : data) {
            item.draw();
        }
    }

    public boolean add(RenderData item) {
        return data.add(item);
    }

    public boolean remove(RenderData item) {
        return data.remove(item);
    }

    public void clear() {
        data.clear();
    }

}
