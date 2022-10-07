package test.highmax.finance.annotation;

import test.highmax.finance.validator.ValidTransactionAccountsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTransactionAccountsValidator.class)
public @interface ValidTransactionAccounts {
    String message() default  "Accounts should be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
