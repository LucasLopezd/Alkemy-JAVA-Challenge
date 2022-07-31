package com.alkemy.challenge.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

}
