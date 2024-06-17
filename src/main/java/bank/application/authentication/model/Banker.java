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
@Table(name = "banker")
public class Banker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "banker_code", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID bankerCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;

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

    @Column(name = "birthday")
    private Date birthday;


}
