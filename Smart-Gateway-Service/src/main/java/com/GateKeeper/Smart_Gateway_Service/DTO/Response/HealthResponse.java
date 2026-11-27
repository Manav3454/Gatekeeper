package com.GateKeeper.Smart_Gateway_Service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthResponse
{
    private String servicename;
    private String serviceDescription;
    private String  AppStatus;

}
