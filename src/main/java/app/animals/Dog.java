package app.animals;

//4) Создайте класс Dog, унаследуйте его от Animal
//Переопределить метод Say (Вывод на экран: «Гав»)

import app.factory.Animal;

public class Dog extends Animal {

    @Override
    public void say() {
        System.out.println("Гав");
    }
}
