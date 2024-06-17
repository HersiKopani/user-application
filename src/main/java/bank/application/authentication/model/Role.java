package bank.application.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_code")
    private UUID roleCode;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="role")
    @JsonIgnore
    private Set<User> users;

}
