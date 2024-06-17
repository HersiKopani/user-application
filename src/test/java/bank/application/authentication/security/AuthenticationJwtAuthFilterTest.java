package bank.application.authentication.security;

import bank.application.authentication.security.service.AuthenticationJwtService;
import bank.application.authentication.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;


public class AuthenticationJwtAuthFilterTest {
    @Mock
    private AuthenticationJwtService jwtService;

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @InjectMocks
    private AuthenticationJwtAuthFilter authenticationJwtAuthFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternal_ValidToken_SetAuthentication() throws ServletException, IOException {
        // Arrange
        String username = "testUser";
        String token = "dummyToken";
        UserDetails userDetails = new User(username, "password", Collections.singleton(new SimpleGrantedAuthority("BANKER")));
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(jwtService.extractUsername(token)).thenReturn(username);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(jwtService.validateToken(token, userDetails)).thenReturn(true);

        // Act
        authenticationJwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verify(userDetailsServiceImpl).loadUserByUsername(username);
        Mockito.verify(jwtService).validateToken(token, userDetails);
        Mockito.verify(request).getHeader("Authorization");
        Assert.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void doFilterInternal_InvalidToken_NoAuthenticationSet() throws ServletException, IOException {
        // Arrange
        String token = "invalidToken";
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(jwtService.extractUsername(token)).thenReturn(null);

        // Act
        authenticationJwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verify(request).getHeader("Authorization");
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

}
