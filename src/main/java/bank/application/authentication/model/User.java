package bank.application.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_code", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID userCode;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "status")
    private String status;

    @Column(name = "created_ts")
    private LocalDateTime createdTs;

    @Column(name = "updated_ts")
    private LocalDateTime  updatedTs;

    @ManyToOne
    @JoinColumn(name = "role_code", nullable = false)
    private Role role;

    @OneToOne(mappedBy="user")
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy="user")
    @JsonIgnore
    private Banker banker;
}
