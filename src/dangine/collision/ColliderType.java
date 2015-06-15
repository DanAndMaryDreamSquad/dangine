package dangine.collision;

import dangine.utility.Vector2f;

public enum ColliderType {

    LIGHT(25, new Vector2f(-38, 0)), //
    HEAVY(35, new Vector2f(-25, 0)), //
    COUNTER(50, new Vector2f(-16, 0));

    final int size;
    final Vector2f offset;

    ColliderType(int size, Vector2f offset) {
        this.size = size;
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public Vector2f getOffset() {
        return offset;
    }

}
