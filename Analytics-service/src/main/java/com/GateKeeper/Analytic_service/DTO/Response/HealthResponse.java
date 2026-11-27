package com.GateKeeper.Analytic_service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponse
{
    private String name ;
    private String description;
    private String AppStatus;

}
