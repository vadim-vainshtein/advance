package test.highmax.finance.screen.transaction;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Transaction;

@UiController("Transaction.edit")
@UiDescriptor("transaction-edit.xml")
@EditedEntityContainer("transactionDc")
public class TransactionEdit extends StandardEditor<Transaction> {

    @Autowired
    private Notifications notifications;

    /**
     * Валидация данных
     */
    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {

        BankAccount fromAccount = getEditedEntity().getFromAccount();
        Long amount = getEditedEntity().getTransferAmount();

        // Достаточно ли средств?
        if(fromAccount != null && amount > fromAccount.getAmount()) {
           notifications.create().withCaption("Not enough money on " + fromAccount.getName()).show();
           event.preventCommit();
        }

        // Указан ли тип?
        if(getEditedEntity().getType().isEmpty()) {
            notifications.create().withCaption("Please enter at least one transaction type").show();
            event.preventCommit();
        }
    }

    /**
     * Преобразование рублей в копейки
     */
    @Subscribe("rubField")
    public void onRubFieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        getEditedEntity().setTransferAmount((long) (getEditedEntity().getRub() * 100));
    }
}