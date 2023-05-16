package tx.fragments;


import jakarta.transaction.Transactional;

public class TransactionalEjb {


    @Transactional(Transactional.TxType.REQUIRED)
    public void standardPropagation() {
    }
}
