package com.GateKeeper.Api_Management_Service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse
{
    private String token;
    private String tokenType;
    private Long epiryInSecond;
    private String role;
    private Long organizationId;



}
