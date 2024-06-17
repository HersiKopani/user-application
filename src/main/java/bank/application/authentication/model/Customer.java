package bank.application.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_code", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID customerCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "notification_number")
    private String notificationNumber;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "created_ts", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private java.sql.Timestamp createdTs;

    @Column(name = "updated_ts", columnDefinition = "timestamp default current_timestamp")
    private java.sql.Timestamp updatedTs;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy="customer")
    @JsonIgnore
    private Set<BankAccount> bankAccounts;
}
