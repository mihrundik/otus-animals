import app.factory.Animal;

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

import app.birds.Duck;
import app.factory.AnimalFactory;
import app.factory.AnimalType;
import app.utilities.*;

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
            currentCommand = ReadCommand.getCommand(scanner);
            if (currentCommand == Command.LIST) {
                if (animals.isEmpty()) {
                    scanner.nextLine();
                    System.out.println("Список пуст.");
                }
                for (Animal animal : animals) {
                    System.out.println(animal);
                }
            } else if (currentCommand == Command.ADD) {
                AnimalType animalType = ReadAnimalType.selectAnimalType(scanner);
                Animal animal = animalFactory.create(animalType);

                // определяем и устанавливаем параметры животных
                animal.setName(ReadName.readName(scanner));

                int age = ReadPositiveParam.readPositiveAge(scanner);
                double weight = ReadPositiveParam.readPositiveDouble(scanner);

                animal.setAge(age);
                animal.setWeight(weight);
                animal.setColor(ReadSelectColor.selectColor(scanner));

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