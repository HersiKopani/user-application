package bank.application.authentication.controller;

import bank.application.authentication.dto.AuthRequestDTO;
import bank.application.authentication.dto.JwtResponseDTO;
import bank.application.authentication.security.service.AuthenticationJwtService;
import bank.application.authentication.utils.ErrorResponseDTO;
import bank.application.authentication.utils.LogInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bank.application.authentication.utils.LogInfoUtil.*;


@Slf4j
@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationJwtService authenticationJwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                JwtResponseDTO response = JwtResponseDTO.builder()
                        .accessToken(authenticationJwtService.GenerateToken(authRequestDTO.getUsername())).build();
                return ResponseEntity.ok(response);
            } else {
                log.error("Invalid Credentials");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(BAD_CREDENTIALS, BAD_CREDENTIALS_CODE));
            }
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO("Authentication failed", UNAUTHORIZED_CODE));
        }
    }
}
