package app.factory;

import java.util.ArrayList;
import java.util.List;

public enum AnimalType {

    CAT,
    DOG,
    DUCK;

    public static List<String> TYPES = collectTypes();

    private static List<String> collectTypes() {
        List<String> result = new ArrayList<>();

        for (AnimalType animalType : AnimalType.values()) {
            result.add(animalType.name());
        }
        return result;
    }

    public static boolean doseNotContain2(String value) {
        if (value == null) {
            return true;
        }
        return !TYPES.contains(value.toUpperCase().trim());
    }

    public static AnimalType fromString2(String value) {
        if (value == null) {
            return null;
        }
        return AnimalType.valueOf(value.toUpperCase().trim());
    }
}
