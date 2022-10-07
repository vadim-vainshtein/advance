package test.highmax.finance.screen.transactiontype;

import io.jmix.ui.screen.*;
import test.highmax.finance.entity.TransactionType;

@UiController("TransactionType.browse")
@UiDescriptor("transaction-type-browse.xml")
@LookupComponent("transactionTypesTable")
public class TransactionTypeBrowse extends StandardLookup<TransactionType> {
}