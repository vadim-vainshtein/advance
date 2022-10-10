package test.highmax.finance.listener;

import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Transaction;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionEventListener {

    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onTransactionChangedBeforeCommit(EntityChangedEvent<Transaction> event) {

        Long oldAmount;
        BankAccount oldFromAccount, oldToAccount, newFromAccount, newToAccount;

        Transaction newTransaction =
                dataManager.load(Transaction.class).id(event.getEntityId()).one();
        newFromAccount = newTransaction.getFromAccount();
        newToAccount = newTransaction.getToAccount();

        if(event.getChanges().isChanged("transferAmount"))
            oldAmount = event.getChanges().getOldValue("transferAmount");
        else oldAmount = newTransaction.getTransferAmount();

        // Если старое значение transferAmount существует, нужно вернуть счета к исходному состоянию
        if(oldAmount != null) {
            // Если счёт изменён, загрузим старый
            if(event.getChanges().isChanged("fromAccount")) {
                UUID oldFromAccountId = event.getChanges().getOldValue("fromAccount");
                oldFromAccount = dataManager.load(BankAccount.class).id(oldFromAccountId).one();
            // Если не менялся - оставим новый
            } else {
                oldFromAccount = newFromAccount;
            }
            // в конце концов, если был счёт списания, возвращаем его к исходному состоянию
            if(oldFromAccount != null) {
                oldFromAccount.setAmount(oldFromAccount.getAmount() + oldAmount);
                dataManager.save(oldFromAccount);
            }

            // То же самое со счётом зачисления
            if(event.getChanges().isChanged("toAccount")) {
                UUID oldToAccountId = event.getChanges().getOldValue("toAccount");
                oldToAccount = dataManager.load(BankAccount.class).id(oldToAccountId).one();
            } else {
                oldToAccount = newToAccount;
            }

            if(oldToAccount != null) {
                oldToAccount.setAmount(oldToAccount.getAmount() - oldAmount);
                dataManager.save(oldToAccount);
            }
        }

        // сохраним новые значения сумм на счетах
        if(newToAccount != null) {
            newToAccount.setAmount(newToAccount.getAmount() + newTransaction.getTransferAmount());
            dataManager.save(newToAccount);
        }
        if(newFromAccount != null) {
            newFromAccount.setAmount(newFromAccount.getAmount() - newTransaction.getTransferAmount());
            dataManager.save(newFromAccount);
        }
    }
}