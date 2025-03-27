package com.toporkov.automobileapp.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public final class CoordinateUtil {

    private static final int SRID = 4326;

    public static Point getPoint(Double latitude, Double longitude) {
        var geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
