package com.omar_ahmed42.employeemanagementsystem.controller;

import com.omar_ahmed42.employeemanagementsystem.exception.NotFoundException;
import com.omar_ahmed42.employeemanagementsystem.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class RoleUserController {


    private final RoleUserService roleUserService;

    @Autowired
    public RoleUserController(RoleUserService roleUserService) {
        this.roleUserService = roleUserService;
    }

    @PutMapping("/user/role/")
    public ResponseEntity<?> addRoleToUser(@RequestParam(name = "userId") long userID, @RequestParam(name = "roleId") long roleID){
        try {
            roleUserService.addRoleToUser(userID, roleID);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (NotFoundException notFoundException){
            return ResponseEntity.notFound().build();
        }
    }
}