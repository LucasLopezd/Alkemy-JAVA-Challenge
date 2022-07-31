package com.alkemy.challenge.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.challenge.dto.user.UserUpdateDto;
import com.alkemy.challenge.models.User;
import com.alkemy.challenge.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    
    @PutMapping("/update/{id}")
    public User update(@RequestBody UserUpdateDto dto, @PathVariable(value = "id") Long id){
        return userService.update(dto, id);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable (value = "id") Long id){
        return userService.detele(id);
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable (value = "id") Long id){
        return userService.getById(id);
    }



}
