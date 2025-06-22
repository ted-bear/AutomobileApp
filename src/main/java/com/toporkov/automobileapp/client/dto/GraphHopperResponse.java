package com.toporkov.automobileapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GraphHopperResponse {

    private Hints hints;
    private Info info;
    private List<Path> paths;

    @Data
    public static class Hints {
        @JsonProperty("visited_nodes.sum")
        private int visitedNodesSum;

        @JsonProperty("visited_nodes.average")
        private double visitedNodesAverage;
    }

    @Data
    public static class Info {
        private List<String> copyrights;
        private int took;

        @JsonProperty("road_data_timestamp")
        private String roadDataTimestamp;
    }

    @Data
    public static class Path {
        private double distance;
        private double weight;
        private long time;
        private int transfers;

        @JsonProperty("points_encoded")
        private boolean pointsEncoded;

        private List<Double> bbox;
        private Points points;
        private List<Instruction> instructions;
        private List<Object> legs; // Уточните тип, если legs имеет конкретную структуру
        private Map<String, Object> details; // Уточните тип, если details имеет конкретную структуру
        private double ascend;
        private double descend;

        @JsonProperty("snapped_waypoints")
        private Points snappedWaypoints;
    }

    @Data
    public static class Points {
        private String type;
        private List<List<Double>> coordinates;
    }

    @Data
    public static class Instruction {
        private double distance;
        private Double heading; // Может быть null для последней инструкции
        private int sign;
        private List<Integer> interval;
        private String text;
        private long time;

        @JsonProperty("street_name")
        private String streetName;

        @JsonProperty("street_ref")
        private String streetRef;

        @JsonProperty("exit_number")
        private Integer exitNumber;

        @JsonProperty("exited")
        private Boolean exited;

        @JsonProperty("turn_angle")
        private Double turnAngle;

        @JsonProperty("last_heading")
        private Double lastHeading; // Только для последней инструкции
    }
}
