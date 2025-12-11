package app.animals;

//3) Создайте класс Cat, унаследуйте его от Animal.
//Переопределить метод Say (Вывод на экран: «Мяу»).

import app.factory.Animal;

public class Cat extends Animal {

    @Override
    public void say() {
        System.out.println("Мяу");
    }
}
