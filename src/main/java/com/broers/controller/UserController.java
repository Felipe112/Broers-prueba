package com.broers.controller;

import com.broers.common.Constants;
import com.broers.controller.request.PasswordResetRequest;
import com.broers.controller.request.UpdatePasswordRequest;
import com.broers.controller.request.UserRegisterRequest;
import com.broers.controller.response.BasicResponse;
import com.broers.controller.response.UserResponse;
import com.broers.model.User;
import com.broers.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de usuarios.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios")
public class UserController {

    ////
    //// DEPENDENCIAS
    ////
    private final UserService userService;

    ////
    //// MÉTODOS PÚBLICOS
    ////

    /**
     * Metodo de registro de usuarios.
     * @param userRequest
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<BasicResponse<UserResponse>> registerUser(@Valid @RequestBody UserRegisterRequest userRequest) {

        User user = userService.registerUser(userRequest);

        return new ResponseEntity<>(new BasicResponse<>(Constants.SUCCESS_RESPONSE, UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .isActive(user.isActive())
            .build()), HttpStatus.CREATED);
    }

    /**
     * Metodo para activar un registro.
     * @param token
     * @return
     */
    @GetMapping("/activate")
    @Operation(summary = "Activar usuario", description = "Activa un usuario mediante un token enviado por correo electrónico")
    public ResponseEntity<BasicResponse<UserResponse>> activateUser(@RequestParam("token") String token) {
        User user = userService.activateUser(token);
        return new ResponseEntity<>(new BasicResponse<>(Constants.SUCCESS_RESPONSE, UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .password(user.getPassword())
            .isActive(user.isActive())
            .build()), HttpStatus.OK);
    }

    /**
     * Metodo para petición de reset de clave.
     * @param request
     * @return
     */
    @PostMapping("/password-reset")
    @Operation(summary = "Restablecer contraseña", description = "Envía un enlace de recuperación de contraseña al correo del usuario")
    public ResponseEntity<BasicResponse<Void>> passwordReset(
        @RequestBody PasswordResetRequest request) {
        userService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok(
            new BasicResponse<>(Constants.LINK_RESET));
    }

    /**
     * Metodo para actualizar la clave.
     * @param request
     * @return
     */
    @PutMapping("/update-password")
    @Operation(summary = "Actualizar contraseña", description = "Permite actualizar la contraseña usando un token de recuperación")
        public ResponseEntity<BasicResponse<String>> updatePassword(
        @Valid  @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(
            request.getToken(),
            request.getNewPassword()
        );

        return ResponseEntity.ok(
            new BasicResponse<>(Constants.UPDATE_PASS_MESSAGE)
        );
    }

}
