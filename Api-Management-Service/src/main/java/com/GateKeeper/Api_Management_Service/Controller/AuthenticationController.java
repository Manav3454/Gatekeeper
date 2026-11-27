package com.GateKeeper.Api_Management_Service.Controller;

import com.GateKeeper.Api_Management_Service.DTO.Request.LoginRequest;
import com.GateKeeper.Api_Management_Service.DTO.Response.LoginResponse;
import com.GateKeeper.Api_Management_Service.Service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController
{
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
        public ResponseEntity<LoginResponse>login(@Valid @RequestBody LoginRequest loginRequest)
        {
            return ResponseEntity.ok(authenticationService.login(loginRequest));
        }

    }

