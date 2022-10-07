package test.highmax.finance.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import test.highmax.finance.entity.*;

@ResourceRole(name = "Client", code = "client")
public interface ClientRole {
    @MenuPolicy(menuIds = {"User.browse", "themeSettingsScreen", "BankAccount.browse", "Transaction.browse", "TransactionType.browse"})
    @ScreenPolicy(screenIds = {"LoginScreen", "User.browse", "themeSettingsScreen", "BankAccount.browse", "BankAccount.edit", "Transaction.browse", "Transaction.edit", "TransactionType.edit", "TransactionType.browse"})
    void screens();

    @EntityAttributePolicy(entityClass = BankAccount.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = BankAccount.class, actions = EntityPolicyAction.ALL)
    void bankAccount();

    @EntityPolicy(entityClass = Client.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Client.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void client();

    @EntityPolicy(entityClass = Transaction.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Transaction.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void transaction();

    @EntityPolicy(entityClass = TransactionType.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = TransactionType.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void transactionType();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.ALL)
    void user();
}