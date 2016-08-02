package com.shadyaardvark.map;

import static com.sun.javafx.sg.prism.NGCanvas.LINE_WIDTH;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;

public class GameBoardRenderer {
    private static final Color[] COLORS =
            {Color.PURPLE, Color.CYAN, Color.ROYAL, Color.PINK, Color.FOREST, Color.MAROON,
                    Color.LIME, Color.CORAL};

    private PolygonSpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Map<Integer, PolygonSprite> regionSprites;
    private Map<Integer, Region> regionMap;

    public GameBoardRenderer(Map<Integer, Region> regionMap) {
        batch = new PolygonSpriteBatch();
        shapeRenderer = new ShapeRenderer();
        regionSprites = createRegions(regionMap);
        this.regionMap = regionMap;
    }

    public void render(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Region region : regionMap.values()) {
            if (region.isValid()) {
                PolygonSprite sprite = regionSprites.get(region.getRegion());
                if (region.isHighlight()) {
                    sprite.setColor(Color.RED);
                } else {
                    sprite.setColor(COLORS[region.getTeam()]);
                }
                regionSprites.get(region.getRegion())
                        .draw(batch);
            }
        }
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

    private Map<Integer, PolygonSprite> createRegions(Map<Integer, Region> regionMap) {
        Map<Integer, PolygonSprite> result = new HashMap<>();

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.WHITE);
        pix.fill();
        TextureRegion textureRegion = new TextureRegion(new Texture(pix));

        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        for (Region region : regionMap.values()) {
            FloatArray verts = new FloatArray();
            for (Vector2 p : region.getPoints()) {
                verts.addAll(p.x, p.y);
            }

            ShortArray triangles = triangulator.computeTriangles(verts);
            PolygonRegion polyReg = new PolygonRegion(textureRegion,
                    verts.toArray(),
                    triangles.toArray());
            result.put(region.getRegion(), new PolygonSprite(polyReg));
        }

        return result;
    }

    private Array<Array<Vector2>> createOutlines(Map<Integer, Region> regionMap) {
        Array<Array<Vector2>> result = new Array<>();

        for (Region region : regionMap.values()) {
            Array<Vector2> outline = new Array<>();

            outline.add(region.getPoints().get(0));
            for (int i=0; i<region.getPoints().size-1; i++) {
                Vector2 a = region.getPoints().get(i);
                Vector2 b = region.getPoints().get(i+1);

               outline.addAll(perp(a, b));
            }

            outline.add(region.getPoints().get(region.getPoints().size-1));
            result.add(outline);
        }

        return result;
    }

    private Array<Vector2> perp(Vector2 a, Vector2 b) {
        Array<Vector2> result = new Array<>();
        Vector2 perp = new Vector2();

        Vector2 p = a;
        Vector2 p2 = b;

        perp.set(p).sub(p2).nor();

        perp.set(-perp.y, perp.x);

        perp.scl(LINE_WIDTH/2f);

        result.add(new Vector2(p.x+perp.x, p.y+perp.y));
        result.add(new Vector2(p.x-perp.x, p.y-perp.y));

        return result;
    }
}
