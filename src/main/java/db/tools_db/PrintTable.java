package db.tools_db;

import java.util.List;

public class PrintTable {
    //отдельно метод вывода таблицы для LIST и SORT
    public static void printTable(List<String[]> data) {
        String id = "animal_id";
        String type = "type";
        String name = "name";
        String age = "age";
        String weight = "weight";
        String color = "color";
        System.out.printf(" %-10s | %-10s | %-20s | %-10s | %-10s | %-10s\n", id, type, name, age, weight, color);
        System.out.println("-".repeat(90));
        for (String[] row : data) {
            System.out.printf(" %-10s | %-10s | %-20s | %-10s | %-10s | %-10s\n",
                    row[0], row[1], row[2], row[3], row[4], row[5]);
            System.out.println("-".repeat(90));
        }
    }
}
