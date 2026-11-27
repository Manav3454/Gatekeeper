package com.GateKeeper.Api_Management_Service.Controller;

import com.GateKeeper.Api_Management_Service.DTO.Request.OrganizationRequest;
import com.GateKeeper.Api_Management_Service.DTO.Request.OrganizationStatusRequest;
import com.GateKeeper.Api_Management_Service.DTO.Request.TenantAdminRequest;
import com.GateKeeper.Api_Management_Service.DTO.Response.OrganizationResponse;
import com.GateKeeper.Api_Management_Service.DTO.Response.TenantAdminResponse;
import com.GateKeeper.Api_Management_Service.Service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/platform/organization")
public class PlatformOrganizationController
{
    private final OrganizationService organizationService;

    public PlatformOrganizationController(OrganizationService organizationService)
    {
        this.organizationService = organizationService;
    }

    //crud operation perform krega on Organization ans tenant
    /*
    1.Create
    */
    @PostMapping("")
    public ResponseEntity<OrganizationResponse> create(
            @Valid @RequestBody OrganizationRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.create(request));
    }

    //2.reedAll
    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> findAll()
    {
        return ResponseEntity.ok(organizationService.findAll());
    }

     //3.readone
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse>findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(organizationService.findById(id));
    }

    //4.update
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationResponse> update (@PathVariable Long id , @Valid @RequestBody OrganizationRequest request)
    {
        return ResponseEntity.ok(organizationService.update(id,request));
    }

    //5.Patchend point

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrganizationResponse> changeStatus(@PathVariable Long id ,
                                                             @Valid @RequestBody OrganizationStatusRequest statusRequest)
    {
        return  ResponseEntity.ok(organizationService.changeStatus(id,statusRequest));
    }

    // 6.delete(softdelete)

    @DeleteMapping("/{id}")

        public ResponseEntity<Void> delete(@PathVariable Long id)
        {
            organizationService.delete(id);
            return ResponseEntity.noContent().build();
        }




        /// /tenant ke liye



        @PostMapping("/{organizationId}/tenant-admin")
        public ResponseEntity<TenantAdminResponse> createTenantAdmin(
                @PathVariable Long organizationId,
                @Valid @RequestBody TenantAdminRequest request)
        {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(organizationService.createTenantAdmin(organizationId, request));
        }

    @GetMapping("/{organizationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> getTenantAdmin(@PathVariable Long organizationId) {
        return ResponseEntity.ok(organizationService.getTenantAdmin(organizationId));
    }

    @PutMapping("/{organizationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> updateTenantAdmin(@PathVariable Long organizationId,
                                                                 @Valid @RequestBody TenantAdminRequest request) {
        return ResponseEntity.ok(organizationService.updateTenantAdmin(organizationId, request));
    }

    @DeleteMapping("/{organizationId}/tenant-admin")
    public ResponseEntity<Void> deleteTenantAdmin(@PathVariable Long organizationId) {
        organizationService.deleteTenantAdmin(organizationId);
        return ResponseEntity.noContent().build();
    }

}
