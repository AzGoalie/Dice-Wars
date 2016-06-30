package com.shadyaardvark.map;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class OutlineRenderer {
    private static final int LINE_SIZE = 4;
    private ShapeRenderer shapeRenderer;
    private Set<Line> outlines;

    public OutlineRenderer(HexagonMap map) {
        shapeRenderer = new ShapeRenderer();
        outlines = new HashSet<>();

        Array<Hexagon> padding = new Array<>();
        for (int x = -1; x <= map.getWidth(); x++) {
            for (int y = -1; y <= map.getHeight(); y++) {
                if (x == -1 || x == map.getWidth() || y == -1 || y == map.getHeight()) {
                    padding.add(new Hexagon(x, y, map.getHexSize()));
                }
            }
        }

        for (Hexagon hexagon : map.getHexagons()) {
            if (hexagon.isValid()) {
                Array<Hexagon> neighbors = map.getNeighborsOf(hexagon);

                for (Hexagon neighbor : neighbors) {
                    if (neighbor.isValid()) {
                        if (neighbor.getRegion() != hexagon.getRegion()) {
                            createLine(hexagon.getPoints(), neighbor.getPoints());
                        }
                    } else {
                        createLine(hexagon.getPoints(), neighbor.getPoints());
                    }
                }
            }
        }

        for (Hexagon hexagon : padding) {
            Array<Hexagon> neighbors = map.getNeighborsOf(hexagon);
            for (Hexagon neighbor : neighbors) {
                if (neighbor.isValid()) {
                    createLine(hexagon.getPoints(), neighbor.getPoints());
                }
            }
        }
    }

    public void render(OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);

        for (Line line : outlines) {
            shapeRenderer.rectLine(line.start.x, line.start.y, line.end.x, line.end.y, LINE_SIZE);
        }

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

    private void createLine(Array<Vector2> hexagon1, Array<Vector2> hexagon2) {
        Line line = new Line();
        for (Vector2 p1 : hexagon1) {
            for (Vector2 p2 : hexagon2) {
                if (p1.epsilonEquals(p2, .1f)) {
                    line.add(p1);
                }
            }
        }
        outlines.add(line);
    }

    private class Line {
        private Vector2 start;
        private Vector2 end;

        void add(Vector2 point) {
            if (start == null) {
                start = point;
            } else {
                end = point;
            }
        }
    }
}
