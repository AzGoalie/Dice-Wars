package com.shadyaardvark.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.Point;

import java.util.ArrayList;
import java.util.List;

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
    private HexagonalGrid grid;

    public MapRenderer(HexagonalGrid grid) {
        this.grid = grid;
        shapeRenderer = new ShapeRenderer();
    }

    public void render(OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        grid.getHexagons().forEach(hexagon -> {

            if (hexagon.getSatelliteData().isPresent()) {
                HexData data = (HexData) hexagon.getSatelliteData().get();

                shapeRenderer.setColor(COLORS[data.team]);

                List<Point> points = new ArrayList<>();
                points.add(Point.fromPosition(hexagon.getCenterX(), hexagon.getCenterY()));
                points.addAll(hexagon.getPoints());

                // Draw each triangle in the hexagon
                for (int i = 0; i < INDEX.length; i += 3) {
                    shapeRenderer.triangle(
                            (float) points.get(INDEX[i]).getCoordinateX(), (float) points.get(INDEX[i]).getCoordinateY(),
                            (float) points.get(INDEX[i + 1]).getCoordinateX(), (float) points.get(INDEX[i + 1]).getCoordinateY(),
                            (float) points.get(INDEX[i + 2]).getCoordinateX(), (float) points.get(INDEX[i + 2]).getCoordinateY());
                }
            }
        });
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
