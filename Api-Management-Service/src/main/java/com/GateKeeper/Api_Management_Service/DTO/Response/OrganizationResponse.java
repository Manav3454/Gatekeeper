package com.GateKeeper.Api_Management_Service.DTO.Response;

import com.GateKeeper.Api_Management_Service.Entity.Organization;
import com.GateKeeper.Api_Management_Service.Entity.OrganizationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse
{
    private Long id;
    private String name ;
    private String slug;
    private OrganizationStatus status;
    private LocalDateTime  createdAt;
    private LocalDateTime updatedAt;

    public static OrganizationResponse from (Organization organization)
    {
        return new OrganizationResponse(organization.getId()
                ,organization.getName(),organization.getSlug(),
                organization.getStatus(),organization.getCreatedAt(),organization.getUpdatedAt());
    }
}
