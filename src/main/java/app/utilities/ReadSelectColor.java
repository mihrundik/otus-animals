package app.utilities;

import app.factory.Color;

import java.util.Scanner;

public class ReadSelectColor {

    public static Color selectColor(Scanner scanner) {
        String colorInput = "";
        while (Color.doseNotContain3(colorInput)) {
            if (!colorInput.isEmpty()) {
                System.out.println("Введён неправильный цвет животного!");
            }
            System.out.printf("Введите один из цветов животных (%s): ", String.join("/", Color.COLORS));
            colorInput = scanner.nextLine();
        }
        return Color.fromString3(colorInput);
    }
}
