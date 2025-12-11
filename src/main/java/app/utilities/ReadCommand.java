package app.utilities;

import java.util.Scanner;

public class ReadCommand {

    public static Command getCommand(Scanner scanner) {
        String commandInput = null;
        while (Command.doseNotContain(commandInput)) {
            if (commandInput != null && !commandInput.trim().isEmpty()) {
                System.out.println("Введена неверная команда!");
            }
            System.out.printf("Введите одну из команд (%s): ", String.join("/", Command.COMMAND));
            commandInput = scanner.nextLine();
        }
        return Command.fromString(commandInput);
    }
}
