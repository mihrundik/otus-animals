package animals;

//3) Создайте класс Cat, унаследуйте его от Animal.
//Переопределить метод Say (Вывод на экран: «Мяу»).

public class Cat extends Animal {

    @Override
    public void say() {
        System.out.println("Мяу");
    }
}
