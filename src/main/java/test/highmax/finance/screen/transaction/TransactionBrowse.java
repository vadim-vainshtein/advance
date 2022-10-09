package test.highmax.finance.screen.transaction;

import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Transaction;
import test.highmax.finance.entity.User;

import java.util.List;

@UiController("Transaction.browse")
@UiDescriptor("transaction-browse.xml")
@LookupComponent("transactionsTable")
public class TransactionBrowse extends StandardLookup<Transaction> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private DataManager dataManager;

    @Install(to = "transactionsDl", target = Target.DATA_LOADER)
    private List<Transaction> transactionsDlLoadDelegate(LoadContext<Transaction> loadContext) {

        User currentUser = (User)currentAuthentication.getUser();
        List<Transaction> transactions = dataManager.load(Transaction.class)
                .query("select t from Transaction t ")
                .list();
        // загружаем только данные текущего пользователя, если это не админ
        // TODO: лучше загружать сразу только необходимые данные из базы, но пока это не удалось
        if(!currentUser.getUsername().equals("admin")) {
            transactions = transactions.stream().filter(
                    t -> t.getType().get(0).getUser().getId().equals(currentUser.getId()))
                    .toList();
        }

        // переводим копейки в рубли
        transactions.forEach(t -> t.setRub((double)(t.getTransferAmount() / 100)));
        return transactions;
    }

}