package com.GateKeeper.Api_Management_Service.DTO.Response;

import com.GateKeeper.Api_Management_Service.Entity.ManagementUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantAdminResponse
{
    private Long id;
    private Long OrganizatioId;
    private String username ;
    private boolean enabled;


    public static TenantAdminResponse from (ManagementUser user)
    {
        return new TenantAdminResponse(user.getId(),
                user.getOrganization().getId(),
                user.getUsername(),
                user.isEnabled());
    }
}
