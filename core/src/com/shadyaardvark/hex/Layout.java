package com.shadyaardvark.hex;

import com.badlogic.gdx.math.Vector2;

import java.util.Map;

public interface Layout {
    Map<Vector2, Hexagon> getMap(int width, int height);
}