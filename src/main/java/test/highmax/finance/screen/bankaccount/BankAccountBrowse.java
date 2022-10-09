package test.highmax.finance.screen.bankaccount;

import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.User;

import javax.persistence.Query;
import java.util.List;

@UiController("BankAccount.browse")
@UiDescriptor("bank-account-browse.xml")
@LookupComponent("bankAccountsTable")
public class BankAccountBrowse extends StandardLookup<BankAccount> {

    @Autowired
    DataManager dataManager;

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Install(to = "bankAccountsDl", target = Target.DATA_LOADER)
    private List<BankAccount> bankAccountDlLoadDelegate(LoadContext<BankAccount> loadContext) {

        User currentUser = (User)currentAuthentication.getUser();
        List<BankAccount> accounts;

        if(currentUser.getUsername().equals("admin")) {
            accounts = dataManager.load(BankAccount.class)
                    .query("select a from BankAccount a")
                    .list();
        }
        // загружаем только данные текущего пользователя
        else {
             accounts = dataManager.load(BankAccount.class)
                    .query("select a from BankAccount a where a.user.id = :userId")
                    .parameter("userId", currentUser.getId())
                    .list();
        }

        // конвертируем рубли в копейки
        accounts.forEach(a -> a.setRub((double)a.getAmount() / 100));
        return accounts;
    }
}