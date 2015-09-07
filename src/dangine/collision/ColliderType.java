package dangine.collision;

import dangine.utility.Vector2f;

public enum ColliderType {

    LIGHT(20, new Vector2f(8, 8)), //
    HEAVY(20, new Vector2f(4, 12)), //
    COUNTER(50, new Vector2f(5, 20));

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
