package bank.application.authentication.users;

import bank.application.authentication.controller.AuthController;
import bank.application.authentication.dto.AuthRequestDTO;
import bank.application.authentication.dto.JwtResponseDTO;
import bank.application.authentication.security.service.AuthenticationJwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthenticationJwtService authenticationJwtService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateAndGetToken_AuthenticationSuccess_ReturnsJwtResponse() {
        // Arrange
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("validUsername", "validPassword");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationJwtService.GenerateToken("validUsername")).thenReturn("dummyToken");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act
        ResponseEntity<?> responseEntity = authController.AuthenticateAndGetToken(authRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof JwtResponseDTO);
    }

    @Test
    void authenticateAndGetToken_AuthenticationFailure_ReturnsErrorResponse() {
        // Arrange
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("invalidUsername", "invalidPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(AuthenticationServiceException.class);

        // Act
        ResponseEntity<?> responseEntity = authController.AuthenticateAndGetToken(authRequestDTO);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        // Add more assertions for the response body if needed
    }

}
