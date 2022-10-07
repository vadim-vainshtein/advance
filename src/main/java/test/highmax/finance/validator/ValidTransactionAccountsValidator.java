package test.highmax.finance.validator;

import test.highmax.finance.annotation.ValidTransactionAccounts;
import test.highmax.finance.entity.BankAccount;
import test.highmax.finance.entity.Transaction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTransactionAccountsValidator
        implements ConstraintValidator<ValidTransactionAccounts, Transaction> {

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext context) {

        if(transaction == null) {
            return false;
        }

        BankAccount toAccount = transaction.getToAccount();
        BankAccount fromAccount = transaction.getFromAccount();

        // должен быть указан хотя бы один счёт
        if(toAccount == null && fromAccount == null) {
            return false;
        }

        long amount = transaction.getTransferAmount();

        // достаточно ли средств?
        if(fromAccount != null) {
            // счета должны быть разными, использование в транзакции
            // одного и того же счёта не имеет смысла
            if (fromAccount.equals(toAccount)) {
                return false;
            }
        }

        return true;
    }
}
