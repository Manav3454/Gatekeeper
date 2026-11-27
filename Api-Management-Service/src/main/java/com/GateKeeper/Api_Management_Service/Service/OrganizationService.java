package com.GateKeeper.Api_Management_Service.Service;

import com.GateKeeper.Api_Management_Service.DTO.Request.OrganizationRequest;
import com.GateKeeper.Api_Management_Service.DTO.Request.TenantAdminRequest;
import com.GateKeeper.Api_Management_Service.DTO.Response.OrganizationResponse;
import com.GateKeeper.Api_Management_Service.DTO.Response.TenantAdminResponse;
import com.GateKeeper.Api_Management_Service.Entity.ManagementUser;
import com.GateKeeper.Api_Management_Service.Entity.Organization;
import com.GateKeeper.Api_Management_Service.Entity.OrganizationStatus;
import com.GateKeeper.Api_Management_Service.Entity.Role;
import com.GateKeeper.Api_Management_Service.Repository.ManagementUserRepo;
import com.GateKeeper.Api_Management_Service.Repository.OrganizationRepo;
import com.GateKeeper.Api_Management_Service.Repository.RoleRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.GateKeeper.Api_Management_Service.Exception.ApiException;
import org.springframework.transaction.annotation.Transactional;
import com.GateKeeper.Api_Management_Service.DTO.Request.OrganizationStatusRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class OrganizationService
{
    private final OrganizationRepo organizationRepo;
    private final ManagementUserRepo managementUserRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    public OrganizationService(OrganizationRepo organizationRepo, ManagementUserRepo managementUserRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder)
    {
        this.organizationRepo = organizationRepo;
        this.managementUserRepo = managementUserRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public OrganizationResponse create (OrganizationRequest request)
    {
        long startedAt = System.nanoTime();
        checkUnique (request.getName(),request.getSlug(),null);
        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setSlug(request.getSlug());
        organization.setStatus(OrganizationStatus.PENDING);
        organization .setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());

        organizationRepo.save(organization);

//        auditService.record(organization,"CREATE_ORGANIZATION","ORGANIZATION",organization.getId().toString(),startedAt,"Organization created in PENDING status");
        return OrganizationResponse.from(organization);
    }

    private  void checkUnique (String name , String slug, Long currentId) throws ApiException {
        if(organizationRepo.existsByNameIgnoreCase(name))
        {
            Organization current = currentId==null?
                    null:organizationRepo.findById(currentId).orElse(null);
            if(current==null || !current.getName().equalsIgnoreCase(slug))
            {
                throw new ApiException(HttpStatus.CONFLICT,"Organization name already Exists");
            }
        }
        if(organizationRepo.existsBySlugIgnoreCase(slug))
        {
            Organization current = currentId==null?
                    null:organizationRepo.findById(currentId).orElse(null);
            if(current==null || !current.getSlug().equalsIgnoreCase(slug))
            {
                throw new ApiException(HttpStatus.CONFLICT,"Organization Slug already Exists");
            }
        }
    }

    @Transactional(readOnly = true)
    public List<OrganizationResponse> findAll()
    {
        List<OrganizationResponse> response = new ArrayList<>();
        for(Organization organization: organizationRepo.findAllByStatusNot(OrganizationStatus.DELETED))
        {
            response.add(OrganizationResponse.from(organization));
        }
        return response;
    }

    @Transactional (readOnly=true)
    public OrganizationResponse findById (Long id)
    {
        return OrganizationResponse.from(getOrganization(id));
    }

    @Transactional
    public OrganizationResponse update( Long id ,OrganizationRequest request )
    {
        long startedAt = System.nanoTime();
        Organization organization = getOrganization(id);

        checkUnique(request.getName(), request.getSlug(),id);

        organization.setName(request.getName());
        organization.setSlug(request.getSlug());
        organization.setUpdatedAt(LocalDateTime.now());

        return OrganizationResponse.from(organization);
    }

    @Transactional
    public OrganizationResponse changeStatus (Long id , OrganizationStatusRequest request)
    {
        long startedAt= System.nanoTime();
        Organization organization= getOrganization(id);

        if(request.getStatus()== OrganizationStatus.DELETED)
        {
            throw new ApiException((HttpStatus .BAD_REQUEST),"USE DELETE to Soft DELETE AN ORGANIZATION");
        }
        OrganizationStatus status = request.getStatus();
        organization.setStatus(request.getStatus());
        organization.setUpdatedAt(LocalDateTime.now());

        return OrganizationResponse.from(organization);
    }
    @Transactional
    public void delete(Long id) {
        long startedAt = System.nanoTime();

        Organization organization = getOrganization(id);
        organization.setStatus(OrganizationStatus.DELETED);
        organization.setDeletedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());
    }

    private Organization getOrganization(Long id)
    {
        return organizationRepo.findByIdAndStatusNot(id,OrganizationStatus.DELETED).orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND,"Organization not Found"));
    }




    /// // tenant ke liye ///
    @Transactional
    public TenantAdminResponse createTenantAdmin(Long organizationId, TenantAdminRequest request) {
        long startedAt = System.nanoTime();
        Organization organization = getOrganization(organizationId);

        if(managementUserRepo.findByOrganizationId(organizationId).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "This organization already has a tenant administrator");
        }

        if(managementUserRepo.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists");
        }

        Role role = roleRepo.findByName("TENANT_ADMIN")
                .orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "TENANT_ADMIN role is missing"));

        ManagementUser user = new ManagementUser();
        user.setOrganization(organization);
        user.setRole(role);
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(request.isEnabled());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        managementUserRepo.save(user);

        return TenantAdminResponse.from(user);
    }

    @Transactional(readOnly = true)
    public TenantAdminResponse getTenantAdmin(Long organizationId) {
        return TenantAdminResponse.from(getTenantAdminEntity(organizationId));
    }

    @Transactional
    public TenantAdminResponse updateTenantAdmin(Long organizationId, TenantAdminRequest request) {
        long startedAt = System.nanoTime();
        ManagementUser user = getTenantAdminEntity(organizationId);

        if(!user.getUsername().equals(request.getUsername())
                && managementUserRepo.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists");
        }

        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(request.isEnabled());
        user.setUpdatedAt(LocalDateTime.now());

        return TenantAdminResponse.from(user);
    }

    @Transactional
    public void deleteTenantAdmin(Long organizationId) {
        long startedAt = System.nanoTime();

        ManagementUser user = getTenantAdminEntity(organizationId);

        managementUserRepo.delete(user);
    }

    private ManagementUser getTenantAdminEntity(Long organizationId) {
        getOrganization(organizationId);
        return managementUserRepo.findByOrganizationId(organizationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Tenant administrator not found"));
    }


}
