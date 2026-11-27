package com.GateKeeper.Api_Management_Service.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TenantAdminRequest
{
    @NotBlank
    @Size(max=100)
    private String username;

    @NotBlank
    @Size(max=100)
    private String password;

    private boolean enabled = true;
}
