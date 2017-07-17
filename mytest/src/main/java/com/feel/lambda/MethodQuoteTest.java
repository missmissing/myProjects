package com.feel.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by yulong.li on 2017/7/17.
 */
public class MethodQuoteTest {
    public static void main(String[] args) {
        Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);

        cars.forEach(c -> System.out.println(c));
        cars.forEach(Car::collide);

        cars.forEach( Car::repair );

        final Car police = Car.create( Car::new );
        cars.forEach( police::follow );
    }
}

class Car {
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}

