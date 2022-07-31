package com.alkemy.challenge.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.challenge.models.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long>{
    
    
}
