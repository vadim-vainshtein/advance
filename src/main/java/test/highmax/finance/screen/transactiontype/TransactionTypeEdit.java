package test.highmax.finance.screen.transactiontype;

import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.TransactionType;
import test.highmax.finance.entity.User;

@UiController("TransactionType.edit")
@UiDescriptor("transaction-type-edit.xml")
@EditedEntityContainer("transactionTypeDc")
public class TransactionTypeEdit extends StandardEditor<TransactionType> {

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {

        User currentUser = (User) currentAuthentication.getUser();
        getEditedEntity().setUser(currentUser);
    }
}