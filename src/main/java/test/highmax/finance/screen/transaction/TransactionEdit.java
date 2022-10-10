package test.highmax.finance.screen.transaction;

import io.jmix.core.DataManager;
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

    @Subscribe("rubField")
    public void onRubFieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        getEditedEntity().setTransferAmount((long) (getEditedEntity().getRub() * 100));
    }
}