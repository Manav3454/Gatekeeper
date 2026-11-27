package com.GateKeeper.Api_Management_Service.DTO.Request;

import com.GateKeeper.Api_Management_Service.Entity.OrganizationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationStatusRequest
{
    private OrganizationStatus status;
}
