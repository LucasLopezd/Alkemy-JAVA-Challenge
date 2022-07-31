package com.alkemy.challenge.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
