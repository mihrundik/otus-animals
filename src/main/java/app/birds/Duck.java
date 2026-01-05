package app.birds;

import app.factory.Animal;
import app.factory.AnimalType;

public class Duck extends Animal implements Flying {

    public Duck(AnimalType type) {
        super(type);
    }

    @Override
    public void say() {
        System.out.println("Кря");
    }

    public void fly() {
        System.out.println("Я лечу");
    }
}
