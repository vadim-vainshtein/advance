package test.highmax.finance.screen.transaction;

import io.jmix.ui.screen.*;
import test.highmax.finance.entity.Transaction;

@UiController("Transaction.browse")
@UiDescriptor("transaction-browse.xml")
@LookupComponent("transactionsTable")
public class TransactionBrowse extends StandardLookup<Transaction> {
}