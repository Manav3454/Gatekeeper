package com.GateKeeper.Api_Management_Service.Repository;
import com.GateKeeper.Api_Management_Service.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface RoleRepo extends JpaRepository<Role, Long>
{

        Optional<Role> findByName(String name);
 }

