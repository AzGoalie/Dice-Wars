package com.shadyaardvark.map;

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
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.ShortArray;

public class GameBoardRenderer {
    private static final Color[] COLORS =
            {Color.PURPLE, Color.CYAN, Color.ROYAL, Color.PINK, Color.FOREST, Color.MAROON,
                    Color.LIME, Color.CORAL};

    private PolygonSpriteBatch batch;
    private Map<Integer, PolygonSprite> regionSprites;
    private Map<Integer, Region> regionMap;

    public GameBoardRenderer(Map<Integer, Region> regionMap) {
        batch = new PolygonSpriteBatch();
        regionSprites = new HashMap<>();
        this.regionMap = regionMap;

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.WHITE);
        pix.fill();
        TextureRegion textureRegion = new TextureRegion(new Texture(pix));

        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        for (Region region : regionMap.values()) {
            ShortArray triangles = triangulator.computeTriangles(region.getPoints());
            PolygonRegion polyReg = new PolygonRegion(textureRegion,
                    region.getPoints()
                            .toArray(),
                    triangles.toArray());
            regionSprites.put(region.getRegion(), new PolygonSprite(polyReg));
        }
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
}
