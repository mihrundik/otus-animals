package app.factory;

import java.util.ArrayList;
import java.util.List;

public enum Color {

    WHITE("белый"),
    BROWN("коричневый"),
    GRAY("серый"),
    BLACK("черный"),
    YELLOW("желтый"),
    FAWN("палевый"),
    CHAMPAGNE("цвета шампанского"),
    TORTOISESHELL("черепаховый окрас");

    private String value;

    Color(String value) {
        this.value = value;
    }

    public static Object stringColor(String trim) {
        return trim;
    }

    public String getValue() {
        return value;
    }

    public static List<String> COLORS = collectColors();

    private static List<String> collectColors() {
        List<String> result = new ArrayList<>();

        for (Color color : Color.values()) {
            result.add(color.name());
        }
        return result;
    }

    public static boolean doseNotContain3(String value) {
        if (value == null) {
            return true;
        }
        return !COLORS.contains(value.toUpperCase().trim());
    }

    public static Color fromString3(String value) {
        if (value == null) {
            return null;
        }
        return Color.valueOf(value.toUpperCase().trim());
    }
}
