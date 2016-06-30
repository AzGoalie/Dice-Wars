package com.shadyaardvark.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MapRenderer {
    private static final short[] INDEX = {
            0, 1, 2,
            0, 2, 3,
            0, 3, 4,
            0, 4, 5,
            0, 5, 6,
            0, 6, 1};

    private static final Color[] COLORS = {Color.PURPLE, Color.CYAN, Color.CORAL, Color.PINK, Color.FOREST, Color.GOLD, Color.CHARTREUSE, Color.ORANGE};

    private ShapeRenderer shapeRenderer;
    private HexagonMap map;

    public MapRenderer(HexagonMap map) {
        this.map = map;
        shapeRenderer = new ShapeRenderer();
    }

    public void render(OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Hexagon hexagon : map.getHexagons()) {
            if (hexagon.isValid()) {
                if (hexagon.isHighlight()) {
                    shapeRenderer.setColor(Color.RED);
                } else {
                    shapeRenderer.setColor(COLORS[hexagon.getTeam()]);
                }

                Array<Vector2> points = hexagon.getPoints();

                // Draw each triangle in the hexagon
                for (int i = 0; i < INDEX.length; i += 3) {
                    shapeRenderer.triangle(points.get(INDEX[i]).x,
                            points.get(INDEX[i]).y,
                            points.get(INDEX[i + 1]).x,
                            points.get(INDEX[i + 1]).y,
                            points.get(INDEX[i + 2]).x,
                            points.get(INDEX[i + 2]).y);
                }
            }
        }
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
