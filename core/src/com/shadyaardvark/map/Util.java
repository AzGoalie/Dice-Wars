package com.shadyaardvark.map;

import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.CoordinateConverter;
import org.codetome.hexameter.core.api.HexagonOrientation;

public class Util {
    public static AxialCoordinate convertToAxial(int x, int y) {
        return AxialCoordinate.fromCoordinates(
                CoordinateConverter.convertOffsetCoordinatesToAxialX(x, y, HexagonOrientation.POINTY_TOP),
                CoordinateConverter.convertOffsetCoordinatesToAxialZ(x, y, HexagonOrientation.POINTY_TOP));
    }
}
