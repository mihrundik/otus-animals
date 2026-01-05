package app.factory;

public class Animal {

    private final AnimalType type;
    private String name;
    private int age;
    private double weight;
    private Color color;

    public Animal(AnimalType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        char name1 = Character.toUpperCase(name.charAt(0));
        String name2 = name.substring(1);
        this.name = name1 + name2;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void say() {
        System.out.println("Я говорю");
    }

    public void fly() {
    }

    public void go() {
        System.out.println("Я иду");
    }

    public void drink() {
        System.out.println("Я пью");
    }

    public void eat() {
    }


    @Override
    public String toString() {
        return String.format("Привет! Меня зовут %s, мне %d %s, я вешу - %.2f кг, мой цвет - %s.", name, age, getAgeSuffix(), weight, getColorValue());
    }

    private String getColorValue() {
        if (color == null) {
            return "неизвестный";
        }
        return color.getValue();
    }

    private String getAgeSuffix() {
        int remainder10 = age % 10;
        int remainder100 = age % 100;

        if (remainder10 == 1 && remainder100 != 11) {
            return "год";
        }
        if (remainder10 >= 2 && remainder10 <= 4 && remainder100 != 12 && remainder100 != 13 && remainder100 != 14) {
            return "года";
        }
        return "лет";
    }

    public AnimalType getType() {
        return type;
    }
}



