package app.utilities;

import java.util.ArrayList;
import java.util.List;

public enum Command {

    ADD,
    LIST,
    EXIT;

    public static List<String> COMMAND = collectNames();

    private static List<String> collectNames() {
        List<String> result = new ArrayList<>();

        for (Command command : Command.values()) {
            result.add(command.name());
        }
        return result;
    }

    public static boolean doseNotContain(String value) {
        if (value == null) {
            return true;
        }
        return !COMMAND.contains(value.toUpperCase().trim());
    }

    public static Command fromString(String value) {
        if (value == null) {
            return null;
        }
        return Command.valueOf(value.toUpperCase().trim());
    }
}
