package app.animals;

import app.factory.Animal;
import app.factory.AnimalType;

public class Dog extends Animal {

    public Dog(AnimalType type) {
        super(type);
    }

    @Override
    public void say() {
        System.out.println("Гав");
    }
}
