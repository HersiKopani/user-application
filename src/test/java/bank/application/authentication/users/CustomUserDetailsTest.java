package bank.application.authentication.users;

import bank.application.authentication.model.Role;
import bank.application.authentication.model.User;
import bank.application.authentication.service.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomUserDetailsTest{
    @Test
    void testConstructor() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        Role role = new Role();
        role.setName("ADMIN");
        user.setRole(role);

        // Act
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // Assert
        assertEquals("testUser", customUserDetails.getUsername());
        assertEquals("password", customUserDetails.getPassword());
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        Assertions.assertEquals(1, authorities.size());
        Assertions.assertTrue(authorities.contains(new SimpleGrantedAuthority("ADMIN")));
    }
}