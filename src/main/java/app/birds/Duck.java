package app.birds;

//5) Создайте класс Duck, унаследуйте его от Animal, реализуйте интерфейс Flying.
//Переопределить метод Say (Вывод на экран: «Кря»).
//Реализовать метод Fly (Вывод на экран: «Я лечу»).

import app.factory.Animal;

public class Duck extends Animal implements Flying {

    @Override
    public void say() {
        System.out.println("Кря");
    }

    public void fly() {
        System.out.println("Я лечу");
    }
}
