package org.authmicroservice.web;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.authmicroservice.dto.*;
import org.authmicroservice.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {return ResponseEntity.ok(authService.login(request));}

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {return ResponseEntity.ok(authService.register(request));}

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> validationEmail(@RequestParam("token") String confirmationToken) {
        return authService.confirmEmail(confirmationToken);
    }

    @PostMapping(value = "/recuperer-mot-de-passe", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> handleResetPassword(@RequestParam("email") String email) throws MessagingException {
        return authService.handleResetPassword(email);
    }

    @PostMapping(value = "/changer-mot-de-passe", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> handleChangePassword(@RequestParam("token") String token, @RequestBody ChangePasswordDTO changePasswordDTO) {
        changePasswordDTO.setToken(token);
        authService.handleChangePassword(changePasswordDTO);
        return ResponseEntity.ok("Le mot de passe a été changé avec succès");
    }

}
