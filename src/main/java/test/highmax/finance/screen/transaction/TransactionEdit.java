package test.highmax.finance.screen.transaction;

import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Transaction;

@UiController("Transaction.edit")
@UiDescriptor("transaction-edit.xml")
@EditedEntityContainer("transactionDc")
public class TransactionEdit extends StandardEditor<Transaction> {

    @Autowired
    Notifications notifications;

    @Autowired
    DataManager dataManager;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {

        BankAccount toAccount = getEditedEntity().getToAccount();
        BankAccount fromAccount = getEditedEntity().getFromAccount();

        // приход, если указан целевой счёт
        if(toAccount != null) {
            toAccount.setAmount(toAccount.getAmount() + getEditedEntity().getTransferAmount());
            dataManager.save(toAccount);
        }

        // расход, если указан счёт списания
        if(fromAccount != null) {
            long amount = getEditedEntity().getTransferAmount();
            // Если недостаточно средств
            if(fromAccount.getAmount() < amount) {
                notifications.create().withCaption("Not enough money on " + fromAccount.getName()).show();
                event.preventCommit();
            }

            fromAccount.setAmount(fromAccount.getAmount() - amount);
            dataManager.save(fromAccount);
        }
    }
}