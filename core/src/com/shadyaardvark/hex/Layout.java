package com.shadyaardvark.hex;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;

public interface Layout {
    Map<Vector2, Hexagon> getMap(int width, int height);
}