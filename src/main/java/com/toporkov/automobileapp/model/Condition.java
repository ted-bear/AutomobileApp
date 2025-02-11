package com.toporkov.automobileapp.model;

import com.toporkov.automobileapp.util.exception.NoSuchConditionException;

import java.util.Arrays;
import java.util.List;

public enum Condition {
    NEW("Новый"),
    USED("Б/у");

    private final String description;

    Condition(String description) {
        this.description = description;
    }

    public static Condition getByDescription(String description) {
        if ("Новый".equals(description)) {
            return Condition.NEW;
        } else if ("Б/у".equals(description)) {
            return Condition.USED;
        }
        throw new NoSuchConditionException();
    }

    public static List<Condition> getAsList() {
        return Arrays.asList(Condition.values());
    }

    public String getDescription() {
        return description;
    }
}
