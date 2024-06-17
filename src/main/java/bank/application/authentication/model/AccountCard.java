package bank.application.authentication.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_card")
public class AccountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_card_code")
    private UUID accountCardCode;

    @Column(name = "created_ts")
    private Date createdTs;

    @Column(name = "updated_ts")
    private Date updatedTs;

    @ManyToOne
    @JoinColumn(name = "account_code", referencedColumnName = "bank_code")
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "card_code")
    private Card card;

}