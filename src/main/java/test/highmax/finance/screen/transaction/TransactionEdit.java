package test.highmax.finance.screen.transaction;

import io.jmix.ui.Notifications;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Transaction;

import java.util.EventObject;

@UiController("Transaction.edit")
@UiDescriptor("transaction-edit.xml")
@EditedEntityContainer("transactionDc")
public class TransactionEdit extends StandardEditor<Transaction> {

    @Autowired
    Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {


    }

    
    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {

        BankAccount toAccount = getEditedEntity().getToAccount();
        BankAccount fromAccount = getEditedEntity().getFromAccount();

        // prevent commit if the accounts are same
        try {
            if (toAccount.equals(fromAccount)) {
                sameAccounts(event, );
            }
            // = if(toAccount == null)
        } catch (NullPointerException e) {
            if(fromAccount == null) {
                sameAccounts(event);
            }
        }
    }

    /**
     * Прервать commit, выдав сообщение о недопустимости одинаковых счетов в полях ввода
     * @param event
     */
    private void sameAccounts(BeforeCommitChangesEvent event) {
        notifications.create().withCaption("From account and To account should be different").show();
        event.preventCommit();
    }
}