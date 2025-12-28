import app.birds.Duck;
import app.factory.Animal;
import app.factory.AnimalFactory;
import app.factory.AnimalType;
import app.utilities.*;
import db.ConnectionManager;
import db.tools_db.InsertAnimals;
import db.tools_db.RetrieveAnimals;
import db.tools_db.UpdateAnimals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static db.tools_db.PrintTable.printTable;


public class AnimalApp {

    public static void main(String[] args) throws SQLException {

        List<Animal> animals = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        Command currentCommand = null;

        while (true) {
            currentCommand = ReadCommand.getCommand(scanner);
            AnimalType animalType;
            if (currentCommand == Command.EXIT) {
                ConnectionManager.getInstance().close();
                break;
            }
            if (currentCommand == Command.LIST) {
                RetrieveAnimals retriever = new RetrieveAnimals(ConnectionManager.getInstance());
                List<String[]> allAnimals = retriever.retrieveAllAnimals();

                if (allAnimals.isEmpty()) {
                    System.out.println("Нет записей в таблице.");
                } else {
                    printTable(allAnimals);
                }
            } else if (currentCommand == Command.SORT) {
                AnimalType selectedType = ReadAnimalType.selectAnimalType(scanner);

                RetrieveAnimals retriever = new RetrieveAnimals(ConnectionManager.getInstance());
                List<String[]> sortedAnimals = retriever.retrieveByType(String.valueOf(selectedType));

                if (sortedAnimals.isEmpty()) {
                    System.out.println("Нет записей в таблице.");
                } else {
                    printTable(sortedAnimals);
                }
            } else if (currentCommand == Command.UPDATE) {
                RetrieveAnimals retriever = new RetrieveAnimals(ConnectionManager.getInstance());
                List<String[]> allAnimals = retriever.retrieveAllAnimals();
                printTable(allAnimals);

                System.out.print("Выберите animal_id животного для изменения: ");
                String inputId = scanner.nextLine().trim();
                int selectedID = Integer.parseInt(inputId);

                animalType = ReadAnimalType.selectAnimalType(scanner);
                Animal animal = AnimalFactory.create(animalType);

                // устанавливаем новые параметры животных
                animal.setName(ReadName.readName(scanner));
                int age = ReadPositiveParam.readPositiveAge(scanner);
                double weightD = ReadPositiveParam.readPositiveDouble(scanner);
                String weightS = Double.toString(weightD).replace(',', '.');
                animal.setColor(ReadSelectColor.selectColor(scanner));

                String formattedData = String.format("%s,%d,%s,%s",
                        animal.getName(), age, weightS, animal.getColor());

                UpdateAnimals updater = new UpdateAnimals(ConnectionManager.getInstance());
                updater.update(selectedID, formattedData);

            } else if (currentCommand == Command.ADD) {
                animalType = ReadAnimalType.selectAnimalType(scanner);
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
    }
}