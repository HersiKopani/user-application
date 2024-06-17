package bank.application.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "card")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "card_code", nullable = false)
    private UUID cardCode;

    @Column(name = "card_type", length = 12, nullable = false)
    private String cardType;

    @Column(name = "limit")
    private BigDecimal limit;

    @Column(name = "monthly_salary")
    private BigDecimal monthlySalary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_code", referencedColumnName = "customer_code", nullable = false)
    private Customer customer;

    @Column(name = "created_ts", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date createdTs;

    @Column(name = "card_status")
    private String card_status;

    @Column(name = "updated_ts", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date updatedTs;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_code", referencedColumnName = "bank_code")
    private BankAccount bankAccount;

    @OneToOne(mappedBy="card")
    @JsonIgnore
    private AccountCard accountCard;

}
