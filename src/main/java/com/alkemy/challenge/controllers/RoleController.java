package com.alkemy.challenge.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.challenge.dto.NewRoleDto;
import com.alkemy.challenge.models.Role;
import com.alkemy.challenge.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    
    private RoleService roleService;

    @PostMapping("/new")
    public Role create(@RequestBody NewRoleDto dto){
        return roleService.create(dto);
    }

    @GetMapping("/all")
    public List<Role> getAll(){
        return roleService.getAll();
    }
}
