import java.util.Scanner;

public class ReadPositiveParam {

    static int readPositiveAge(Scanner scanner) {
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

    static double readPositiveDouble(Scanner scanner) {
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
}
