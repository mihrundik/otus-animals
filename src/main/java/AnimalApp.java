import app.birds.Duck;
import app.factory.Animal;
import app.factory.AnimalFactory;
import app.factory.AnimalType;
import app.utilities.*;
import db.ConnectionManager;
import db.tools_db.InsertAnimals;
import db.tools_db.RetrieveAnimals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AnimalApp {

    public static void main(String[] args) {

        List<Animal> animals = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        Command currentCommand = null;

        while (currentCommand != Command.EXIT) {
            currentCommand = ReadCommand.getCommand(scanner);
            if (currentCommand == Command.LIST) {
                RetrieveAnimals retriever = new RetrieveAnimals(ConnectionManager.getInstance());
                List<String[]> allAnimals = retriever.retrieveAllAnimals();

                if (allAnimals.isEmpty()) {
                    System.out.println("Нет записей в таблице.");
                } else {
                    printTable(allAnimals);
                }
            }
            else if (currentCommand == Command.SORT) {
                System.out.print("Выберите тип животного для сортировки (CAT, DOG, DUCK): ");
                String selectedType = scanner.nextLine().toUpperCase().trim();

                RetrieveAnimals retriever = new RetrieveAnimals(ConnectionManager.getInstance());
                List<String[]> sortedAnimals = retriever.retrieveByType(selectedType);

                if (sortedAnimals.isEmpty()) {
                    System.out.println("Нет записей в таблице.");
                } else {
                    printTable(sortedAnimals);
                }
            }
            else if (currentCommand == Command.ADD) {
                AnimalType animalType = ReadAnimalType.selectAnimalType(scanner);
                Animal animal = AnimalFactory.create(animalType);

                // определяем и устанавливаем параметры животных
                animal.setName(ReadName.readName(scanner));

                int age = ReadPositiveParam.readPositiveAge(scanner);
                double weight = ReadPositiveParam.readPositiveDouble(scanner);

                animal.setAge(age);
                animal.setWeight(weight);
                animal.setColor(ReadSelectColor.selectColor(scanner));

                // добавляем животного в базу данных
                InsertAnimals inserter = new InsertAnimals(ConnectionManager.getInstance());
                inserter.insertAnimal(
                        animalType.name(),
                        animal.getName(),
                        animal.getAge(),
                        animal.getWeight(),
                        String.valueOf(animal.getColor())
                );

                animals.add(animal);
                animal.say();

                // вызов интерфейса для утки
                if (animal instanceof Duck) {
                    ((Duck) animal).fly();
                }
            }
        }
        ConnectionManager.getInstance().close();
    }

    //отдельно метод вывода таблицы для LIST и SORT
    private static void printTable(List<String[]> data) {
        String id = "animal_id";
        String type = "type";
        String name = "name";
        String age = "age";
        String weight = "weight";
        String color = "color";
        System.out.printf(" %-10s | %-10s | %-20s | %-10s | %-10s | %-10s\n", id, type, name, age, weight, color);
        System.out.println("-".repeat(82));
        for (String[] row : data) {
            System.out.printf(" %-10s | %-10s | %-20s | %-10s | %-10s | %-10s\n",
                    row[0], row[1], row[2], row[3], row[4], row[5]);
            System.out.println("-".repeat(82));
        }
    }


}