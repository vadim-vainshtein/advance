package test.highmax.finance.screen.transactiontype;

import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import test.highmax.finance.entity.TransactionType;
import test.highmax.finance.entity.User;

import java.util.List;

@UiController("TransactionType.browse")
@UiDescriptor("transaction-type-browse.xml")
@LookupComponent("transactionTypesTable")
public class TransactionTypeBrowse extends StandardLookup<TransactionType> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Install(to = "transactionTypesDl", target = Target.DATA_LOADER)
    private List<TransactionType> transactionTypesDlLoadDelegate(LoadContext<TransactionType> loadContext) {

        String query = "select t from TransactionType t ";
        User currentUser = (User)currentAuthentication.getUser();

        if(!currentUser.getUsername().equals("admin")) {
            query = "select t from TransactionType t where t.user.id = '" + currentUser.getId() + "'";
        }

        return dataManager.load(TransactionType.class).query(query).list();
    }
    
    
}