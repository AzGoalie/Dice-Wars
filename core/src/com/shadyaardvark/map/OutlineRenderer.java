package com.shadyaardvark.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.internal.impl.HexagonImpl;

import java.util.*;

public class OutlineRenderer {
    private static final int LINE_SIZE = 4;
    private ShapeRenderer shapeRenderer;
    private Set<Line> outlines;

    private List<Hexagon> padding;

    public OutlineRenderer(HexagonalGrid grid) {
        shapeRenderer = new ShapeRenderer();
        outlines = new HashSet<>();

        padding = new ArrayList<>();
        for (int x = -1; x <= grid.getGridData().getGridWidth(); x++) {
            for (int y = -1; y <= grid.getGridData().getGridHeight(); y++) {
                if (x == -1 || x == grid.getGridData().getGridWidth()
                        || y == -1 || y == grid.getGridData().getGridHeight()) {
                    AxialCoordinate coord = Util.convertToAxial(x, y);
                    padding.add(HexagonImpl.newHexagon(grid.getGridData(), coord, null));
                }
            }
        }

        grid.getHexagons().forEach(hexagon -> {
            if (hexagon.getSatelliteData().isPresent()) {
                HexData data = (HexData) hexagon.getSatelliteData().get();
                List<Hexagon> neighbors = new ArrayList<>(grid.getNeighborsOf(hexagon));

                for (Hexagon neighbor : neighbors) {
                    if (neighbor.getSatelliteData().isPresent()) {
                        HexData adjData = (HexData) neighbor.getSatelliteData().get();

                        if (adjData.region != data.region) {
                            createLines(hexagon.getPoints(), neighbor.getPoints());
                        }
                    } else {
                        createLines(hexagon.getPoints(), neighbor.getPoints());
                    }
                }
            }
        });

        padding.forEach(hexagon -> {
            List<Hexagon> neighbors = new ArrayList<>(grid.getNeighborsOf(hexagon));
            for (Hexagon neighbor : neighbors) {
                if (neighbor.getSatelliteData().isPresent()) {
                    createLines(hexagon.getPoints(), neighbor.getPoints());
                }
            }
        });
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

    private void createLines(Collection<Point> p1, Collection<Point> p2) {
        Set<MyPoint> s1 = new HashSet<>();
        Set<MyPoint> s2 = new HashSet<>();

        p1.forEach(point -> s1.add(new MyPoint(point)));
        p2.forEach(point -> s2.add(new MyPoint(point)));

        s1.retainAll(s2);

        Iterator<MyPoint> iter = s1.iterator();
        while (iter.hasNext()) {
            Line line = new Line();
            line.start = iter.next();

            if (iter.hasNext()) {
                line.end = iter.next();
                outlines.add(line);
            }
        }
    }

    private class Line {
        private MyPoint start;
        private MyPoint end;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Line)) return false;
            Line line = (Line) obj;
            return line.start.equals(start) && line.end.equals(start);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + Float.floatToIntBits(start.x);
            result = 31 * result + Float.floatToIntBits(start.y);
            result = 31 * result + Float.floatToIntBits(end.x);
            result = 31 * result + Float.floatToIntBits(end.y);
            return result;
        }
    }

    private class MyPoint {
        public float x;
        public float y;

        public MyPoint(Point point) {
            this.x = Math.round(point.getCoordinateX());
            this.y = Math.round(point.getCoordinateY());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof MyPoint)) return false;
            MyPoint point = (MyPoint) obj;
            return Float.compare(x, point.x) == 0 && Float.compare(y, point.y) == 0;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + Float.floatToIntBits(x);
            result = 31 * result + Float.floatToIntBits(y);
            return result;
        }
    }
}
