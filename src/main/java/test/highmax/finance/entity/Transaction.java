package test.highmax.finance.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "TRANSACTION")
@Entity(name = "Transaction")
public class Transaction {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "FROM_ACC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount fromAccount;

    @JoinTable(name = "TRANSACTION_TO_TYPE",
            joinColumns = @JoinColumn(name = "TRANSACTION_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TYPE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<TransactionType> type;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "TO_ACC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount toAccount;

    @Column(name = "CREATE_DATE", columnDefinition = "timestamp")
    private LocalDate createDate;

    @Column(name = "TRANSFER_AMOUNT", nullable = false, columnDefinition = "bigint")
    @NotNull
    private Long transferAmount;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    public BankAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(BankAccount toAccount) {
        this.toAccount = toAccount;
    }

    public void setFromAccount(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public BankAccount getFromAccount() {
        return fromAccount;
    }

    public List<TransactionType> getType() {
        return type;
    }

    public void setType(List<TransactionType> type) {
        this.type = type;
    }

    public Long getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Long transferAmount) {
        this.transferAmount = transferAmount;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}