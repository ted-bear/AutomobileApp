package com.toporkov.automobileapp.web.mapper.converter;

import org.locationtech.jts.geom.Point;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class PointToCoordinatesConverter extends AbstractConverter<Point, String> {

    @Override
    protected String convert(Point point) {
        Assert.notNull(point, "point argument is null");

        return point.toString();
    }
}
