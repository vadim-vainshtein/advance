package test.highmax.finance.screen.bankaccount;

import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Client;
import test.highmax.finance.entity.User;

@UiController("BankAccount.edit")
@UiDescriptor("bank-account-edit.xml")
@EditedEntityContainer("bankAccountDc")
public class BankAccountEdit extends StandardEditor<BankAccount> {

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {

        User currentUser = (User) currentAuthentication.getUser();
        BankAccount edited = getEditedEntity();
        edited.setUser(currentUser);
        // кол-во денег хранится в копейках, сразу конвертируем в рубли
        getEditedEntity().setAmount((long) (getEditedEntity().getRub()*100));
    }

}