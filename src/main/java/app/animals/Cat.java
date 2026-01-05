package app.animals;

import app.factory.Animal;
import app.factory.AnimalType;

public class Cat extends Animal {

    public Cat(AnimalType type) {
        super(type);
    }

    @Override
    public void say() {
        System.out.println("Мяу");
    }
}
