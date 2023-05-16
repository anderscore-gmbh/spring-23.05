package tx.fragments;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalSpring {

    @Transactional(propagation = Propagation.REQUIRED)
    public void standardPropagation() {
    }
}
