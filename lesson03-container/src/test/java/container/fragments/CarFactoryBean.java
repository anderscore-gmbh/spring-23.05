package container.fragments;

import org.springframework.beans.factory.FactoryBean;

// tag::factory-bean[]
public class CarFactoryBean implements FactoryBean<Car> {

    final Car car = new Car();
    {
        car.setEngine(new Engine("electric", 50));
        car.setColor("white");
    }

    @Override
    public Car getObject() throws Exception {
        return car;
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return true; // Default-Implementierung
    }
}
// end::factory-bean[]