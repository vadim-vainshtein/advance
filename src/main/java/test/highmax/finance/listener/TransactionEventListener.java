package test.highmax.finance.listener;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import liquibase.pro.packaged.I;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Transaction;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {

    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onTransactionChangedBeforeCommit(EntityChangedEvent<Transaction> event) {

        Long oldAmount, newAmount;
        BankAccount oldFromAccount, oldToAccount;
        BankAccount newFromAccount = null;
        BankAccount newToAccount = null;

        Transaction newTransaction;

        try {
            newTransaction = dataManager.load(Transaction.class).id(event.getEntityId()).one();
            newFromAccount = newTransaction.getFromAccount();
            newToAccount = newTransaction.getToAccount();
            newAmount = newTransaction.getTransferAmount();
        } catch (IllegalStateException e) {
            newTransaction = null;
            newAmount = 0L;
        }

        if(event.getChanges().isChanged("transferAmount"))
            oldAmount = event.getChanges().getOldValue("transferAmount");
        else oldAmount = newTransaction.getTransferAmount();

        // Если старое значение transferAmount существует, нужно вернуть счета к исходному состоянию
        if(oldAmount != null) {
            // Если счёт изменён, загрузим старый
            if(event.getChanges().isChanged("fromAccount")) {
                Id<BankAccount> oldFromAccountId = event.getChanges().getOldValue("fromAccount");
                if(oldFromAccountId == null) oldFromAccount = null;
                else {
                    try {
                        oldFromAccount = dataManager.load(BankAccount.class).id(oldFromAccountId).one();
                    } catch (IllegalStateException e) {
                        oldFromAccount = null;
                    }
                }
            } // Если не менялся - оставим новый
            else {
                oldFromAccount = newFromAccount;
            }
            // в конце концов, если был счёт списания, возвращаем его к исходному состоянию
            if(oldFromAccount != null) {
                oldFromAccount.setAmount(oldFromAccount.getAmount() + oldAmount);
                dataManager.save(oldFromAccount);
            }

            // То же самое со счётом зачисления
            if(event.getChanges().isChanged("toAccount")) {
                Id<BankAccount> oldToAccountId = event.getChanges().getOldValue("toAccount");
                if(oldToAccountId == null) oldToAccount = null;
                else {
                    try {
                        oldToAccount = dataManager.load(BankAccount.class).id(oldToAccountId).one();
                    } catch (IllegalStateException e) {
                        oldToAccount = null;
                    }
                }
            }
            else {
                oldToAccount = newToAccount;
            }

            if(oldToAccount != null) {
                oldToAccount.setAmount(oldToAccount.getAmount() - oldAmount);
                dataManager.save(oldToAccount);
            }
        }

        // сохраним новые значения сумм на счетах
        if(newToAccount != null) {
            newToAccount.setAmount(newToAccount.getAmount() + newAmount);
            dataManager.save(newToAccount);
        }
        if(newFromAccount != null) {
            newFromAccount.setAmount(newFromAccount.getAmount() - newAmount);
            dataManager.save(newFromAccount);
        }
    }
}