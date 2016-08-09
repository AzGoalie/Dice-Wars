package com.shadyaardvark.map;

import static com.shadyaardvark.Settings.LINE_WIDTH;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.HexagonMap;

public class GameBoardRenderer {
    private static final Color[] COLORS =
            {Color.valueOf("AA2FFF"), Color.valueOf("01B0F0"), Color.valueOf("97FF7F"), Color.valueOf("E8A241"), Color.valueOf("FF5757")};

    private static final short[] INDEX = {0, 1, 2, 0, 2, 3, 0, 3, 4, 0, 4, 5, 0, 5, 6, 0, 6, 1};

    private ShapeRenderer hexRenderer;
    private SpriteBatch fontRenderer;
    private DistanceFieldFont font;
    private GameBoard gameBoard;
    private IntMap<Array<Vector2>> outlines;
    private Array<Vector2> points;

    public GameBoardRenderer(GameBoard board) {
        this.gameBoard = board;
        hexRenderer = new ShapeRenderer();
        outlines = new IntMap<>();
        points = new Array<>();

        fontRenderer = new SpriteBatch();
        fontRenderer.setShader(DistanceFieldFont.createDistanceFieldShader());

        font = new DistanceFieldFont(Gdx.files.internal("verdana39distancefield.fnt"));
        font.getData().setScale(.25f);
        font.setDistanceFieldSmoothing(5);
        font.setColor(Color.BLACK);


        for (Region region : gameBoard.getRegionMap().values()) {
            outlines.put(region.getId(),
                    createOutline(gameBoard.getHexagonMap(), region.getHexagons()));
        }
    }

    public void render(OrthographicCamera camera) {
        hexRenderer.setProjectionMatrix(camera.combined);
        hexRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Region region : gameBoard.getRegionMap().values()) {
            if (region.isHighlight()) {
                hexRenderer.setColor(Color.RED);
            } else {
                hexRenderer.setColor(COLORS[region.getTeam()]);
            }

            for (Hexagon hexagon : region.getHexagons()) {
                points.clear();
                points.add(gameBoard.getHexagonMap().getHexCenter(hexagon));
                points.addAll(gameBoard.getHexagonMap().getHexCorners(hexagon));

                // Draw each triangle in the hexagon
                for (int i = 0; i < INDEX.length; i += 3) {
                    hexRenderer.triangle(points.get(INDEX[i]).x,
                            points.get(INDEX[i]).y,
                            points.get(INDEX[i + 1]).x,
                            points.get(INDEX[i + 1]).y,
                            points.get(INDEX[i + 2]).x,
                            points.get(INDEX[i + 2]).y);
                }
            }

            // Draw outline
            hexRenderer.setColor(Color.BLACK);
            for (int i = 0; i < outlines.get(region.getId()).size; i += 2) {
                Vector2 a = outlines.get(region.getId()).get(i);
                Vector2 b = outlines.get(region.getId()).get(i + 1);
                hexRenderer.rectLine(a.x, a.y, b.x, b.y, LINE_WIDTH);
            }
        }
        hexRenderer.end();

        fontRenderer.setProjectionMatrix(camera.combined);
        fontRenderer.begin();
        for (Region region : gameBoard.getRegionMap().values()) {
            Vector2 pos = gameBoard.getHexagonMap().getHexCenter(region.getHexagons().first());
            font.draw(fontRenderer, Integer.toString(region.getDice()), pos.x-3, pos.y+4);
        }
        fontRenderer.end();
    }

    public void dispose() {
        hexRenderer.dispose();
        fontRenderer.dispose();
        font.dispose();
    }

    private Array<Vector2> createOutline(HexagonMap map, Array<Hexagon> hexagons) {
        Array<Vector2> result = new Array<>();

        for (Hexagon hexagon : hexagons) {
            Array<Hexagon> neighbors = hexagon.getNeighbors();

            for (Hexagon neighbor : neighbors) {
                if (!hexagons.contains(neighbor, false)) {
                    Set<Vector2> set = new HashSet<>();

                    for (Vector2 v1 : map.getHexCorners(hexagon)) {
                        for (Vector2 v2 : map.getHexCorners(neighbor)) {
                            if (v1.epsilonEquals(v2, 0.1f)) {
                                set.add(v1);
                            }
                        }
                    }

                    for (Vector2 v : set) {
                        result.add(v);
                    }
                }
            }
        }

        return result;
    }
}
