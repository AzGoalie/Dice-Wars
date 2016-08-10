package com.shadyaardvark.hex;

import com.badlogic.gdx.math.Vector2;

public class HexagonMapBuilder {
    private Layout layout;
    private Orientation orientation;
    private Vector2 origin;
    private Vector2 hexSize;
    private Vector2 gridSize;

    public HexagonMapBuilder setLayout(Layout layout) {
        this.layout = layout;
        return this;
    }

    public HexagonMapBuilder setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public HexagonMapBuilder setOrigin(int x, int y) {
        this.origin = new Vector2(x, y);
        return this;
    }

    public HexagonMapBuilder setHexSize(int width, int height) {
        this.hexSize = new Vector2(width, height);
        return this;
    }

    public HexagonMapBuilder setGridSize(int width, int height) {
        gridSize = new Vector2(width, height);
        return this;
    }

    public HexagonMap buid() {
        if (layout == null || orientation == null || hexSize == null || gridSize == null) {
            throw new IllegalStateException("Layout, Orientation, HexSize, and GridSize must be set");
        }

        if (origin == null) {
            origin = hexSize;
        }

        return new HexagonMap(layout.getMap((int) gridSize.x, (int) gridSize.y),
                orientation,
                origin,
                hexSize);
    }
}
