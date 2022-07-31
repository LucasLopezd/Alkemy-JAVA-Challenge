package com.alkemy.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.challenge.dto.NewRoleDto;
import com.alkemy.challenge.models.Role;
import com.alkemy.challenge.respository.RoleRepository;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;

    
    public Role create(NewRoleDto dto){
        Role role = new Role(dto.getRoleName());
        return roleRepository.save(role);
    }

    public Role createFromUser(String roleName){
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    public Role getById(Long id){
        return roleRepository.findById(id).get();
    }
    

}
