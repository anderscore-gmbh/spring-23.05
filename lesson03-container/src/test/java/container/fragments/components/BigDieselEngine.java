package container.fragments.components;

import org.springframework.stereotype.Component;

import container.fragments.Engine;

@Component
public class BigDieselEngine extends Engine {

    public BigDieselEngine() {
        super("diesel", 132);
    }

}
