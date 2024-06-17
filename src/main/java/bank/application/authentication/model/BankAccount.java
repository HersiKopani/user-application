package bank.application.authentication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "bank_account")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bank_code", updatable = false, nullable = false)
    private UUID bankCode;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "balance")
    private double balance;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "created_ts")
    private Timestamp createdTimestamp;

    @Column(name = "updated_ts")
    private Timestamp updatedTimestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_code", referencedColumnName = "customer_code")
    private Customer customer;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "currency")
    private String currency;

    @Column(name = "iban")
    private String iban;

    @Column(name = "notes")
    private String notes;

    @OneToOne(mappedBy="bankAccount")
    @JsonIgnore
    private AccountCard accountCard;

    //Concurrency
    @Version
    private UUID version;

    // Constructors, getters, and setters
}