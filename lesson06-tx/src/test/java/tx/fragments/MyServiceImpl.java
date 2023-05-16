package tx.fragments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// tag::service[]
@Service
public class MyServiceImpl implements MyService {

    @Override
    @Transactional // <1>
    public void doSomething() {
        // ...
    }
}
// end::service[]