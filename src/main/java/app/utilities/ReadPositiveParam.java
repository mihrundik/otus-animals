package app.utilities;

import java.util.List;
import java.util.Scanner;

import static app.utilities.ValidId.isValidId;

public class ReadPositiveParam {

    public static int readPositiveAge(Scanner scanner) {
        while (true) {
            System.out.print("Возраст животного: ");
            String input = scanner.nextLine().trim();

            if (input.matches("[1-9][0-9]*")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Возраст должен быть положительным целым числом больше нуля.");
            }
        }
    }

    public static double readPositiveDouble(Scanner scanner) {
        while (true) {
            System.out.print("Вес животного (вес можно вести целым или дробным числом через точку): ");
            String input = scanner.nextLine().trim();

            if (input.matches("^([1-9][0-9]*(\\.\\d*)?)|(0(\\.\\d*[1-9]+)?)$")) {
                return Double.parseDouble(input);
            } else {
                System.out.println("Вес должен быть положительным числом больше или равен нулю.");
            }
        }
    }

    public static <allAnimals> int readPositiveId(Scanner scanner, List<String[]> allAnimals) {

        while (true) {
            System.out.print("Выберите animal_id животного для изменения: ");
            String input = scanner.nextLine().trim();

            if (!input.matches("[1-9][0-9]*")) {
                System.out.println("ID должен быть положительным целым числом больше нуля.");
                continue;
            }

            if (!isValidId(input, allAnimals)) {
                System.out.println("ID не найден в таблице. Попробуйте снова.");
                continue;
            }
            return Integer.parseInt(input);
        }
    }

}
