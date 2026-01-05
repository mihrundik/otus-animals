import app.birds.Duck;
import app.factory.Animal;
import app.factory.AnimalFactory;
import app.factory.AnimalType;
import app.utilities.*;
import db.ConnectionManager;
import db.dao.AnimalTable;
import db.dao.tools_db.InsertAnimals;
import db.dao.tools_db.ReadAnimals;
import db.dao.tools_db.UpdateAnimals;

import java.sql.SQLException;

import static app.utilities.PrintTable.printTable;


void main() throws SQLException {

    try {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        AnimalTable animalTable = new AnimalTable(connectionManager);

        animalTable.createAnimalsTableIfNotExist();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
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
            ReadAnimals retriever = new ReadAnimals(ConnectionManager.getInstance());
            List<String[]> allAnimals = retriever.retrieveAllAnimals();

            if (allAnimals.isEmpty()) {
                IO.println("Нет записей в таблице.");
            } else {
                printTable(allAnimals);
            }
        } else if (currentCommand == Command.SORT) {
            AnimalType selectedType = ReadAnimalType.selectAnimalType(scanner);

            ReadAnimals retriever = new ReadAnimals(ConnectionManager.getInstance());
            List<String[]> sortedAnimals = retriever.retrieveByType(String.valueOf(selectedType));

            if (sortedAnimals.isEmpty()) {
                IO.println("Нет записей в таблице.");
            } else {
                printTable(sortedAnimals);
            }
        } else if (currentCommand == Command.UPDATE) {
            ReadAnimals retriever = new ReadAnimals(ConnectionManager.getInstance());
            List<String[]> allAnimals = retriever.retrieveAllAnimals();
            printTable(allAnimals);

            int inputId = ReadPositiveParam.readPositiveId(scanner);

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
            updater.update(inputId, formattedData);

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
                    animal.getType().name(),
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