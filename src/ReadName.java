import java.util.Scanner;

public class ReadName {

    static String readName(Scanner scanner) {
        String name = "";
        while (true) {
            System.out.print("Имя животного: ");
            name = scanner.nextLine();

            if (!name.trim().isEmpty()) {
                return name;
            } else {
                System.out.println("Имя животного не было введено. Повторите ввод.");
            }
        }
    }
}
