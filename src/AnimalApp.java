import animals.Animal;

//6) В основной программе:
//Создайте ArrayList Animal
//Создайте в консоли меню. При входе в приложение на экран выводится запрос команды add/list/exit
//Команды оформить в enum. При вводе команды она должна быть регистронезависимой и обрезать пробелы в начале и конце
//7) Если add
//спросить какое животное (cat/dog/duck)
//cпросить имя, возраст, вес, цвет
//bнициализировать класс
//добавить экземпляр в ArrayList и вызвать метод Say()
//dернуться к главному меню
//8) Если list
//Вывести на экран метод toString() для всех элементов массива
//9)Если exit
//выйти из программы.

import animals.Color;
import factory.AnimalFactory;
import factory.AnimalType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AnimalApp {

    public static void main(String[] args) {

        List<Animal> animals = new ArrayList<>();
        AnimalFactory animalFactory = new AnimalFactory();

        Scanner scanner = new Scanner(System.in);
        Command currentCommand = null;

        while (currentCommand != Command.EXIT) {
            currentCommand = getCommand(scanner);
            if (currentCommand == Command.LIST) {
                if (animals.isEmpty()) {
                    scanner.nextLine();
                    System.out.println("Список пуст.");
                }
                for (Animal animal : animals) {
                    scanner.nextLine();
                    System.out.println(animal);
                }
            } else if (currentCommand == Command.ADD) {
                scanner.nextLine();
                AnimalType animalType = selectAnimalType(scanner);
                Animal animal = animalFactory.create(animalType);

                scanner.nextLine();

                // запрос параметров животного
                System.out.print("Имя животного: ");
                String name = scanner.next().trim();

                System.out.print("Возраст животного: ");
                int age = Integer.parseInt(scanner.next());

                System.out.print("Вес животного: ");
                double weight = Double.parseDouble(scanner.next());

                Color animalColor = selectColor(scanner);

                // установка полученных параметров
                animal.setName(name);
                animal.setAge(age);
                animal.setWeight((int) weight);
                animal.setColor(animalColor);

                animals.add(animal);
                animal.say();
            }
        }
    }

    private static Color selectColor(Scanner scanner) {
        String colorInput = null;
        while (Color.doseNotContain3(colorInput)) {
            if (colorInput != null) {
                System.out.println("Введён неправильный цвет животного!");
            }
            System.out.printf("Введите один из цветов животных (%s): ", String.join("/", Color.COLORS));
            colorInput = scanner.next();
        }
        return Color.fromString3(colorInput);
    }

    private static AnimalType selectAnimalType(Scanner scanner) {
        String animalInput = null;
        while (AnimalType.doseNotContain2(animalInput)) {
            if (animalInput != null) {
                System.out.println("Введён неправильный тип животного!");
            }
            System.out.printf("Введите один из типов животных (%s): ", String.join("/", AnimalType.TYPES));
            animalInput = scanner.next();
        }
        return AnimalType.fromString2(animalInput);
    }


    private static Command getCommand(Scanner scanner) {
        String commandInput = null;
        while (Command.doseNotContain(commandInput)) {
            if (commandInput != null) {
                System.out.println("Введена неверная команда!");
            }
            System.out.printf("Введите одну из команд (%s): ", String.join("/", Command.NAMES));
            commandInput = scanner.next();
        }
        return Command.fromString(commandInput);
    }
}

