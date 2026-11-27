package com.GateKeeper.Api_Management_Service.Repository;

import com.GateKeeper.Api_Management_Service.Entity.ManagementUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagementUserRepo extends JpaRepository<ManagementUser, Long>
{
   Optional<ManagementUser> findByUsername(String username);

   Optional<ManagementUser> findByOrganizationId(Long organizationId);

   boolean existsByUsername(String username);
}
