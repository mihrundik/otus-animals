package app.utilities;

import app.factory.AnimalType;

import java.util.Scanner;

public class ReadAnimalType {

    public static AnimalType selectAnimalType(Scanner scanner) {
        String animalInput = "";
        while (AnimalType.doseNotContain2(animalInput)) {
            if (!animalInput.isEmpty()) {
                System.out.println("Введён неправильный тип животного!");
            }
            System.out.printf("Введите один из типов животных (%s): ", String.join("/", AnimalType.TYPES));
            animalInput = scanner.nextLine();
        }
        return AnimalType.fromString2(animalInput);
    }
}
