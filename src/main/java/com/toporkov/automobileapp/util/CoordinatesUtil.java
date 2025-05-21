package com.toporkov.automobileapp.util;

import java.util.List;

import com.toporkov.automobileapp.model.VehicleCoordinate;

public class CoordinatesUtil {

    private final static Integer EARTH_RADIUS = 6371;

    public static double calculateTotalDistance(List<VehicleCoordinate> coordinates) {
        if (coordinates == null || coordinates.size() < 2) {
            return 0.0;
        }

        double totalDistance = 0.0;

        for (int i = 0; i < coordinates.size() - 1; i++) {
            var point1 = coordinates.get(i);
            var point2 = coordinates.get(i + 1);

            totalDistance += calculateDistance(
                point1.getLatitude(), point1.getLongitude(),
                point2.getLatitude(), point2.getLongitude()
            );
        }

        return totalDistance;
    }

    private static double calculateDistance(
        double lat1, double lon1,
        double lat2, double lon2
    ) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
