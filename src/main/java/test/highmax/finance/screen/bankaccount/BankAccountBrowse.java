package test.highmax.finance.screen.bankaccount;

import io.jmix.ui.screen.*;
import test.highmax.finance.entity.BankAccount;

@UiController("BankAccount.browse")
@UiDescriptor("bank-account-browse.xml")
@LookupComponent("bankAccountsTable")
public class BankAccountBrowse extends StandardLookup<BankAccount> {
}