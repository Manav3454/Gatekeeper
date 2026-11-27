package com.GateKeeper.Api_Management_Service.Repository;

import com.GateKeeper.Api_Management_Service.Entity.Organization;
import com.GateKeeper.Api_Management_Service.Entity.OrganizationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepo extends JpaRepository<Organization,Long >
{
    boolean existsByNameIgnoreCase(String name);
    boolean existsBySlugIgnoreCase(String slug);
    List<Organization> findAllByStatusNot(OrganizationStatus status);
    Optional<Organization> findByIdAndStatusNot(Long id, OrganizationStatus status);

}
