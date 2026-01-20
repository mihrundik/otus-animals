package app.utilities;

import java.util.List;

public class ValidId {

    static boolean isValidId(String input, List<String[]> allAnimals) {
        for (String[] row : allAnimals) {
            if (row != null && row.length > 0 && input.equals(row[0])) {
                return true;
            }
        }
        return false;
    }
}
