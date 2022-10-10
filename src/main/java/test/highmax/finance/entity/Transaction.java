package test.highmax.finance.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import test.highmax.finance.annotation.ValidTransactionAccounts;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ValidTransactionAccounts(groups = {Default.class, UiCrossFieldChecks.class})
@JmixEntity
@Table(name = "TRANSACTION")
@Entity(name = "Transaction")
public class Transaction {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "FROM_ACC_ID")
    @ManyToOne
    private BankAccount fromAccount;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "TO_ACC_ID")
    @ManyToOne
    private BankAccount toAccount;

    @JoinTable(name = "TRANSACTION_TO_TYPE",
            joinColumns = @JoinColumn(name = "TRANSACTION_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TRANSACTION_TYPE_ID", referencedColumnName = "ID"))
    @ManyToMany
    @OnDeleteInverse(DeletePolicy.CASCADE)
    private List<TransactionType> type;

    @Column(name = "CREATE_DATE", columnDefinition = "timestamp")
    @PastOrPresent
    private LocalDate createDate;

    @Column(name = "TRANSFER_AMOUNT", nullable = false, columnDefinition = "bigint")
    @NotNull
    private Long transferAmount;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @JmixProperty(mandatory = true)
    @NotNull
    @Transient
    private Double rub;

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public List<TransactionType> getType() {
        return type;
    }

    public void setType(List<TransactionType> type) {
        this.type = type;
    }

    public Double getRub() {
        return rub;
    }

    public void setRub(Double rub) {
        this.rub = rub;
    }

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