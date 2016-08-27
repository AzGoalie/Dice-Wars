package com.shadyaardvark.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.shadyaardvark.hex.Hexagon;
import com.shadyaardvark.hex.HexagonMap;

import java.util.HashSet;
import java.util.Set;

import static com.shadyaardvark.Settings.LINE_WIDTH;
import static com.shadyaardvark.map.HexMesh.COLORS;

public class GameBoardRenderer {
    private ShapeRenderer hexRenderer;
    private SpriteBatch fontRenderer;
    private BitmapFont font;
    private GameBoard gameBoard;
    private IntMap<Array<Vector2>> outlines;
    private Array<Vector2> points;

    public GameBoardRenderer(GameBoard board, BitmapFont font, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        this.gameBoard = board;
        hexRenderer = shapeRenderer;
        outlines = new IntMap<>();
        points = new Array<>();

        fontRenderer = spriteBatch;
        this.font = font;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.font.setColor(Color.BLACK);


        for (Region region : gameBoard.getRegionMap().values()) {
            outlines.put(region.getId(),
                    createOutline(gameBoard.getHexagonMap(), region.getHexagons()));
        }
    }

    public void render(Camera camera) {
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

                HexMesh.draw(hexRenderer, points);
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
            font.draw(fontRenderer, String.valueOf(region.getDice()), pos.x - 5, pos.y + 8);
        }
        fontRenderer.end();
    }

    public void dispose() {
        hexRenderer.dispose();
        fontRenderer.dispose();
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
