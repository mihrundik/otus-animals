package app.factory;

import app.animals.Cat;
import app.animals.Dog;
import app.birds.Duck;

public class AnimalFactory {

    public static Animal create(AnimalType type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case CAT:
                return new Cat(type); // Pass the type
            case DOG:
                return new Dog(type); // Pass the type
            case DUCK:
                return new Duck(type); // Pass the type
            default:
                throw new IllegalArgumentException("Неожидаемый тип животного: " + type);
        }
    }
}